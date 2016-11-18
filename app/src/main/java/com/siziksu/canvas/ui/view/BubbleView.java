package com.siziksu.canvas.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.siziksu.canvas.R;

public class BubbleView extends View {

    public static final int TOP_LEFT = 0;
    public static final int TOP_RIGHT = 1;
    public static final int BOTTOM_LEFT = 2;
    public static final int BOTTOM_RIGHT = 3;

    private int layout_height = 0;
    private int layout_width = 0;

    private int radius;

    private int miniBubbleAngle;
    private float miniBubbleRadius;
    private float miniBubbleCx;
    private float miniBubbleCy;
    private int miniBubbleColor;
    private Paint miniBubblePaint = new Paint();

    private int miniBubbleValue = 0;
    private Paint miniItemsPaint = new Paint();
    private int miniBubbleTextColor = 0xAAFFFFFF;
    private int miniBubblePosition = -45;

    private String text;
    private int textStyle = Typeface.NORMAL;
    private int textColor = 0xAA000000;
    private float textSize = 30;
    private TextPaint textPaint = new TextPaint();

    private float textPadding = 0;
    private float bubbleHorizontalCompensation = 0;
    private int bubbleVerticalCompensation = 0;
    private Drawable bubbleBackgroundDrawable;

    private Drawable bubbleCategoryDrawable;

    public BubbleView(Context context) {
        super(context);
        init(context, null);
    }

