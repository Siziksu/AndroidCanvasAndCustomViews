package com.siziksu.canvas.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RotatingImageView extends ImageView {

  private Paint paint;

  private int xRotatingAngle = 0;
  private int yRotatingAngle = 0;
  private int zTurningAngle = 0;

  private Bitmap bitmap;
  private Canvas canvas;
  private Matrix matrix;
  private Camera camera;

  public RotatingImageView(Context context) {
    super(context);
    init();
  }

  public RotatingImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public RotatingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public RotatingImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // Create matrix
    matrix = new Matrix();
    // Create a camera
    camera = new Camera();
  }

  @Override
  protected void onDraw(@NonNull Canvas canvas) {
    // Calculate center
    int halfWidth = getMeasuredWidth() / 2;
    int halfHeight = getMeasuredHeight() / 2;
    // Turn the camera in the three axes
    camera.rotateX(xRotatingAngle);
    camera.rotateY(yRotatingAngle);
    camera.rotateZ(zTurningAngle);
    // Extract the matrix from the camera
    camera.getMatrix(matrix);
    // Make the matrix to move image center to 0,0 before turning
    matrix.preTranslate(-halfWidth, -halfHeight);
    // Make the matrix move back after turning
    matrix.postTranslate(halfWidth, halfHeight);
    if (bitmap == null) {
      // Create a new bitmap with same size as view
      bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
      // Create the canvas of the new bitmap
      this.canvas = new Canvas(bitmap);
    }
    // Draw the image (relay on ImageView)
    super.onDraw(this.canvas);
    // Draw the bitmap on main canvas using the matrix
    canvas.drawBitmap(bitmap, matrix, paint);
  }

  public void setTurning(int x, int y, int z) {
    xRotatingAngle = x;
    yRotatingAngle = y;
    zTurningAngle = z;
    invalidate();
  }
}
