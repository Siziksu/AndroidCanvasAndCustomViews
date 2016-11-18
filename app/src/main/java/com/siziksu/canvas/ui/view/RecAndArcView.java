package com.siziksu.canvas.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

public class RecAndArcView extends View {

    private Paint paint;
    private RectF rectF;
    private RectF rectFback;

    public RecAndArcView(Context context) {
        super(context);
    }

    public RecAndArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecAndArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RecAndArcView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        paint = new Paint();
        paint.setAntiAlias(true);
        rectF = new RectF(
                getMeasuredWidth() / 4,
                getMeasuredHeight() / 4,
                (getMeasuredWidth() / 4) * 3,
                (getMeasuredHeight() / 4) * 3
        );
        rectFback = new RectF(
                getMeasuredWidth() / 6,
                getMeasuredHeight() / 6,
                (getMeasuredWidth() / 6) * 5,
                (getMeasuredHeight() / 6) * 5
        );
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        // canvas.drawLine(0, 0, getMeasuredWidth(), getMeasuredHeight(), drawingPaint);
        canvas.drawColor(0xffffffff);

        paint.setColor(0x150000ff);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rectFback, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);

        paint.setColor(0xcd892000);
        canvas.drawArc(rectF, 0, 90, false, paint);

        paint.setColor(0xbf200089);
        canvas.drawArc(rectF, 90, 90, false, paint);

        paint.setColor(0xad208900);
        canvas.drawArc(rectF, 180, 90, false, paint);

        paint.setColor(0xcd104589);
        canvas.drawArc(rectF, 270, 90, false, paint);
    }
}
