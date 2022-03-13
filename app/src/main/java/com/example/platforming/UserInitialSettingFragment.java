package com.example.platforming;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserInitialSettingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        SetListenr(view);
        return view;
    }

    private void SetListenr(View view){
        Button selectProfile = (Button)view.findViewById(R.id.selectProfile_SIS);
        Button confirm = (Button)view.findViewById(R.id.confirm_SIS);

        selectProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(getContext(), MainActivity.class);
                startActivity(mainIntent);
                getActivity().finish();
            }
        });
    }

    //프로필 넘기기 == 스와이프 == 드래그
}
