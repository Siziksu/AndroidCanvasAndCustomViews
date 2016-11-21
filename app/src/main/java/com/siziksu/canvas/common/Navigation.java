package com.siziksu.canvas.common;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.siziksu.canvas.R;

import java.util.ArrayList;
import java.util.List;

public class Navigation {

    private final Activity activity;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private View mainContainer;

    private final List<MenuItem> backStack = new ArrayList<>();

    public Navigation(Activity activity) {
        this.activity = activity;
    }

    public Navigation setLayout(Toolbar toolbar, int drawerLayout) {
        this.drawerLayout = (DrawerLayout) activity.findViewById(drawerLayout);
        this.drawerToggle = new DrawerToggle(
                activity,
                this.drawerLayout,
                toolbar,
                R.string.drawer_open, R.string.drawer_close
        );
        this.drawerLayout.addDrawerListener(drawerToggle);
        this.mainContainer = this.drawerLayout.findViewById(R.id.mainContainer);
        return this;
    }

    public Navigation setNavigationView(int navigationView) {
        this.navigationView = (NavigationView) activity.findViewById(navigationView);
        return this;
    }

    public Navigation setNavigationViewContent(int menu) {
        navigationView.inflateMenu(menu);
        return this;
    }

    public Navigation setNavigationViewContent(int header, int menu) {
        navigationView.inflateHeaderView(header);
        navigationView.inflateMenu(menu);
        return this;
    }

    public Navigation appendMenuToNavigationViewContent(int menu) {
        navigationView.inflateMenu(menu);
        return this;
    }

    public Navigation setNavigationViewItemSelectedListener(NavigationView.OnNavigationItemSelectedListener listener) {
        navigationView.setNavigationItemSelectedListener(listener);
        return this;
    }

    public Navigation setItemIconTintList(@Nullable ColorStateList tint) {
        navigationView.setItemIconTintList(tint);
        return this;
    }

    public Navigation syncState() {
        this.drawerLayout.post(new Runnable() {

            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });
        return this;
    }

    public void initializeBackStack(int id) {
        Menu menu = navigationView.getMenu();
        addBackStack(menu.findItem(id));
    }

    public void addBackStack(MenuItem item) {
        if (!backStack.isEmpty()) {
            if (!item.equals(backStack.get(backStack.size() - 1))) {
                backStack.add(item);
            }
        } else {
            backStack.add(item);
        }
    }

    public void goBackStack() {
        if (!backStack.isEmpty()) {
            backStack.remove(backStack.size() - 1);
            if (!backStack.isEmpty()) {
                backStack.get(backStack.size() - 1).setChecked(true);
            }
        }
        if (backStack.isEmpty()) {
            int size = getMenu().size();
            for (int i = 0; i < size; i++) {
                getMenu().getItem(i).setChecked(false);
            }
        }
    }

    public Menu getMenu() {
        return navigationView.getMenu();
    }

    public NavigationView getView() {
        return navigationView;
    }

    public DrawerLayout getLayout() {
        return drawerLayout;
    }

    public boolean isOpen() {
        return drawerLayout.isDrawerOpen(navigationView);
    }

    public void close() {
        drawerLayout.closeDrawer(navigationView);
    }

    public void closeDrawers() {
        drawerLayout.closeDrawers();
    }

    private class DrawerToggle extends ActionBarDrawerToggle {

        private final Activity activity;

        DrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
            this.activity = activity;
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            activity.invalidateOptionsMenu();
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            activity.invalidateOptionsMenu();
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            super.onDrawerSlide(drawerView, slideOffset);
            mainContainer.setX(slideOffset * drawerView.getWidth());
            activity.invalidateOptionsMenu();
        }
    }
}

