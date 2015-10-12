package com.siziksu.canvas.commons;

public class Commons {

  private static Commons instance;

  private BuildVariant buildVariant;

  public static Commons getInstance() {
    if (instance == null) {
      instance = new Commons();
    }
    return instance;
  }

  private Commons() {
    this.buildVariant = BuildVariant.DEBUG;
  }

  public boolean isDebug() {
    return buildVariant == BuildVariant.DEBUG;
  }
}
