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
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;

import com.example.customviewhandson.R;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

public class CircleAnimatorView extends View {

    Paint circlePaint;
    int viewWidth;
    int viewHeight;
    int leftTopX;
    int leftTopY;
    int rightBotX;
    int rightBotY;
    int smallCircleRadius = 15;

    public CircleAnimatorView(Context context) {
        super(context);
        init(null);
    }

    public CircleAnimatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CircleAnimatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CircleAnimatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        if (attrs == null) {
            return;
        }
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MyCustomView);
        circlePaint.setColor(ContextCompat.getColor(getContext(), R.color.circle_color));
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(3);
        ta.recycle();
    }

    public void startCircleAnimation() {
        ScaleAnimation bounce = new ScaleAnimation(1f, 1f, 1f, 1.2f, viewHeight, viewWidth);
        bounce.setDuration(600);
        bounce.setInterpolator(new AccelerateDecelerateInterpolator());
        this.startAnimation(bounce);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        viewWidth = getWidth() / 2;
        viewHeight = getHeight() / 2;
        leftTopX = viewWidth - 20;
        leftTopY = viewHeight - 20;
        rightBotX = viewWidth + 20;
        rightBotY = viewHeight + 20;
        // canvas.rotate(rotate, viewWidth, viewHeight);
        canvas.drawRoundRect(leftTopX, leftTopY, rightBotX, rightBotY, smallCircleRadius, smallCircleRadius, circlePaint);
    }
}
