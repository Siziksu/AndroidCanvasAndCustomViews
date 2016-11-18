package com.siziksu.canvas.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.siziksu.canvas.R;

public class RoundedView extends View {

    private boolean debug = false;

    private int layout_height = 0;
    private int layout_width = 0;

    private int innerCircleRadius = 80;

    private int rimWidth = 20;
    private int barColor = 0xAA000000;
    private int barWidth = 20;

    private int rimColor = 0xAADDDDDD;

    private int contourColor = 0xAA000000;
    private float rimContourSize = 0;

    private int centerX;
    private int centerY;
    private int fullRadius;
    private int innerCircleColor = 0x90C3CCEF;

    private Paint boundsPaint = new Paint();
    private Paint innerCirclePaint = new Paint();
    private Paint barPaint = new Paint();
    private Paint rimPaint = new Paint();
    private Paint contourPaint = new Paint();
    private Paint textPaint = new Paint();
    private Paint gradientPaint = new Paint();

    private RectF circleBounds = new RectF();
    private RectF rimOuterContour = new RectF();
    private RectF rimInnerContour = new RectF();

    private Rect textBounds = new Rect();

    private int increment;

    private int textStyle = Typeface.NORMAL;
    private int textColor = 0xAA000000;
    private float textSize = 30;
    private String text = "0";
    private int textX;
    private int textY;

    public RoundedView(Context context) {
        super(context);
        init(context, null);
    }

    public RoundedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundedView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.RoundedView));
        }
    }

    /**
     * Parse the attributes passed to the view from the XML
     *
     * @param attributes the attributes to parse
     */
    private void parseAttributes(TypedArray attributes) {
        barColor = attributes.getColor(R.styleable.RoundedView_barColor, barColor);
        barWidth = (int) attributes.getDimension(R.styleable.RoundedView_barWidth, barWidth);
        rimWidth = (int) attributes.getDimension(R.styleable.RoundedView_rimWidth, rimWidth);
        rimColor = attributes.getColor(R.styleable.RoundedView_rimColor, rimColor);
        innerCircleColor = attributes.getColor(R.styleable.RoundedView_innerCircleColor, innerCircleColor);
        contourColor = attributes.getColor(R.styleable.RoundedView_rimContourColor, contourColor);
        rimContourSize = attributes.getDimension(R.styleable.RoundedView_rimContourSize, rimContourSize);
        textColor = attributes.getColor(R.styleable.RoundedView_android_textColor, textColor);
        textSize = attributes.getDimension(R.styleable.RoundedView_android_textSize, textSize);
        textStyle = attributes.getInt(R.styleable.RoundedView_android_textStyle, textStyle);
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

        setupBounds();
        setupPaints();
        invalidate();
    }

    private void setupBounds() {
        // Width should equal to Height, find the min value to setup the circle
        int minWidthHeightValue = Math.min(layout_width, layout_height);
        int maxBarRimValue = Math.max(barWidth, rimWidth);
        fullRadius = minWidthHeightValue / 2;

        centerX = layout_width / 2;
        centerY = layout_height / 2;
        innerCircleRadius = fullRadius - maxBarRimValue;

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

        if (rimContourSize > 0) {
            rimInnerContour = new RectF(circleBounds.left + (rimWidth / 2.0f) + (rimContourSize / 2.0f), circleBounds.top +
                                                                                                         (rimWidth / 2.0f) + (rimContourSize / 2.0f), circleBounds.right - (rimWidth / 2.0f) -
                                                                                                                                                      (rimContourSize / 2.0f), circleBounds.bottom - (rimWidth / 2.0f) - (rimContourSize / 2.0f));

            rimOuterContour = new RectF(circleBounds.left - (rimWidth / 2.0f) - (rimContourSize / 2.0f), circleBounds.top -
                                                                                                         (rimWidth / 2.0f) - (rimContourSize / 2.0f), circleBounds.right + (rimWidth / 2.0f) +
                                                                                                                                                      (rimContourSize / 2.0f), circleBounds.bottom + (rimWidth / 2.0f) + (rimContourSize / 2.0f));
        }
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

        innerCirclePaint.setAntiAlias(true);
        innerCirclePaint.setColor(innerCircleColor);
        innerCirclePaint.setStyle(Paint.Style.FILL);

        if (rimContourSize > 0) {
            contourPaint.setAntiAlias(true);
            contourPaint.setColor(contourColor);
            contourPaint.setStrokeWidth(rimContourSize);
            contourPaint.setStyle(Paint.Style.STROKE);
        }

        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, textStyle));
        textPaint.setTextAlign(Paint.Align.CENTER);

        RadialGradient radial = new RadialGradient(centerX, centerY, fullRadius, 0x00000000, rimColor, TileMode.CLAMP);
        gradientPaint = new Paint();
        gradientPaint.setDither(true);
        gradientPaint.setAntiAlias(true);
        gradientPaint.setShader(radial);
        gradientPaint.setStrokeWidth(barWidth);
        gradientPaint.setStyle(Paint.Style.STROKE);

        LinearGradient linear = new LinearGradient(0, 0, 0, getHeight(), 0x00000000, rimColor, Shader.TileMode.MIRROR);
        gradientPaint.setShader(linear);
    }

    public void setIncrement(int increment) {
        this.increment = (int) (increment * 1.8f);
        text = String.valueOf(increment);
        invalidate();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        // super.onDraw(canvas);

        // Draw the inner circle
        canvas.drawCircle(centerX, centerY, innerCircleRadius, innerCirclePaint);

        // Draw the rim
        if (rimWidth > 0) {
            canvas.drawArc(circleBounds, 360, 360, false, rimPaint);
        }

        // Draw the rim contour
        if (rimContourSize > 0) {
            canvas.drawArc(rimInnerContour, 360, 360, false, contourPaint);
            canvas.drawArc(rimOuterContour, 360, 360, false, contourPaint);
        }

        // Draw the bar
        if (barWidth > 0) {
            canvas.drawArc(circleBounds, 90 - increment, (increment * 2), false, barPaint);
        }

        // Draw the text
        // textPaint.getTextBounds(text, 0, text.length(), textBounds);
        textX = (getMeasuredWidth() / 2);
        textY = (int) ((getMeasuredHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
        canvas.drawText(text, textX, textY, textPaint);

        if (debug) {
            canvas.drawRect(circleBounds, boundsPaint);
            canvas.drawRect(textBounds, boundsPaint);
        }
    }
}