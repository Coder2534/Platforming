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

import com.android.platforming.activity.MainActivity;
import com.android.platforming.adapter.ImageSliderAdapter;
import com.android.platforming.interfaze.ListenerInterface;
import com.android.platforming.object.FirestoreManager;
import com.android.platforming.object.User;
import com.android.platforming.view.ImageSlider;
import com.example.platforming.R;

import java.security.PrivateKey;

public class UserInitialSettingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_initialsetting, container, false);
        setListenr(view);

        ImageSlider imageSlider = new ImageSlider(view.getContext(), view.findViewById(R.id.sliderViewPager), view.findViewById(R.id.layoutIndicators));
        imageSlider.setAdapter(new ImageSliderAdapter(User.getProfiles()));
        imageSlider.setIndicators(User.getProfiles().size());

        return view;
    }

    private void setListenr(View view){
        Button confirm = view.findViewById(R.id.btn_initialsetting);

        confirm.setOnClickListener(v -> {

            String userName = ((EditText)view.findViewById(R.id.et_initialsetting_username)).getText().toString();
            if(userName != ""){
                return;
            }

            String nickName = ((EditText)view.findViewById(R.id.et_initialsetting_nickname)).getText().toString();
            if(nickName != ""){
                return;
            }

            String telephone = ((EditText)view.findViewById(R.id.et_initialsetting_telephone)).getText().toString();
            if(telephone != ""){
                return;
            }

            boolean isMale = ((RadioButton)view.findViewById(R.id.rbtn_initialsetting_male)).isChecked();
            boolean isFemale = ((RadioButton)view.findViewById(R.id.rbtn_initialsetting_female)).isChecked();
            if(!isMale && !isFemale){
                return;
            }

            FirestoreManager firestoreManager = new FirestoreManager();
            firestoreManager.writeUserData(new ListenerInterface() {
                @Override
                public void onSuccess() {
                    ((MainActivity)getActivity()).setListner();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cl_main, new MainPageFragment()).commit();
                }
            });

        });
    }
}
