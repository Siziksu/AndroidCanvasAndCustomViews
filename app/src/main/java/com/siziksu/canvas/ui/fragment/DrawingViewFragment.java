package com.siziksu.canvas.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siziksu.canvas.R;
import com.siziksu.canvas.ui.manager.ContentManager;

public class DrawingViewFragment extends Fragment {

  public DrawingViewFragment() {
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
    return inflater.inflate(R.layout.fragment_drawing_view, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ContentManager.getInstance().setTag(this, FragmentTag.DRAWING_VIEW.getTag());
    getActivity().setTitle(FragmentTag.DRAWING_VIEW.getTitle());
  }

  @Override
  public void onResume() {
    super.onResume();
  }
}
