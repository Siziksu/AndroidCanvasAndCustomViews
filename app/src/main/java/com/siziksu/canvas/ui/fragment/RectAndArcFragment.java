package com.siziksu.canvas.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siziksu.canvas.R;
import com.siziksu.canvas.ui.manager.ContentManager;

public class RectAndArcFragment extends Fragment {

  public RectAndArcFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_rect_and_arc, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ContentManager.getInstance().setTag(this, FragmentTag.RECT_AND_ARC.getTag());
    getActivity().setTitle(FragmentTag.RECT_AND_ARC.getTitle());
  }

  @Override
  public void onResume() {
    super.onResume();
  }
}
