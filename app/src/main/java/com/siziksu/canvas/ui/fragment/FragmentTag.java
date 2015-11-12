package com.siziksu.canvas.ui.fragment;

public enum FragmentTag {
  MAIN(0, "main_fragment", "Home"),
  RECT_AND_ARC(1, "rect_and_arc_fragment", "Rect and Arc"),
  ROTATING_IMAGE_VIEW(2, "rotating_image_view_fragment", "Rotating Image View"),
  DRAWING_VIEW(3, "drawing_view_fragment", "Drawing View"),
  ROUNDED_VIEW(4, "rounded_view_fragment", "Rounded View"),
  COUNTER_VIEW_01(5, "counter_view_01_fragment", "Counter View 01"),
  COUNTER_VIEW_02(6, "counter_view_02_fragment", "Counter View 02"),
  BUBBLE_VIEW(7, "bubble_view_fragment", "Bubble View");

  private int index;
  private String tag;
  private String title;

  FragmentTag(int index, String tag, String title) {
    this.index = index;
    this.tag = tag;
    this.title = title;
  }

  public int getIndex() {
    return index;
  }

  public String getTag() {
    return tag;
  }

  public String getTitle() {
    return title;
  }
}

