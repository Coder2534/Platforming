package com.android.platforming.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.platforming.adapter.ImageSliderAdapter;
import com.android.platforming.object.User;
import com.example.platforming.R;

public class UserInitialSettingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userinitialsetting, container, false);
        setListenr(view);

        sliderViewPager = view.findViewById(R.id.sliderViewPager);
        layoutIndicator = view.findViewById(R.id.layoutIndicators);

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSliderAdapter(User.getProfiles()));

        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        setupIndicators(User.getProfiles().size());

        return view;
    }

    private void setListenr(View view){
        Button confirm = view.findViewById(R.id.confirm_UIS);

        confirm.setOnClickListener(v -> {

            String userName = ((EditText)view.findViewById(R.id.userName_UIS)).getText().toString();
            if(userName != ""){
                return;
            }

            String nickName = ((EditText)view.findViewById(R.id.nickName_UIS)).getText().toString();
            if(nickName != ""){
                return;
            }

            String telephone = ((EditText)view.findViewById(R.id.telephone_UIS)).getText().toString();
            if(telephone != ""){
                return;
            }

            boolean isMale = ((RadioButton)view.findViewById(R.id.male_UIS)).isChecked();
            boolean isFemale = ((RadioButton)view.findViewById(R.id.female_UIS)).isChecked();
            if(!isMale && !isFemale){
                return;
            }




        });
    }

    //ViewPager
    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;

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
