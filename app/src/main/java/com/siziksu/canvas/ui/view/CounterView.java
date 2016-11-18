package com.siziksu.canvas.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.siziksu.canvas.R;

public class CounterView extends View {

    private boolean debug = false;

    private Canvas canvas;
    private Bitmap bitmap;

    private int increment;

    private int startAngle = 180;
    private int drawingAngle = 300;
    private int stepAngle = 30;

    private float multiplier;

    private int layout_height = 0;
    private int layout_width = 0;

    private int rimScaleInnerRadius;
    private int rimScaleOuterRadius;

    private int barWidth = 20;
    private int barColor = 0xAA000000;
    private Paint barPaint = new Paint();

    private int rimWidth = 20;
    private int rimColor = 0xAADDDDDD;
    private Paint rimPaint = new Paint();

    private int needleWidth = 20;
    private int needleColor = 0xAA000000;

    private int outerScaleSize;
    private int outerScaleWidth = 20;
    private int outerScaleColor = 0xAADDDDDD;
    private Paint outerScalePaint = new Paint();

    private int rimScaleWidth = 20;

    private String kmh = "KMH";
    private String text = "0";
    private int textX;
    private int textY;
    private Rect textBounds = new Rect();
    private int textStyle = Typeface.NORMAL;
    private int textColor = 0xAA000000;
    private float textSize = 30;
    private Paint textPaint = new Paint();

    private int max;
    private int centerX;
    private int centerY;
    private int fullRadius;

    private Paint canvasPaint = new Paint();
    private Paint boundsPaint = new Paint();
    private Paint rimScalePaint = new Paint();

    private Paint needlePaint = new Paint();

    private RectF circleBounds = new RectF();

