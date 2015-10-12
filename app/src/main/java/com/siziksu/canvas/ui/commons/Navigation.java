package com.siziksu.canvas.ui.commons;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.siziksu.canvas.R;

public class Navigation {

  private Activity activity;

  private NavigationView navigationView;
  private DrawerLayout drawerLayout;
  private ActionBarDrawerToggle drawerToggle;

  public Navigation(Activity activity) {
    this.activity = activity;
  }

  public Navigation setLayout(Toolbar toolbar, int drawerLayout) {
    this.drawerLayout = (DrawerLayout) activity.findViewById(drawerLayout);
    drawerToggle = new DrawerToggle(activity, this.drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
    this.drawerLayout.setDrawerListener(drawerToggle);
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

  public Navigation syncState() {
    this.drawerLayout.post(new Runnable() {

      @Override
      public void run() {
        drawerToggle.syncState();
      }
    });
    return this;
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

  class DrawerToggle extends ActionBarDrawerToggle {

    private Activity activity;

    public DrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
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
      activity.invalidateOptionsMenu();
    }
  }
}
