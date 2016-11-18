package com.siziksu.canvas.common;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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

    /**
     * Converts a value into sp unit
     *
     * @param value the value to convert
     *
     * @return the value in sp
     */
    public int valueToSp(float value) {
        return dpToPx(value);
    }

    /**
     * Converts a value into dp unit
     *
     * @param value the value to convert
     *
     * @return the value in dp
     */
    public int valueToDp(float value) {
        return dpToPx(value);
    }

    /**
     * Converts dp units into px
     *
     * @param dp the value to convert
     *
     * @return the value in px
     */
    public int dpToPx(float dp) {
        return (int) (dp * (context.getResources().getDisplayMetrics().densityDpi / 160f));
    }

    /**
     * Converts px units into dp
     *
     * @param px the value to convert
     *
     * @return the value in dp
     */
    public int pxToDp(float px) {
        return (int) (px / (context.getResources().getDisplayMetrics().densityDpi / 160f));
    }
}
