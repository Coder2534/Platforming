package com.android.platforming.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager2.widget.ViewPager2;

import com.android.platforming.adapter.ImageSliderAdapter;
import com.android.platforming.object.User;
import com.example.platforming.R;

public class ImageSlider {

    Context context;

    public ImageSlider(Context context, ViewPager2 sliderViewPager, LinearLayout layoutIndicator){
        this.context = context;
        this.sliderViewPager = sliderViewPager;
        this.layoutIndicator = layoutIndicator;

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });
    }


    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;

    public void setAdapter(ImageSliderAdapter sliderAdapter){
        sliderViewPager.setAdapter(sliderAdapter);
    }

    public void setIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(context);
            indicators[i].setImageDrawable(context.getResources().getDrawable(R.drawable.bg_indicator_inactive, null));
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
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_indicator_inactive, null));
            } else {
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_indicator_inactive, null));
            }
        }
    }
}
