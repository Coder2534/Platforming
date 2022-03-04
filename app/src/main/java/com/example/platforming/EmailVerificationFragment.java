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

public class EmailVerificationFragment extends Fragment {
    boolean checkEmail = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_verification, container, false);

        return view;
    }

    //리스너 설정
    private void SetListener(View view){
        Button confirm = (Button) view.findViewById(R.id.confirm_emailVerification);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEmailVerification();
            }
        });
    }

    //이메일 인증여부 확인
    private void CheckEmailVerification(){
        if(Variable.firebaseAuth.getCurrentUser().isEmailVerified()){
            checkEmail = true;
            Intent mainIntent = new Intent(getContext(), MainActivity.class);
            getActivity().startActivity(mainIntent);
            getActivity().finish();
        }else{

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!checkEmail){
            Variable.firebaseAuth.getCurrentUser().delete();
        }
    }
}
