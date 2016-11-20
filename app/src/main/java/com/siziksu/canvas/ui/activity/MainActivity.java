package com.siziksu.canvas.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.siziksu.canvas.R;
import com.siziksu.canvas.common.ActivityCommon;
import com.siziksu.canvas.common.Constants;
import com.siziksu.canvas.common.Navigation;
import com.siziksu.canvas.ui.fragment.BubbleViewFragment;
import com.siziksu.canvas.ui.fragment.CounterView01Fragment;
import com.siziksu.canvas.ui.fragment.CounterView02Fragment;
import com.siziksu.canvas.ui.fragment.DrawingViewFragment;
import com.siziksu.canvas.ui.fragment.MainFragment;
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

        contentManager = new ContentManager(getSupportFragmentManager());
        if (savedInstanceState == null) {
            contentManager.show(R.id.mainContent, new MainFragment(), Constants.MAIN, false);
        }
    }

    @Override
    public void onBackPressed() {
        if (navigation.isOpen()) {
            navigation.close();
        } else {
            navigation.goBackStack();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.actionRectAndArc:
                contentManager.show(R.id.mainContent, new RectAndArcFragment(), Constants.RECT_AND_ARC, true);
                break;
            case R.id.actionRotatingImageView:
                contentManager.show(R.id.mainContent, new RotatingImageViewFragment(), Constants.ROTATING_IMAGE_VIEW, true);
                break;
            case R.id.actionDrawingView:
                contentManager.show(R.id.mainContent, new DrawingViewFragment(), Constants.DRAWING_VIEW, true);
                break;
            case R.id.actionRoundedView:
                contentManager.show(R.id.mainContent, new RoundedViewFragment(), Constants.ROUNDED_VIEW, true);
                break;
            case R.id.actionCounterView01:
                contentManager.show(R.id.mainContent, new CounterView01Fragment(), Constants.COUNTER_VIEW_01, true);
                break;
            case R.id.actionCounterView02:
                contentManager.show(R.id.mainContent, new CounterView02Fragment(), Constants.COUNTER_VIEW_02, true);
                break;
            case R.id.actionBubbleView:
                contentManager.show(R.id.mainContent, new BubbleViewFragment(), Constants.BUBBLE_VIEW, true);
                break;
            default:
                break;
        }
        navigation.addBackStack(menuItem);
        navigation.closeDrawers();
        return true;
    }
}
