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
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.siziksu.canvas.R;

public class RoundedImageView extends ImageView {

  private int layout_height = 0;
  private int layout_width = 0;

  private int paddingTop = 2;
  private int paddingBottom = 2;
  private int paddingLeft = 2;
  private int paddingRight = 2;

  private int barWidth = 20;
  private int barColor = 0xAA000000;
  private Paint barPaint = new Paint();
  private RectF circleBounds = new RectF();

  private Bitmap bitmapDrawable;
  private Bitmap bitmapMutable;
  private Bitmap roundedBitmap;
  private Drawable drawable;

  public RoundedImageView(Context context) {
    super(context);
    init(context, null);
  }

  public RoundedImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    drawable = getDrawable();
    if (attrs != null) {
      parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.RoundedImageView));
    }
  }

  /**
   * Parse the attributes passed to the view from the XML
   *
   * @param attributes the attributes to parse
   */
  private void parseAttributes(TypedArray attributes) {
    barColor = attributes.getColor(R.styleable.RoundedImageView_rivBarColor, barColor);
    barWidth = (int) attributes.getDimension(R.styleable.RoundedImageView_rivBarWidth, barWidth);
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

  /**
   * Set the bounds of the component
   */
  private void setupBounds() {
    // Width should equal to Height, find the min value to setup the circle
    int minValue = Math.min(layout_width, layout_height);

    // Calc the Offset if needed
    int xOffset = layout_width - minValue;
    int yOffset = layout_height - minValue;

    // Add the offset
    paddingTop = this.getPaddingTop() + (yOffset / 2);
    paddingBottom = this.getPaddingBottom() + (yOffset / 2);
    paddingLeft = this.getPaddingLeft() + (xOffset / 2);
    paddingRight = this.getPaddingRight() + (xOffset / 2);
    circleBounds = new RectF(paddingLeft + barWidth, paddingTop + barWidth, this.getLayoutParams().width - paddingRight - barWidth, this.getLayoutParams().height - paddingBottom - barWidth);
  }

  /**
   * Set the properties of the paints we're using to draw the progress wheel
   */
  private void setupPaints() {
    barPaint.setAntiAlias(true);
    barPaint.setColor(barColor);
    barPaint.setStrokeWidth(barWidth);
    barPaint.setStyle(Paint.Style.STROKE);
  }

  public static Bitmap getCircleCroppedBitmap(Bitmap bitmap, int radius) {
    Bitmap finalBitmap;
    if (bitmap.getWidth() != radius || bitmap.getHeight() != radius) {
      finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius, false);
    } else {
      finalBitmap = bitmap;
    }
    Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(), finalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(output);
    final Paint paint = new Paint();
    final Rect rect = new Rect(0, 0, finalBitmap.getWidth(), finalBitmap.getHeight());
    paint.setAntiAlias(true);
    paint.setFilterBitmap(true);
    paint.setDither(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(0xFFBAB399);
    canvas.drawCircle(finalBitmap.getWidth() / 2 + 0.7f, finalBitmap.getHeight() / 2 + 0.7f, finalBitmap.getWidth() / 2 + 0.1f, paint);
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    canvas.drawBitmap(finalBitmap, rect, rect, paint);
    return output;
  }

  @Override
  protected void onDraw(@NonNull Canvas canvas) {
    super.onDraw(canvas);
    //    if (drawable == null) {
    //      return;
    //    } else if (getMeasuredWidth() == 0 || getMeasuredHeight() == 0) {
    //      return;
    //    }
    //    if (roundedBitmap == null) {
    //      bitmapDrawable = ((BitmapDrawable) drawable).getBitmap();
    //      bitmapMutable = bitmapDrawable.copy(Bitmap.Config.ARGB_8888, true);
    //      bitmapDrawable.recycle();
    //    }
    //    int w = getWidth();
    //    int h = getHeight();
    //    if (roundedBitmap == null) {
    //      roundedBitmap = getCircleCroppedBitmap(bitmapMutable, w / 2);
    //      bitmapMutable.recycle();
    //    }
    //    canvas.drawBitmap(roundedBitmap, roundedBitmap.getWidth() / 2, roundedBitmap.getHeight() / 2, null);
    // Draw the bar
    canvas.drawArc(circleBounds, -90, 360, false, barPaint);
  }
}