package com.siziksu.canvas.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.siziksu.canvas.R;

public class DrawingView extends View {

    private Paint drawingPaint;
    private Path path;
    private int brushWidth = 5;
    private int brushColor = Color.parseColor("#ffff0000");

    public DrawingView(Context context) {
        super(context);
        init(context, null);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.DrawingView));
        }
    }

    /**
     * Parse the attributes passed to the view from the XML
     *
     * @param attributes the attributes to parse
     */
    private void parseAttributes(TypedArray attributes) {
        brushWidth = attributes.getInt(R.styleable.DrawingView_brushWidth, brushWidth);
        brushColor = attributes.getColor(R.styleable.DrawingView_brushColor, brushColor);
        attributes.recycle();
    }

    /**
     * Use onSizeChanged instead of onAttachedToWindow to get the dimensions of the view,
     * because this method is called after measuring the dimensions of MATCH_PARENT & WRAP_CONTENT.
     * Use this dimensions to setup the bounds and paints.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setupPaints();
        invalidate();
    }

    private void setupPaints() {
        path = new Path();
        drawingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        drawingPaint.setStyle(Paint.Style.STROKE);
        drawingPaint.setColor(brushColor);
        drawingPaint.setStrokeWidth(brushWidth);
        // drawingPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        // canvas.drawLine(0, 0, getMeasuredWidth(), getMeasuredHeight(), drawingPaint);
        canvas.drawPath(path, drawingPaint);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(touchX, touchY);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
