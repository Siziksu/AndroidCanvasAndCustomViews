package com.siziksu.canvas.ui.fragment;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.siziksu.canvas.R;
import com.siziksu.canvas.ui.commons.ActivityCommon;
import com.siziksu.canvas.ui.manager.ContentManager;
import com.siziksu.canvas.ui.view.BubbleView;

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

    BubbleView bubble1 = (BubbleView) getActivity().findViewById(R.id.bubbleView1);
    BubbleView bubble2 = (BubbleView) getActivity().findViewById(R.id.bubbleView2);
    BubbleView bubble3 = (BubbleView) getActivity().findViewById(R.id.bubbleView3);

    bubble1.setBubbleBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bubble_circle));
    bubble1.setCategoryDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.face_blue));
    bubble1.setText(getString(R.string.bubble_temp_text1));
    bubble1.setTextColor(getResources().getColor(R.color.white));
    bubble1.setTextSize(ActivityCommon.getInstance(getActivity()).valueToSp(16));
    bubble1.setTextStyle(Typeface.NORMAL);
    bubble1.setTextPadding(ActivityCommon.getInstance(getActivity()).valueToDp(20));
    bubble1.setMiniBubbleValue(7);
    bubble1.setMiniBubbleColor(0xd7275ac1);
    bubble1.setMiniBubbleTextColor(getResources().getColor(R.color.white));
    bubble1.setMiniBubblePosition(BubbleView.TOP_RIGHT);
    
    bubble1.setOnClickListener(this);
    bubble2.setOnClickListener(this);
    bubble3.setOnClickListener(this);
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
