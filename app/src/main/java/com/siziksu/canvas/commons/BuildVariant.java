package com.siziksu.canvas.commons;

public enum BuildVariant {

  DEBUG(0, "DEBUG"), RELEASE(1, "RELEASE");

  private int index;
  private String name;

  BuildVariant(int index, String name) {
    this.index = index;
    this.name = name;
  }

  public int getIndex() {
    return index;
  }

  public String getName() {
    return name;
  }
}

