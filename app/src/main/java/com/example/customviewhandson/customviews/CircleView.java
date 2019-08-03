package com.example.customviewhandson.customviews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;

import com.example.customviewhandson.R;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

public class CircleView extends View {

    Paint paintObject;
    int viewHeight;
    int viewWidth;
    ValueAnimator valueAnimator;
    int circleRadius;

    public CircleView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attributeSet, int defStyle) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleView);
        int strokeWidth = typedArray.getInteger(R.styleable.CircleView_widthStroke, 10);
        int color = typedArray.getColor(R.styleable.CircleView_color, ContextCompat.getColor(context,R.color.circle_color));
        circleRadius = typedArray.getInt(R.styleable.CircleView_radius, 100);
        paintObject = new Paint();
        paintObject.setColor(color);
        paintObject.setStyle(Paint.Style.STROKE);
        paintObject.setStrokeWidth(strokeWidth);
        typedArray.recycle();
    }

    public void startAnimation() {
        valueAnimator= ValueAnimator.ofInt(20, circleRadius);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleRadius = (int) animation.getAnimatedValue();
                animation.setRepeatCount(ValueAnimator.INFINITE);
                animation.setRepeatMode(ValueAnimator.REVERSE);
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        viewHeight = getHeight() / 2;
        viewWidth = getWidth() / 2;
        canvas.drawCircle(viewWidth, viewHeight, circleRadius, paintObject);
    }

    public void stopAnimation() {
        valueAnimator.end();
    }
}