    public BubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BubbleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.BubbleView));
        }
    }

    /**
     * Parse the attributes passed to the view from the XML
     *
     * @param attributes the attributes to parse
     */
    private void parseAttributes(TypedArray attributes) {
        text = attributes.getString(R.styleable.BubbleView_bubbleText);
        textColor = attributes.getColor(R.styleable.BubbleView_android_textColor, textColor);
        textSize = attributes.getDimension(R.styleable.BubbleView_android_textSize, textSize);
        textStyle = attributes.getInteger(R.styleable.BubbleView_android_textStyle, textStyle);
        textPadding = attributes.getDimension(R.styleable.BubbleView_bubbleTextPadding, textPadding);
        bubbleBackgroundDrawable = attributes.getDrawable(R.styleable.BubbleView_bubbleBackground);
        bubbleCategoryDrawable = attributes.getDrawable(R.styleable.BubbleView_bubbleCategoryDrawable);
        miniBubbleValue = attributes.getInteger(R.styleable.BubbleView_bubbleMiniValue, miniBubbleValue);
        miniBubbleColor = attributes.getColor(R.styleable.BubbleView_bubbleMiniColor, miniBubbleColor);
        miniBubbleTextColor = attributes.getColor(R.styleable.BubbleView_bubbleMiniTextColor, miniBubbleTextColor);
        miniBubblePosition = attributes.getInteger(R.styleable.BubbleView_bubbleMiniPosition, miniBubblePosition);
        attributes.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    /*
     * RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
     * params.topMargin | params.bottomMargin | params.leftMargin | params.rightMargin
     *
     * this.getPaddingLeft() | this.getPaddingRight() | this.getPaddingTop() | this.getPaddingBottom()
     */
        layout_width = w - this.getPaddingLeft() - this.getPaddingRight();
        layout_height = h - this.getPaddingTop() - this.getPaddingBottom();
        text = text != null ? text : "";
        setup();
        setupPaints();
        invalidate();
    }

    private void setup() {
        setMiniBubbleAngle();
        float cx = layout_width / 2;
        float cy = layout_height / 2;
        calculateCompensations();
        int diameter = Math.min(layout_width, layout_height);
        radius = diameter / 2;
        float cos = (float) Math.cos(Math.toRadians(miniBubbleAngle));
        float sin = (float) Math.sin(Math.toRadians(miniBubbleAngle));
        miniBubbleRadius = (float) (diameter * 0.085); // 8.5% of the diameter
        miniBubbleCx = cx + radius * cos;
        miniBubbleCy = cy + radius * sin;
        if (bubbleBackgroundDrawable != null) {
            bubbleBackgroundDrawable.setBounds(
                    (int) (bubbleHorizontalCompensation / 2),
                    bubbleVerticalCompensation / 2,
                    (int) (layout_width - (bubbleHorizontalCompensation / 2)),
                    layout_height - (bubbleVerticalCompensation / 2)
            );
        }
        if (bubbleCategoryDrawable != null) {
            float categoryDrawableDimension = (float) (diameter * 0.225);
            float categoryX = cx - categoryDrawableDimension / 2;
            float categoryY = cy - categoryDrawableDimension;
            float categoryWidth = categoryX + categoryDrawableDimension;
            float categoryHeight = categoryY + categoryDrawableDimension;
            bubbleCategoryDrawable.setBounds(
                    (int) categoryX,
                    (int) categoryY,
                    (int) categoryWidth,
                    (int) categoryHeight
            );
        }
    }

    private void setMiniBubbleAngle() {
        if (miniBubblePosition == TOP_LEFT) {
            miniBubbleAngle = -135;
        }
        if (miniBubblePosition == TOP_RIGHT) {
            miniBubbleAngle = -45;
        }
        if (miniBubblePosition == BOTTOM_LEFT) {
            miniBubbleAngle = 135;
        }
        if (miniBubblePosition == BOTTOM_RIGHT) {
            miniBubbleAngle = 45;
        }
    }

    private void calculateCompensations() {
        if (layout_width != layout_height) {
            if (layout_width > layout_height) {
                bubbleHorizontalCompensation = layout_width - layout_height;
            }
            if (layout_height > layout_width) {
                bubbleVerticalCompensation = layout_height - layout_width;
            }
        }
    }

    private void setupPaints() {
        miniBubblePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        miniBubblePaint.setAntiAlias(true);
        miniBubblePaint.setColor(miniBubbleColor);

        miniItemsPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        miniItemsPaint.setAntiAlias(true);
        miniItemsPaint.setColor(miniBubbleTextColor);
        miniItemsPaint.setTextSize(textSize);
        miniItemsPaint.setTypeface(Typeface.create(Typeface.DEFAULT, textStyle));
        miniItemsPaint.setTextAlign(Paint.Align.CENTER);

        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, textStyle));
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (bubbleBackgroundDrawable != null) {
            bubbleBackgroundDrawable.draw(canvas);
        }
        if (bubbleCategoryDrawable != null) {
            bubbleCategoryDrawable.draw(canvas);
        }
        if (miniBubbleValue != 0) {
            canvas.drawCircle(miniBubbleCx, miniBubbleCy, miniBubbleRadius, miniBubblePaint);
        }
        drawMultiLineEllipsizedText(canvas, textPaint, text, layout_width);
        if (miniBubbleValue != 0) {
            canvas.drawText(String.valueOf(miniBubbleValue), miniBubbleCx, miniBubbleCy - ((miniItemsPaint.descent() + miniItemsPaint.ascent()) / 2), miniItemsPaint);
        }
    }

    public void drawMultiLineEllipsizedText(final Canvas canvas, final TextPaint textPaint, final String text, int layout_width) {
        int width = layout_width - (int) textPadding - (int) bubbleHorizontalCompensation;
        StaticLayout layout = new StaticLayout(text, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        canvas.save();
        float x = radius + bubbleHorizontalCompensation / 2;
        float y = radius + bubbleVerticalCompensation / 2;
        canvas.translate(x, y);
        layout.draw(canvas);
        canvas.restore();
    }

    public void setBubbleBackground(Drawable drawable) {
        this.bubbleBackgroundDrawable = drawable;
    }

    public void setCategoryDrawable(Drawable drawable) {
        this.bubbleCategoryDrawable = drawable;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextColor(int color) {
        this.textColor = color;
    }

    public void setTextSize(float size) {
        this.textSize = size;
    }

    public void setTextStyle(int style) {
        this.textStyle = style;
    }

    public void setTextPadding(float padding) {
        this.textPadding = padding;
    }

    /**
     * Sets the number of items that the bubble will show
     */
    public void setMiniBubbleValue(int value) {
        this.miniBubbleValue = value;
    }

    public void setMiniBubbleColor(int color) {
        this.miniBubbleColor = color;
    }

    public void setMiniBubbleTextColor(int color) {
        this.miniBubbleTextColor = color;
    }

    /**
     * Sets the position of the mini bubble using BubbleView constants (TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT)
     */
    public void setMiniBubblePosition(int position) {
        this.miniBubblePosition = position;
    }
}