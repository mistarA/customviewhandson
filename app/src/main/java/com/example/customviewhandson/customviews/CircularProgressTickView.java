package com.example.customviewhandson.customviews;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.customviewhandson.R;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class CircularProgressTickView extends View {

    Paint paint;
    int circleRadius;
    int color;
    int strokeWidth;
    RectF rectF;
    float viewCenterVertical;
    float viewCenterHorizontal;
    float left;
    float top;
    float right;
    float bottom;
    int startAngle;
    int sweepAngle;
    float pathDt;
    private float startX;
    private float startY;
    volatile boolean drawTickMark;
    private Path drawingPath;
    private PathMeasure pathMeasure;
    private float pathLength;
    final float[] position = new float[2];
    private ValueAnimator arcAnimator;
    private ValueAnimator tickValueAnimator;
    private static final int ANIMATION_DURATION = 1500;
    private static final String PROPERTY_START_ANGLE = "startAngle";
    private static final String PROPERTY_SWEEP_ANGLE = "sweepAngle";
    PropertyValuesHolder propertyStartAngle = PropertyValuesHolder.ofInt(PROPERTY_START_ANGLE, 0, 360);
    PropertyValuesHolder propertySweepAngle = PropertyValuesHolder.ofInt(PROPERTY_SWEEP_ANGLE, 0, 360);


    public CircularProgressTickView(Context context) {
        super(context);
        init(context, null);
    }

    public CircularProgressTickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressTickView);
        strokeWidth = typedArray.getInteger(R.styleable.CircularProgressTickView_widthStroke, 10);
        color = typedArray.getColor(R.styleable.CircularProgressTickView_color, ContextCompat.getColor(context, R.color.circle_color));
        circleRadius = typedArray.getInt(R.styleable.CircularProgressTickView_radius, 100);
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        rectF = new RectF();
        typedArray.recycle();
        tickValueAnimator = ValueAnimator.ofFloat(0, 1);
        arcAnimator = new ValueAnimator();
        arcAnimator.setValues(propertyStartAngle, propertySweepAngle);
        arcAnimator.setDuration(ANIMATION_DURATION);
        arcAnimator.setInterpolator(new LinearInterpolator());
        arcAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startAngle = (int) animation.getAnimatedValue(PROPERTY_START_ANGLE);
                sweepAngle = (int) animation.getAnimatedValue(PROPERTY_SWEEP_ANGLE);
                animation.setRepeatCount(ValueAnimator.INFINITE);
                animation.setRepeatMode(ValueAnimator.REVERSE);
                invalidate();
            }
        });
        tickValueAnimator.setDuration(ANIMATION_DURATION);
        tickValueAnimator.setInterpolator(new FastOutSlowInInterpolator());
        tickValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pathDt = (float) animation.getAnimatedValue();
                final float distance = pathDt * pathLength;
                pathMeasure.getPosTan(distance, position, null);
                drawingPath.lineTo(position[0], position[1]);
                invalidate();
            }
        });
    }

    public void startCircleAnimation() {
        tickValueAnimator.cancel();
        drawTickMark = false;
        drawingPath.reset();
        drawingPath.moveTo(startX, startY);
        arcAnimator.start();
    }


    public void stopCircleAnimation() {
        arcAnimator.cancel();
        drawTickMark = true;
        startTickAnimation();
        invalidate();
    }

    private void startTickAnimation() {
        tickValueAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        arcAnimator.cancel();
        tickValueAnimator.cancel();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawTickMark) {
            canvas.drawCircle(viewCenterHorizontal, viewCenterVertical, circleRadius, paint);
            canvas.drawPath(drawingPath, paint);
            return;
        }
        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewCenterHorizontal = (float) w / 2;
        viewCenterVertical = (float) h / 2;
        initViewProperties();
    }


    private void initViewProperties() {
        Path path = new Path();
        startX = viewCenterHorizontal - 90;
        startY = viewCenterVertical - 10;
        float stopX = viewCenterHorizontal - 30;
        float stopY = viewCenterVertical + 40;
        float finalStopX = viewCenterHorizontal + 70;
        float finalStopY = viewCenterVertical - 70;
        path.moveTo(startX, startY);
        path.lineTo(stopX, stopY);
        path.lineTo(finalStopX, finalStopY);
        drawingPath = new Path();
        drawingPath.moveTo(startX, startY);
        pathMeasure = new PathMeasure(path, false);
        pathLength = pathMeasure.getLength();
        left = viewCenterHorizontal - circleRadius;
        top = viewCenterVertical - circleRadius;
        right = viewCenterHorizontal + circleRadius;
        bottom = viewCenterVertical + circleRadius;
        rectF.set(left, top, right, bottom);
    }
}
