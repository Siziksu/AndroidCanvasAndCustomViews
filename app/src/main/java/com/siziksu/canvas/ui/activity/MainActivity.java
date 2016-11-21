package com.siziksu.canvas.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.siziksu.canvas.R;
import com.siziksu.canvas.common.Constants;
import com.siziksu.canvas.common.Utils;
import com.siziksu.canvas.ui.fragment.BubbleViewFragment;
import com.siziksu.canvas.ui.fragment.CounterView01Fragment;
import com.siziksu.canvas.ui.fragment.CounterView02Fragment;
import com.siziksu.canvas.ui.fragment.DrawingViewFragment;
import com.siziksu.canvas.ui.fragment.MainFragment;
import com.siziksu.canvas.ui.fragment.RectAndArcFragment;
import com.siziksu.canvas.ui.fragment.RotatingImageViewFragment;
import com.siziksu.canvas.ui.fragment.RoundedViewFragment;
import com.siziksu.canvas.ui.manager.ContentManager;
import com.siziksu.canvas.ui.manager.NavigationManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ContentManager contentManager;
    private NavigationManager navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Utils.applyToolBarStyleWithHome(this, toolbar);
        navigation = new NavigationManager(this);
        navigation.setLayout(toolbar, R.id.drawerLayout, R.id.mainContainer, R.string.drawer_open, R.string.drawer_close)
                  .setNavigationView(R.id.navigationView)
                  .setNavigationViewContent(R.layout.header_drawer, R.menu.menu_drawer)
                  .setNavigationViewItemSelectedListener(this)
                  .syncState();
        contentManager = new ContentManager(getSupportFragmentManager());
        if (savedInstanceState == null) {
            contentManager.show(R.id.mainContent, new MainFragment(), Constants.MAIN);
        }
    }

    @Override
    public void onBackPressed() {
        if (navigation.isOpen()) {
            navigation.close();
        } else {
            navigation.goBackStack();
            if (contentManager.goBackStack()) {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.actionRectAndArc:
                contentManager.show(R.id.mainContent, new RectAndArcFragment(), Constants.RECT_AND_ARC);
                navigation.addBackStack(menuItem);
                break;
            case R.id.actionRotatingImageView:
                contentManager.show(R.id.mainContent, new RotatingImageViewFragment(), Constants.ROTATING_IMAGE_VIEW);
                navigation.addBackStack(menuItem);
                break;
            case R.id.actionDrawingView:
                contentManager.show(R.id.mainContent, new DrawingViewFragment(), Constants.DRAWING_VIEW);
                navigation.addBackStack(menuItem);
                break;
            case R.id.actionRoundedView:
                contentManager.show(R.id.mainContent, new RoundedViewFragment(), Constants.ROUNDED_VIEW);
                navigation.addBackStack(menuItem);
                break;
            case R.id.actionCounterView01:
                contentManager.show(R.id.mainContent, new CounterView01Fragment(), Constants.COUNTER_VIEW_01);
                navigation.addBackStack(menuItem);
                break;
            case R.id.actionCounterView02:
                contentManager.show(R.id.mainContent, new CounterView02Fragment(), Constants.COUNTER_VIEW_02);
                navigation.addBackStack(menuItem);
                break;
            case R.id.actionBubbleView:
                contentManager.show(R.id.mainContent, new BubbleViewFragment(), Constants.BUBBLE_VIEW);
                navigation.addBackStack(menuItem);
                break;
            default:
                break;
        }
        navigation.closeDrawers();
        return true;
    }
}
