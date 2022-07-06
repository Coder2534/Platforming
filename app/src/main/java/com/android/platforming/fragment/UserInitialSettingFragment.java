package com.android.platforming.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.platforming.activity.MainActivity;
import com.android.platforming.adapter.ImageSliderAdapter;
import com.android.platforming.interfaze.ListenerInterface;
import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.User;
import com.android.platforming.view.ImageSlider;
import com.example.platforming.R;

import java.util.HashMap;

public class UserInitialSettingFragment extends Fragment {
    ImageSlider imageSlider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_initialsetting, container, false);
        setListenr(view);

        imageSlider = new ImageSlider(view.getContext(), view.findViewById(R.id.sliderViewPager), view.findViewById(R.id.layoutIndicators));
        imageSlider.setAdapter(new ImageSliderAdapter(User.getProfiles()));
        imageSlider.setIndicators(User.getProfiles().size());

        return view;
    }

    private void setListenr(View view){
        Button confirm = view.findViewById(R.id.btn_initialsetting);

        confirm.setOnClickListener(v -> {

            HashMap<String, Object> data = new HashMap<>();
            String userName = ((EditText)view.findViewById(R.id.et_initialsetting_username)).getText().toString();
            if(userName == ""){
                return;
            }
            data.put("userName", userName);

            String nickName = ((EditText)view.findViewById(R.id.et_initialsetting_nickname)).getText().toString();
            if(nickName == ""){
                return;
            }
            data.put("nickName", nickName);

            String telephone = ((EditText)view.findViewById(R.id.et_initialsetting_telephone)).getText().toString();
            if(telephone == ""){
                return;
            }
            data.put("telephone", telephone);

            boolean isMale = ((RadioButton)view.findViewById(R.id.rbtn_initialsetting_male)).isChecked();
            boolean isFemale = ((RadioButton)view.findViewById(R.id.rbtn_initialsetting_female)).isChecked();
            if(!isMale && !isFemale){
                return;
            }
            if(isMale)
                data.put("sex", 0);
            else
                data.put("sex", 1);

            String student = ((EditText)view.findViewById(R.id.et_initialsetting_student)).getText().toString();
            if(student.length() != 5){
                return;
            }
            data.put("grade", Integer.parseInt(String.valueOf(student.charAt(0))));
            data.put("room", Integer.parseInt(student.substring(1, 3)));
            data.put("number", Integer.parseInt(student.substring(3, 5)));

            data.put("profileIndex", imageSlider.getPosition());

            data.put("point", 0);

            FirestoreManager firestoreManager = new FirestoreManager();
            firestoreManager.writeUserData(data, new ListenerInterface() {
                @Override
                public void onSuccess() {
                    ((MainActivity)getActivity()).setListner();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cl_main, new MainPageFragment()).commit();
                }
            });

        });
    }
}
