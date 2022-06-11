package com.android.platforming.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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
        View view = inflater.inflate(R.layout.fragment_user_initial_setting, container, false);
        SetListenr(view);

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

    private void SetListenr(View view){
        int grade;
        int room;
        int number;

        EditText schoolInfo = view.findViewById(R.id.schoolInfo_editText_UIS);
        schoolInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();

                TextView schoolInfo = view.findViewById(R.id.schoolInfo_textView_UIS);
                schoolInfo.setText(s.toString());
                /*if(s.length() >= 1)

                else if(s.length() >= 3)
                    Integer.parseInt(text.substring(1, 2));
                else if(s.length() >= 5)
                    Integer.parseInt(text.substring(3, 4));*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

            int sex;
            boolean isMale = ((RadioButton)view.findViewById(R.id.male_UIS)).isChecked();
            boolean isFemale = ((RadioButton)view.findViewById(R.id.female_UIS)).isChecked();
            if(!isMale && !isFemale){
                return;
            }
            else if(isMale){
                sex = 0;
            }
            else{
                sex = 1;
            }

            // User.setUser(new User(userName, nickName, telephone, sex, , , , sliderViewPager.));
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
