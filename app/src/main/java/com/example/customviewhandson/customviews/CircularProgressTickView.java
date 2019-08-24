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
    float viewHeight;
    float viewWidth;
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
    private ValueAnimator valueAnimator;
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
        valueAnimator = new ValueAnimator();
        valueAnimator.setValues(propertyStartAngle, propertySweepAngle);
        valueAnimator.setDuration(ANIMATION_DURATION);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
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
        valueAnimator.start();
    }


    public void stopCircleAnimation() {
        valueAnimator.cancel();
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
        valueAnimator.cancel();
        tickValueAnimator.cancel();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawTickMark) {
            canvas.drawCircle(viewWidth, viewHeight, circleRadius, paint);
            canvas.drawPath(drawingPath, paint);
            return;
        }
        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = (float) w / 2;
        viewHeight = (float) h / 2;
        initViewProperties();
    }

    final float[] position = new float[2]; // field

    private void initViewProperties() {
        Path path = new Path();
        // 1st Line
        startX = viewWidth - 90;
        startY = viewHeight - 10;
        float stopX = viewWidth - 30;
        float stopY = viewHeight + 40;
        // 2nd Line
        float finalStopX = viewWidth + 70;
        float finalStopY = viewHeight - 70;
        path.moveTo(startX, startY);
        path.lineTo(stopX, stopY);
        path.lineTo(finalStopX, finalStopY);
        drawingPath = new Path();
        drawingPath.moveTo(startX, startY);
        pathMeasure = new PathMeasure(path, false);
        pathLength = pathMeasure.getLength();
        left = viewWidth - circleRadius;
        top = viewHeight - circleRadius;
        right = viewWidth + circleRadius;
        bottom = viewHeight + circleRadius;
        rectF.set(left, top, right, bottom);
    }
}