    public CounterView(Context context) {
        super(context);
        init(context, null);
    }

    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CounterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.CounterView));
        }
    }

    /**
     * Parse the attributes passed to the view from the XML
     *
     * @param attributes the attributes to parse
     */
    private void parseAttributes(TypedArray attributes) {
        barColor = attributes.getColor(R.styleable.CounterView_cvBarColor, barColor);
        barWidth = (int) attributes.getDimension(R.styleable.CounterView_cvBarWidth, barWidth);
        rimWidth = (int) attributes.getDimension(R.styleable.CounterView_cvRimWidth, rimWidth);
        rimColor = attributes.getColor(R.styleable.CounterView_cvRimColor, rimColor);
        textColor = attributes.getColor(R.styleable.CounterView_android_textColor, textColor);
        textSize = attributes.getDimension(R.styleable.CounterView_android_textSize, textSize);
        textStyle = attributes.getInt(R.styleable.CounterView_android_textStyle, textStyle);

        needleColor = attributes.getColor(R.styleable.CounterView_cvNeedleColor, needleColor);
        needleWidth = (int) attributes.getDimension(R.styleable.CounterView_cvNeedleWidth, needleWidth);
        outerScaleColor = attributes.getColor(R.styleable.CounterView_cvOuterScaleColor, outerScaleColor);
        outerScaleWidth = (int) attributes.getDimension(R.styleable.CounterView_cvOuterScaleWidth, outerScaleWidth);
        outerScaleSize = (int) attributes.getDimension(R.styleable.CounterView_cvOuterScaleSize, outerScaleSize);
        rimScaleWidth = (int) attributes.getDimension(R.styleable.CounterView_cvRimScaleWidth, rimScaleWidth);

        max = attributes.getInt(R.styleable.CounterView_android_max, max);
        startAngle = attributes.getInt(R.styleable.CounterView_cvStartAngle, startAngle);
        drawingAngle = attributes.getInt(R.styleable.CounterView_cvDrawingAngle, drawingAngle);
        stepAngle = attributes.getInt(R.styleable.CounterView_cvStepAngle, stepAngle);
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

        // Share the dimensions
        layout_width = w;
        layout_height = h;

        bitmap = Bitmap.createBitmap(layout_width, layout_height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        setupBounds();
        setupPaints();
        invalidate();
    }

    private void setupBounds() {
        // Width should equal to Height, find the min value to setup the circle
        int minWidthHeightValue = Math.min(layout_width, layout_height);
        int maxBarRimValue = Math.max(barWidth, rimWidth);
        int maxVerticalPaddingValue = Math.max(this.getPaddingTop(), this.getPaddingBottom());
        int maxHorizontalPaddingValue = Math.max(this.getPaddingLeft(), this.getPaddingRight());
        int maxPaddingValue = Math.max(maxVerticalPaddingValue, maxHorizontalPaddingValue);
        fullRadius = minWidthHeightValue / 2;

        centerX = layout_width / 2;
        centerY = layout_height / 2;

        // Calc the Offset if needed
        int xOffset = layout_width - minWidthHeightValue;
        int yOffset = layout_height - minWidthHeightValue;

        // Add the offset
        int paddingTop = this.getPaddingTop() + (yOffset / 2);
        int paddingBottom = this.getPaddingBottom() + (yOffset / 2);
        int paddingLeft = this.getPaddingLeft() + (xOffset / 2);
        int paddingRight = this.getPaddingRight() + (xOffset / 2);

        int left = paddingLeft + maxBarRimValue / 2;
        int top = paddingTop + maxBarRimValue / 2;
        int right = this.getLayoutParams().width - paddingRight - maxBarRimValue / 2;
        int bottom = this.getLayoutParams().height - paddingBottom - maxBarRimValue / 2;
        circleBounds = new RectF(left, top, right, bottom);

        left = paddingLeft + maxBarRimValue;
        top = paddingTop + maxBarRimValue;
        right = this.getLayoutParams().width - paddingRight - maxBarRimValue;
        bottom = this.getLayoutParams().height - paddingBottom - maxBarRimValue;
        textBounds = new Rect(left, top, right, bottom);

        rimScaleInnerRadius = fullRadius - maxBarRimValue - maxPaddingValue - 5;
        rimScaleOuterRadius = fullRadius - maxPaddingValue + 5;

        multiplier = drawingAngle / (float) max;
    }

    private void setupPaints() {
        if (debug) {
            boundsPaint.setAntiAlias(true);
            boundsPaint.setColor(0xFF000000);
            boundsPaint.setStrokeWidth(1);
            boundsPaint.setStyle(Paint.Style.STROKE);
        }

        if (barWidth > 0) {
            barPaint.setAntiAlias(true);
            barPaint.setColor(barColor);
            barPaint.setStrokeWidth(barWidth);
            barPaint.setStyle(Paint.Style.STROKE);
        }

        if (rimWidth > 0) {
            rimPaint.setAntiAlias(true);
            rimPaint.setColor(rimColor);
            rimPaint.setStrokeWidth(rimWidth);
            rimPaint.setStyle(Paint.Style.STROKE);
        }

        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, textStyle));
        textPaint.setTextAlign(Paint.Align.CENTER);

        needlePaint.setAntiAlias(true);
        needlePaint.setColor(needleColor); // 0xFF454545
        needlePaint.setStrokeWidth(needleWidth);
        needlePaint.setStyle(Paint.Style.STROKE);

        outerScalePaint.setAntiAlias(true);
        outerScalePaint.setColor(outerScaleColor);
        outerScalePaint.setStrokeWidth(outerScaleWidth);
        outerScalePaint.setStyle(Paint.Style.STROKE);

        rimScalePaint = new Paint();
        rimScalePaint.setAntiAlias(true);
        rimScalePaint.setStrokeWidth(rimScaleWidth);
        rimScalePaint.setStyle(Paint.Style.STROKE);
        rimScalePaint.setColor(getResources().getColor(android.R.color.transparent));
        rimScalePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public void setIncrement(int increment) {
        this.increment = (int) (increment * multiplier);
        text = String.valueOf(increment);
        invalidate();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        // super.onDraw(canvas);

        // Draw the rim
        if (rimWidth > 0) {
            this.canvas.drawArc(circleBounds, startAngle, drawingAngle, false, rimPaint);
        }

        // Draw the bar
        if (barWidth > 0) {
            this.canvas.drawArc(circleBounds, startAngle, increment, false, barPaint);
        }

        // Draw the outer scale
        double angle;
        int startX, startY, stopX, stopY;
        for (int i = startAngle; i <= startAngle + drawingAngle; i = i + stepAngle) {
            angle = Math.toRadians(i);
            startX = centerX + (int) ((fullRadius - outerScaleSize) * Math.cos(angle));
            startY = centerY + (int) ((fullRadius - outerScaleSize) * Math.sin(angle));
            stopX = centerX + (int) (fullRadius * Math.cos(angle));
            stopY = centerY + (int) (fullRadius * Math.sin(angle));
            canvas.drawLine(startX, startY, stopX, stopY, outerScalePaint);
        }

        // Draw the transparent scale
        for (int i = startAngle; i <= startAngle + drawingAngle; i = i + stepAngle) {
            angle = Math.toRadians(i);
            startX = centerX + (int) (rimScaleInnerRadius * Math.cos(angle));
            startY = centerY + (int) (rimScaleInnerRadius * Math.sin(angle));
            stopX = centerX + (int) (rimScaleOuterRadius * Math.cos(angle));
            stopY = centerY + (int) (rimScaleOuterRadius * Math.sin(angle));
            this.canvas.drawLine(startX, startY, stopX, stopY, rimScalePaint);
        }

        canvas.drawBitmap(bitmap, 0, 0, canvasPaint);

        // Draw the needle
        int radius = fullRadius;
        angle = Math.toRadians(startAngle + increment);
        int x = centerX + (int) (radius * Math.cos(angle));
        int y = centerY + (int) (radius * Math.sin(angle));
        canvas.drawLine(centerX, centerY, x, y, needlePaint);

        // Draw the text
        textPaint.setTextSize(toPx(15));
        textX = (getMeasuredWidth() / 2);
        textY = (int) (getMeasuredHeight() - (textSize + toPx(20)));
        canvas.drawText(kmh, textX, textY, textPaint);

        //    textPaint.getTextBounds(text, 0, text.length(), textBounds);
        textPaint.setTextSize(textSize);
        textX = (getMeasuredWidth() / 2);
        // textY = (int) ((getMeasuredHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
        textY = (int) (getMeasuredHeight() - textPaint.getTextSize());
        canvas.drawText(text, textX, textY, textPaint);

        if (debug) {
            canvas.drawRect(circleBounds, boundsPaint);
            canvas.drawRect(textBounds, boundsPaint);
        }
    }

    public int toPx(float dp) {
        return (int) (dp * (getResources().getDisplayMetrics().densityDpi / 160f));
    }

    public int toDp(float px) {
        return (int) (px / (getResources().getDisplayMetrics().densityDpi / 160f));
    }
}