package com.android.platforming.fragment;

import static com.android.platforming.clazz.FirestoreManager.getFirebaseAuth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.platforming.activity.MainActivity;
import com.example.platforming.R;

public class EmailAlarmFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emailalarm, container, false);
        setListner(view);
        return view;
    }

    //리스너&뷰 설정
    private void setListner(View view){
        Button confirm = (Button) view.findViewById(R.id.btn_emailalarm);
        TextView title = (TextView) view.findViewById(R.id.tv_emailalarm_title);
        TextView message = (TextView) view.findViewById(R.id.tv_emailalarm_description);

        if(getArguments().getString("Type").equals("emailVerification")){
            title.setText("이메일 인증");
            message.setText("회원가입 시 입력하신 이메일("+ getFirebaseAuth().getCurrentUser().getEmail()+")로 전송된 인증 메일을 확인해 주세요.");

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkEmailVerification(getActivity());
                    getActivity().onBackPressed();
                }
            });
        }else if(getArguments().getString("Type").equals("findPassword")){
            title.setText("비밀번호 재설정");
            message.setText("입력하신 이메일("+ getArguments().getString("Email") +")로 전송된 비밀번호 재설정 메일을 확인해 주세요.");

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
        }
    }

    //이메일 인증여부 확인
    void checkEmailVerification(Activity activity){
        getFirebaseAuth().getCurrentUser().reload();
        Log.w("EmailAlarmFragment", "Verification" + getFirebaseAuth().getCurrentUser().isEmailVerified());
        if(getFirebaseAuth().getCurrentUser().isEmailVerified()){
            Log.w("EmailAlarmFragment", "Verification success");
            Intent mainIntent = new Intent(activity, MainActivity.class);
            activity.startActivity(mainIntent);
            activity.finish();
        }
    }
}