package com.siziksu.canvas.ui.manager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.siziksu.canvas.R;
import com.siziksu.canvas.ui.fragment.FragmentTag;
import com.siziksu.canvas.ui.fragment.MainFragment;

import java.util.HashMap;
import java.util.Map;

public class ContentManager {

  class Content {

    Fragment fragment;
    FragmentTag tag;

    Content(Fragment fragment, FragmentTag tag) {
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

  private ContentManager() {
  }

  /**
   * Initializes the activity with the MainFragment.java
   *
   * @param manager fragment manager
   */
  public void initialize(FragmentManager manager) {
    try {
      tagList = new HashMap<Integer, Content>();
      setContent(manager, new MainFragment(), FragmentTag.MAIN, false);
    } catch (Exception e) {
      e.printStackTrace();
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
  public void setContent(FragmentManager manager, Fragment fragment, FragmentTag newTag, boolean back) {
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
  public void setContent(FragmentManager manager, Fragment fragment, FragmentTag newTag, int viewId, boolean back) {
    try {
      if (fragment != null) {
        if (!tagList.containsKey(viewId) || !newTag.equals(tagList.get(viewId).tag)) {
          if (back) {
            manager.beginTransaction().replace(viewId, fragment, newTag.getTag()).addToBackStack(null).commit();
          } else {
            manager.beginTransaction().replace(viewId, fragment, newTag.getTag()).commit();
          }
          tagList.put(viewId, new Content(fragment, newTag));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Sets the tag for the fragment in the specified view
   *
   * @param viewId the Id of the view to set the tag to
   * @param tag    the fragment tag
   */
  public void setTag(int viewId, Fragment fragment, FragmentTag tag) {
    tagList.put(viewId, new Content(fragment, tag));
  }

  /**
   * Sets the tag for the fragment in the specified view
   *
   * @param viewId the Id of the view to set the tag to
   * @param tag    the fragment tag
   */
  public void setTagAndTitle(int viewId, Fragment fragment, FragmentTag tag) {
    setTag(viewId, fragment, tag);
    fragment.getActivity().setTitle(tag.getTitle());
  }

  /**
   * Sets the tag for the fragment in the view R.id.mainContent
   *
   * @param tag the fragment tag
   */
  public void setTag(Fragment fragment, FragmentTag tag) {
    setTag(R.id.mainContent, fragment, tag);
  }

  /**
   * Sets the tag for the fragment in the view R.id.mainContent and sets
   * the title of the activity
   *
   * @param tag the fragment tag
   */
  public void setTagAndTitle(Fragment fragment, FragmentTag tag) {
    setTagAndTitle(R.id.mainContent, fragment, tag);
  }

  public String getTag(int viewId) {
    return tagList.get(viewId).tag.getTag();
  }

  public Fragment getFragment(int viewId) {
    return tagList.get(viewId).fragment;
  }
}
