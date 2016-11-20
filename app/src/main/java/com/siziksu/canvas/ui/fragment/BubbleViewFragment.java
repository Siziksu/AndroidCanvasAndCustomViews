package com.siziksu.canvas.ui.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siziksu.canvas.R;
import com.siziksu.canvas.common.ActivityCommon;
import com.siziksu.canvas.ui.view.BubbleView;

public class BubbleViewFragment extends Fragment implements View.OnClickListener {

    private View view;

    public BubbleViewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bubble_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        BubbleView bubble1 = (BubbleView) getActivity().findViewById(R.id.bubbleView1);
        BubbleView bubble2 = (BubbleView) getActivity().findViewById(R.id.bubbleView2);
        BubbleView bubble3 = (BubbleView) getActivity().findViewById(R.id.bubbleView3);

        bubble1.setBubbleBackground(ContextCompat.getDrawable(getActivity(), R.drawable.img_bubble_blue));
        bubble1.setCategoryDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_home));
        bubble1.setText(getString(R.string.bubble_temp_text1));
        bubble1.setTextColor(Color.WHITE);
        bubble1.setAnimated(true);
        bubble1.setAnimation(R.anim.bubble_animation);
        bubble1.setTextSize(ActivityCommon.getInstance(getActivity()).valueToSp(16));
        bubble1.setTextStyle(Typeface.NORMAL);
        bubble1.setTextPadding(ActivityCommon.getInstance(getActivity()).valueToDp(20));
        bubble1.setMiniBubbleValue(7);
        bubble1.setMiniBubbleColor(0xd7273ec1);
        bubble1.setMiniBubbleTextColor(getResources().getColor(R.color.white));
        bubble1.setMiniBubblePosition(BubbleView.TOP_RIGHT);

        bubble2.setAnimated(true);
        bubble2.setAnimation(R.anim.bubble_animation);

        bubble3.setAnimated(true);
        bubble3.setAnimation(R.anim.bubble_animation);

        bubble1.setOnClickListener(this);
        bubble2.setOnClickListener(this);
        bubble3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bubbleView1:
                Snackbar.make(view, "Bubble 1 clicked", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.bubbleView2:
                Snackbar.make(view, "Bubble 2 clicked", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.bubbleView3:
                Snackbar.make(view, "Bubble 3 clicked", Snackbar.LENGTH_SHORT).show();
                break;
        }
    }
}
