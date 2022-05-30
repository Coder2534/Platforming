package com.android.platforming.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.platforming.adapter.ImageSliderAdapter;
import com.android.platforming.activity.MainActivity;
import com.example.platforming.R;

import java.util.ArrayList;

public class UserInitialSettingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_initial_setting, container, false);
        SetListenr(view);

        sliderViewPager = view.findViewById(R.id.sliderViewPager);
        layoutIndicator = view.findViewById(R.id.layoutIndicators);

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSliderAdapter(getContext(), drawables));

        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        setupIndicators(drawables.size());

        return view;
    }

    private void SetListenr(View view){
        Button confirm = view.findViewById(R.id.confirm_UIS);

        confirm.setOnClickListener(v -> {

        });
    }

    //ViewPager
    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;

    private ArrayList<Integer> drawables = new ArrayList<Integer>(){{
        add(R.drawable.ic_launcher_foreground);
    }};

    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getContext());
            indicators[i].setImageDrawable(getResources().getDrawable(R.drawable.bg_indicator_inactive, null));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.bg_indicator_inactive, null));
            } else {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.bg_indicator_inactive, null));
            }
        }
    }

    //프로필 넘기기 == 스와이프 == 드래그
}
