package com.siziksu.canvas.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.siziksu.canvas.R;
import com.siziksu.canvas.ui.commons.ActivityCommon;
import com.siziksu.canvas.ui.commons.Navigation;
import com.siziksu.canvas.ui.fragment.CounterView01Fragment;
import com.siziksu.canvas.ui.fragment.CounterView02Fragment;
import com.siziksu.canvas.ui.fragment.DrawingViewFragment;
import com.siziksu.canvas.ui.fragment.FragmentTag;
import com.siziksu.canvas.ui.fragment.HomeFragment;
import com.siziksu.canvas.ui.fragment.RectAndArcFragment;
import com.siziksu.canvas.ui.fragment.RotatingImageViewFragment;
import com.siziksu.canvas.ui.fragment.RoundedViewFragment;
import com.siziksu.canvas.ui.manager.ContentManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

  private ContentManager contentManager;

  private Navigation navigation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar defaultToolbar = (Toolbar) findViewById(R.id.defaultToolbar);
    ActivityCommon.getInstance(this).applyToolBarStyleWithHome(this, defaultToolbar);

    navigation = new Navigation(this);
    navigation.setLayout(defaultToolbar, R.id.drawerLayout)
        .setNavigationView(R.id.navigationView)
        .setNavigationViewContent(R.layout.header_drawer, R.menu.menu_drawer)
        .setNavigationViewItemSelectedListener(this)
        .syncState();

    contentManager = ContentManager.getInstance();
    if (savedInstanceState == null) {
      contentManager.initialize(getSupportFragmentManager());
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }
  }

  @Override
  public void onBackPressed() {
    if (navigation.isOpen()) {
      navigation.close();
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onNavigationItemSelected(MenuItem menuItem) {
    Fragment fragment = null;
    String newTag = null;
    int viewId = 0;
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    switch (menuItem.getItemId()) {
      case R.id.actionHome:
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        fragment = new HomeFragment();
        newTag = FragmentTag.HOME.getTag();
        viewId = R.id.mainContent;
        break;
      case R.id.actionRectAndArc:
        fragment = new RectAndArcFragment();
        newTag = FragmentTag.RECT_AND_ARC.getTag();
        viewId = R.id.mainContent;
        break;
      case R.id.actionRotatingImageView:
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        fragment = new RotatingImageViewFragment();
        newTag = FragmentTag.ROTATING_IMAGE_VIEW.getTag();
        viewId = R.id.mainContent;
        break;
      case R.id.actionDrawingView:
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        fragment = new DrawingViewFragment();
        newTag = FragmentTag.DRAWING_VIEW.getTag();
        viewId = R.id.mainContent;
        break;
      case R.id.actionRoundedView:
        fragment = new RoundedViewFragment();
        newTag = FragmentTag.ROUNDED_VIEW.getTag();
        viewId = R.id.mainContent;
        break;
      case R.id.actionCounterView01:
        fragment = new CounterView01Fragment();
        newTag = FragmentTag.COUNTER_VIEW_01.getTag();
        viewId = R.id.mainContent;
        break;
      case R.id.actionCounterView02:
        fragment = new CounterView02Fragment();
        newTag = FragmentTag.COUNTER_VIEW_02.getTag();
        viewId = R.id.mainContent;
        break;
    }
    contentManager.setContent(getSupportFragmentManager(), fragment, newTag, viewId, true);
    navigation.closeDrawers();
    return true;
  }
}
