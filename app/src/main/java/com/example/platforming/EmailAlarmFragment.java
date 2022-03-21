package com.example.platforming;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EmailAlarmFragment extends Fragment {
    boolean deleteEmail = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_alarm, container, false);
        Setting(view);
        return view;
    }

    //리스너&뷰 설정
    private void Setting(View view){
        Button confirm = (Button) view.findViewById(R.id.confirm_emailVerification);

        if(getArguments().getString("Type").equals("emailVerification")){
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckEmailVerification();
                }
            });
        }else if(getArguments().getString("Type").equals("findPassword")){
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteEmail = false;
                    getActivity().onBackPressed();
                }
            });
        }
    }

    //이메일 인증여부 확인
    private void CheckEmailVerification(){
        if(Variable.firebaseAuth.getCurrentUser().isEmailVerified()){
            deleteEmail = false;
            Intent mainIntent = new Intent(getContext(), MainActivity.class);
            getActivity().startActivity(mainIntent);
            getActivity().finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(deleteEmail){
            Variable.firebaseAuth.getCurrentUser().delete();
        }
    }
}