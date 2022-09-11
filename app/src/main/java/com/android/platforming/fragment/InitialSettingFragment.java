package com.android.platforming.fragment;

import static com.android.platforming.clazz.FirestoreManager.getFirebaseAuth;

import static com.android.platforming.clazz.User.user;

import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitialSettingFragment extends Fragment {
    ImageSlider imageSlider;

    private final static ArrayList<Integer> profiles = new ArrayList<Integer>(){{
        add(R.drawable.profile_dog);
        add(R.drawable.profile_cat);
        add(R.drawable.profile_rabbit);
    }};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_initialsetting, container, false);
        setListenr(view);

        imageSlider = new ImageSlider(view.getContext(), view.findViewById(R.id.vp_initialsetting_profile), view.findViewById(R.id.layoutIndicators));
        imageSlider.setAdapter(new ImageSliderAdapter(profiles));
        imageSlider.setIndicators(User.getProfiles().size());

        return view;
    }

    private void setListenr(View view){
        Button confirm = view.findViewById(R.id.btn_initialsetting);

        confirm.setOnClickListener(v -> {

            Map<String, Object> data = new HashMap<>();
            String userName = ((EditText)view.findViewById(R.id.et_initialsetting_username)).getText().toString();
            if(userName == ""){
                return;
            }
            data.put("username", userName);

            String nickName = ((EditText)view.findViewById(R.id.et_initialsetting_nickname)).getText().toString();
            if(nickName == ""){
                return;
            }
            data.put("nickname", nickName);

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

            String studentId = ((EditText)view.findViewById(R.id.et_initialsetting_studentid)).getText().toString();
            if(studentId.length() != 5){
                return;
            }
            data.put("studentId", studentId);

            data.put("profileIndex", imageSlider.getPosition());

            data.put("point", 0);

            List<Long> list_design = Collections.singletonList(0L);
            data.put("themes", list_design);
            data.put("fonts", list_design);

            data.put("myPostIds", new ArrayList<String>());

            data.put("lastSignIn", 0);
            data.put("point_receipt", 0);
            data.put("dailyTasks", Arrays.asList(0L, 0L, 0L, 0L));

            FirestoreManager firestoreManager = new FirestoreManager();
            firestoreManager.writeUserData(data, new ListenerInterface() {
                @Override
                public void onSuccess() {
                    user = new User(getFirebaseAuth().getCurrentUser().getUid(), getFirebaseAuth().getCurrentUser().getEmail(), data);
                    ((MainActivity)getActivity()).setting();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cl_main, new MainPageFragment()).commit();
                }
            });

        });
    }
}
