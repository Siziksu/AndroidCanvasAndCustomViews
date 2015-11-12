package com.siziksu.canvas.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.siziksu.canvas.R;
import com.siziksu.canvas.ui.view.CounterView;
import com.siziksu.canvas.ui.manager.ContentManager;

public class CounterView02Fragment extends Fragment {

  private CounterView counterView;

  public CounterView02Fragment() {
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
    return inflater.inflate(R.layout.fragment_counter_view_02, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ContentManager.getInstance().setTagAndTitle(this, FragmentTag.COUNTER_VIEW_02);

    ((SeekBar) getActivity().findViewById(R.id.counterSeekBar)).setOnSeekBarChangeListener(seekBarChangeListener);
    counterView = (CounterView) getActivity().findViewById(R.id.drawingCounterView);
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  private final SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
      counterView.setIncrement(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
  };
}
