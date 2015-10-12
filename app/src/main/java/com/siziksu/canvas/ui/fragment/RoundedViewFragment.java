package com.siziksu.canvas.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.siziksu.canvas.R;
import com.siziksu.canvas.ui.view.RoundedView;
import com.siziksu.canvas.ui.manager.ContentManager;

public class RoundedViewFragment extends Fragment {

  private RoundedView roundedView;

  public RoundedViewFragment() {
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
    return inflater.inflate(R.layout.fragment_rounded_view, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ContentManager.getInstance().setTag(this, FragmentTag.ROUNDED_VIEW.getTag());
    getActivity().setTitle(FragmentTag.ROUNDED_VIEW.getTitle());

    ((SeekBar) getActivity().findViewById(R.id.roundedSeekBar)).setOnSeekBarChangeListener(seekBarChangeListener);
    roundedView = (RoundedView) getActivity().findViewById(R.id.drawingRoundedView);
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  private final SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
      roundedView.setIncrement(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
  };
}
