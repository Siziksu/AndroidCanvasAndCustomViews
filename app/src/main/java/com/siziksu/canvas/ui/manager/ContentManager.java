package com.siziksu.canvas.ui.manager;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public final class ContentManager {

    private final FragmentManager manager;

    private List<Pair> pairs = new ArrayList<>();

    public ContentManager(FragmentManager fragmentManager) {
        manager = fragmentManager;
    }

    public void show(@IdRes int view, Fragment fragment, String tag) {
        show(view, fragment, tag, true);
    }

    public void show(@IdRes int view, Fragment fragment, String tag, boolean value) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(view, fragment, tag);
        transaction.commit();
        if (!pairs.isEmpty()) {
            Pair pair = pairs.get(pairs.size() - 1);
            if (pair == null) {
                pairs.remove(pairs.size() - 1);
            }
        }
        if (value) {
            pairs.add(new Pair(view, tag, fragment));
        } else {
            pairs.add(null);
        }
    }

    public boolean goBackStack() {
        if (!pairs.isEmpty()) {
            pairs.remove(pairs.size() - 1);
            if (!pairs.isEmpty()) {
                Pair pair = pairs.get(pairs.size() - 1);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(pair.view, pair.fragment, pair.tag);
                transaction.commit();
            }
        }
        return pairs.isEmpty();
    }

    public String getSection() {
        return pairs.get(pairs.size() - 1).tag;
    }

    private final class Pair {

        int view;
        String tag;
        Fragment fragment;

        Pair(int view, String tag, Fragment fragment) {
            this.view = view;
            this.tag = tag;
            this.fragment = fragment;
        }
    }
}
