package com.siziksu.canvas.common;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public final class Utils {

    public static void applyToolBarStyleWithHome(AppCompatActivity activity, Toolbar toolbar) {
        activity.setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }
}
