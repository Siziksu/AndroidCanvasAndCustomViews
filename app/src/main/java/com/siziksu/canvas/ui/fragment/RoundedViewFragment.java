package com.siziksu.canvas.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.siziksu.canvas.R;
import com.siziksu.canvas.ui.view.RoundedView;

public class RoundedViewFragment extends Fragment {

    private RoundedView roundedView;

    public RoundedViewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rounded_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SeekBar) getActivity().findViewById(R.id.roundedSeekBar)).setOnSeekBarChangeListener(seekBarChangeListener);
        roundedView = (RoundedView) getActivity().findViewById(R.id.drawingRoundedView);
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
