package com.example.customviewhandson.customviews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.customviewhandson.R;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

public class MyCustomView extends View {

    private static final String PROPERTY_RADIUS = "radius";
    private static final String PROPERTY_SMALL_CIRCLE_RADIUS = "smallCircleRadius";
    private static final String PROPERTY_CIRCLE_FADEIN = "circlefadeIn";
    private static final String PROPERTY_CIRCLE_FADEOUT = "circlefadeOut";
    private static final String PROPERTY_ROTATE = "rotate";
    private static final String PROPERTY_LINE = "line";
    Paint circlePaint;
    Paint linePaint;
    Rect rect;
    RectF oval;
    int viewColor;
    int radius;
    int smallCircleRadius;
    int rotate;
    int lineCoordinates = 0;
    float fadeIn = .9f;
    float fadeOut = .3f;
    int lineStart = 0;
    int viewWidth;
    int viewHeight;
    int leftTopX;
    int leftTopY;
    int rightBotX;
    int rightBotY;
    PropertyValuesHolder propertyRadius = PropertyValuesHolder.ofInt(PROPERTY_RADIUS, 40, 200);
    PropertyValuesHolder propertyRotate = PropertyValuesHolder.ofInt(PROPERTY_ROTATE, 0, 360);
    PropertyValuesHolder propertySmallCircleRadius = PropertyValuesHolder.ofInt(PROPERTY_SMALL_CIRCLE_RADIUS, 5, 10);
    PropertyValuesHolder propertyFadeIn = PropertyValuesHolder.ofFloat(PROPERTY_CIRCLE_FADEIN, .3f, .9f);
    PropertyValuesHolder propertyFadeOut = PropertyValuesHolder.ofFloat(PROPERTY_CIRCLE_FADEOUT, .9f, .3f);
    PropertyValuesHolder propertyLine = PropertyValuesHolder.ofInt(PROPERTY_LINE, 0, 130);
    Canvas smallCircleView = new Canvas();

    public MyCustomView(Context context) {
        super(context);
        init(null);
    }

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        circlePaint = new Paint();
        linePaint = new Paint();
        circlePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(10);
        rect = new Rect();
        oval = new RectF();
        if (attrs == null) {
            return;
        }
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MyCustomView);
        viewColor = ta.getColor(R.styleable.MyCustomView_square_color, Color.GREEN);
        circlePaint.setColor(viewColor);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(6);
        linePaint.setColor(ContextCompat.getColor(getContext(), R.color.red));
        ta.recycle();
        radius = 60;
        rotate = 0;
        lineStart = 80;
        fadeIn = 0f;
        fadeOut = .5f;
    }

    public void startAnimation() {
        final ValueAnimator animator = new ValueAnimator();
        animator.setValues(propertyRadius, propertyFadeIn, propertyFadeOut);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearOutSlowInInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radius = (int) animation.getAnimatedValue(PROPERTY_RADIUS);
                fadeIn = (float)animation.getAnimatedValue(PROPERTY_CIRCLE_FADEIN);
                fadeOut = (float)animation.getAnimatedValue(PROPERTY_CIRCLE_FADEOUT);
                animation.setRepeatCount(ValueAnimator.INFINITE);
                animation.setRepeatMode(ValueAnimator.REVERSE);
                invalidate();
            }
        });
        setUpFadeInOutAnimation();
        animator.start();
    }

    void setUpFadeInOutAnimation() {
        ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(this, "alpha", fadeOut, fadeIn);
        ObjectAnimator fadeOutAnimator = ObjectAnimator.ofFloat(this, "alpha", fadeIn, fadeOut);
        fadeOutAnimator.setDuration(2000);
        fadeInAnimator.setDuration(2000);
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(fadeOutAnimator).after(fadeInAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorSet.start();
            }
        });
        animatorSet.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        viewWidth = getWidth() / 2;
        viewHeight = getHeight() / 2;
        leftTopX = viewWidth - 10;
        leftTopY = viewHeight - 10;
        rightBotX = viewWidth + 10;
        rightBotY = viewHeight + 10;
        canvas.drawCircle(viewWidth, viewHeight, radius, circlePaint);

        // canvas.rotate(rotate, viewWidth, viewHeight);
        // canvas.drawLine(viewWidth - 140, viewHeight, viewWidth - 70, viewHeight + 70, linePaint);
        // canvas.drawLine(viewWidth - lineStart, viewHeight + lineStart, viewWidth + lineCoordinates, viewHeight - lineCoordinates, linePaint);
    }
}
