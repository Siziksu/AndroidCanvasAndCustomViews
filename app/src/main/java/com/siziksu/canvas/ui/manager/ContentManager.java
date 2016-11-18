package com.siziksu.canvas.ui.manager;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public final class ContentManager {

    private final FragmentManager manager;

    private String section = "";

    public ContentManager(FragmentManager fragmentManager) {
        manager = fragmentManager;
    }

    public void show(@IdRes int view, Fragment fragment, String tag, boolean value) {
        if (!section.equals(tag)) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(view, fragment, tag);
            if (value) {
                transaction.addToBackStack(null);
            }
            transaction.commit();
            section = tag;
        }
    }

    public String getSection() {
        return section;
    }
}
