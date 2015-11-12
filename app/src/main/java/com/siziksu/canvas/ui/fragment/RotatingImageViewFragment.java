package com.siziksu.canvas.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.siziksu.canvas.R;
import com.siziksu.canvas.ui.view.RotatingImageView;
import com.siziksu.canvas.ui.manager.ContentManager;

public class RotatingImageViewFragment extends Fragment {

  private RotatingImageView rotatingImageView;

  public RotatingImageViewFragment() {
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
    return inflater.inflate(R.layout.fragment_rotating_image_view, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ContentManager.getInstance().setTagAndTitle(this, FragmentTag.ROTATING_IMAGE_VIEW);

    ((SeekBar) getActivity().findViewById(R.id.drawingSeekBarX)).setOnSeekBarChangeListener(seekBarChangeListener);
    ((SeekBar) getActivity().findViewById(R.id.drawingSeekBarY)).setOnSeekBarChangeListener(seekBarChangeListener);
    ((SeekBar) getActivity().findViewById(R.id.drawingSeekBarZ)).setOnSeekBarChangeListener(seekBarChangeListener);
    rotatingImageView = (RotatingImageView) getActivity().findViewById(R.id.rotatingImageView);
    rotatingImageView.setTurning(1, 0, 0);
  }

  private final SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

    int x;
    int y;
    int z;

    // x0 i5 apply 5 (5-0) | x5 i9 apply 4 (9-5) | x9 i4 apply -5 (4-9) | x4 i0 apply -4 (0-4)

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
      switch (seekBar.getId()) {
        case R.id.drawingSeekBarX:
          // Logger.logE("x" + x + " i" + i + " apply " + (i - x) + "(" + i + "-" + x + ")");
          rotatingImageView.setTurning(i - x, 0, 0);
          x = i;
          break;
        case R.id.drawingSeekBarY:
          rotatingImageView.setTurning(0, i - y, 0);
          y = i;
          break;
        case R.id.drawingSeekBarZ:
          rotatingImageView.setTurning(0, 0, i - z);
          z = i;
          break;
      }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
  };
}
