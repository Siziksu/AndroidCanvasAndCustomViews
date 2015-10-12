package com.siziksu.canvas.ui.commons;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class ActivityCommon {

  private final Context context;

  private static ActivityCommon instance;

  public static ActivityCommon getInstance(Context context) {
    if (instance == null) {
      instance = new ActivityCommon(context);
    }
    return instance;
  }

  private ActivityCommon(Context context) {
    this.context = context;
  }

  public void setToolbar(AppCompatActivity activity, Toolbar toolbar) {
    activity.setSupportActionBar(toolbar);
  }

  public void applyToolBarStyleWithHome(AppCompatActivity activity, Toolbar toolbar) {
    setToolbar(activity, toolbar);
    android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayShowHomeEnabled(true);
    }
  }

  public void applyToolBarStyleWithHomeUp(AppCompatActivity activity, Toolbar toolbar) {
    setToolbar(activity, toolbar);
    android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayShowHomeEnabled(true);
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  public void hideSoftKeyboard(AppCompatActivity activity) {
    if (activity.getCurrentFocus() != null) {
      InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
  }

  public void showMessage(String message) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
  }
}
