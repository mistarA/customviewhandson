package com.example.customviewhandson.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class BarView extends View {

    Paint paintObject;
    int viewHeight;
    int viewWidth;
    int lines = 10;
    int[] data = {20, 45, 28, 80, 99, 43, 99, 80, 28, 45, 20};
    int spacing;

    public BarView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attributeSet, int defStyle) {
        paintObject = new Paint();
        paintObject.setStyle(Paint.Style.STROKE);
        paintObject.setStrokeCap(Paint.Cap.BUTT);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int leftStartCoordinates = getLeft();
        int rightEndCoordinates = getRight();
        int totalWidth = rightEndCoordinates - leftStartCoordinates;
        Log.d(BarView.class.getSimpleName() + "left", String.valueOf(leftStartCoordinates));
        Log.d(BarView.class.getSimpleName() + "right", String.valueOf(rightEndCoordinates));
        Log.d(BarView.class.getSimpleName() + "totalWidthPresent", String.valueOf(totalWidth));
        spacing = totalWidth / lines;
        Log.d(BarView.class.getSimpleName() + "spacing", String.valueOf(spacing));
        paintObject.setStrokeWidth(spacing);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        viewHeight = getHeight() / 2;
        viewWidth = getWidth() / 2;

        int step = 0;
//        canvas.drawLine(getLeft() + spacing/ 2, viewHeight, getLeft() + spacing / 2, viewHeight - 30, paintObject);
//        canvas.drawLine(viewWidth, viewHeight, viewWidth, viewHeight - 40, paintObject);

        for (int i = 0; i < lines; i++) {
            int left = getLeft() + step + spacing / 2;
            canvas.drawLine(left, viewHeight, left, viewHeight - data[i], paintObject);
            Log.d(BarView.class.getSimpleName() + "step", String.valueOf(step));
            step += spacing;
        }
    }

}
