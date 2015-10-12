package com.siziksu.canvas.ui.manager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.siziksu.canvas.R;
import com.siziksu.canvas.commons.Commons;
import com.siziksu.canvas.ui.fragment.FragmentTag;
import com.siziksu.canvas.ui.fragment.HomeFragment;

import java.util.HashMap;
import java.util.Map;

public class ContentManager {

  class Content {

    Fragment fragment;
    String tag;

    Content(Fragment fragment, String tag) {
      this.fragment = fragment;
      this.tag = tag;
    }
  }

  private static ContentManager instance;

  private Map<Integer, Content> tagList;

  public static ContentManager getInstance() {
    if (instance == null) {
      instance = new ContentManager();
    }
    return instance;
  }

  private ContentManager() {}

  /**
   * Initializes the activity with the HomeFragment.java
   *
   * @param manager fragment manager
   */
  public void initialize(FragmentManager manager) {
    try {
      tagList = new HashMap<Integer, Content>();
      setContent(manager, new HomeFragment(), FragmentTag.HOME.getTag(), false);
    } catch (Exception e) {
      if (Commons.getInstance().isDebug()) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Sets a fragment in the view R.id.mainContent
   *
   * @param manager  fragment manager
   * @param fragment the fragment
   * @param newTag   the fragment tag
   * @param back     if the fragment must be added to the back stack
   */
  public void setContent(FragmentManager manager, Fragment fragment, String newTag, boolean back) {
    setContent(manager, fragment, newTag, R.id.mainContent, back);
  }

  /**
   * Sets a fragment in the view specified
   *
   * @param manager  fragment manager
   * @param fragment the fragment
   * @param newTag   the fragment tag
   * @param viewId   the Id of the view that will be the container of the fragment
   * @param back     if the fragment must be added to the back stack
   */
  public void setContent(FragmentManager manager, Fragment fragment, String newTag, int viewId, boolean back) {
    try {
      if (fragment != null) {
        if (!tagList.containsKey(viewId) || !newTag.equals(tagList.get(viewId).tag)) {
          if (back) {
            manager.beginTransaction().replace(viewId, fragment, newTag).addToBackStack(null).commit();
          } else {
            manager.beginTransaction().replace(viewId, fragment, newTag).commit();
          }
          tagList.put(viewId, new Content(fragment, newTag));
        }
      }
    } catch (Exception e) {
      if (Commons.getInstance().isDebug()) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Sets the tag for the fragment in the view R.id.mainContent
   *
   * @param tag the fragment tag
   */
  public void setTag(Fragment fragment, String tag) {
    setTag(R.id.mainContent, fragment, tag);
  }

  /**
   * Sets the tag for the fragment in the specified view
   *
   * @param viewId the Id of the view to set the tag to
   * @param tag    the fragment tag
   */
  public void setTag(int viewId, Fragment fragment, String tag) {
    tagList.put(viewId, new Content(fragment, tag));
  }

  public String getTag(int viewId) {
    return tagList.get(viewId).tag;
  }

  public Fragment getFragment(int viewId) {
    return tagList.get(viewId).fragment;
  }
}
