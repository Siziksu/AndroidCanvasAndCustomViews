package com.siziksu.canvas.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.siziksu.canvas.R;
import com.siziksu.canvas.ui.manager.ContentManager;

public class BubbleViewFragment extends Fragment implements View.OnClickListener {

  public BubbleViewFragment() {
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
    return inflater.inflate(R.layout.fragment_bubble_view, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ContentManager.getInstance().setTagAndTitle(this, FragmentTag.BUBBLE_VIEW);

    getActivity().findViewById(R.id.bubbleView1).setOnClickListener(this);
    getActivity().findViewById(R.id.bubbleView2).setOnClickListener(this);
    getActivity().findViewById(R.id.bubbleView3).setOnClickListener(this);
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bubbleView1:
      Toast.makeText(getActivity(), "Bubble 1 clicked", Toast.LENGTH_SHORT).show();
        break;
      case R.id.bubbleView2:
      Toast.makeText(getActivity(), "Bubble 2 clicked", Toast.LENGTH_SHORT).show();
        break;
      case R.id.bubbleView3:
      Toast.makeText(getActivity(), "Bubble 3 clicked", Toast.LENGTH_SHORT).show();
        break;
    }
  }
}
