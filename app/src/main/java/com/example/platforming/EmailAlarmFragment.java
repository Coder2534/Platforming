package com.example.platforming;

import static com.example.platforming.Variable.firebaseAuth;

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

public class EmailAlarmFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_alarm, container, false);
        Setting(view);
        return view;
    }

    //리스너&뷰 설정
    private void Setting(View view){
        Button confirm = (Button) view.findViewById(R.id.confirm_emailAlarm);
        TextView title = (TextView) view.findViewById(R.id.title_emailAlarm);
        TextView message = (TextView) view.findViewById(R.id.message_emailAlarm);

        if(getArguments().getString("Type").equals("emailVerification")){
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckEmailVerification(getActivity());
                    getActivity().onBackPressed();
                }
            });
            title.setText("이메일 인증");
            message.setText("회원가입 시 입력하신 이메일("+ firebaseAuth.getCurrentUser().getEmail()+")로 전송된 인증 메일을 확인해 주세요.");
        }else if(getArguments().getString("Type").equals("findPassword")){
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
            title.setText("비밀번호 재설정");
            message.setText("입력하신 이메일("+ firebaseAuth.getCurrentUser().getEmail()+")로 전송된 비밀번호 재설정 메일을 확인해 주세요.");
        }
    }

    //이메일 인증여부 확인
    void CheckEmailVerification(Activity activity){
        firebaseAuth.getCurrentUser().reload();
        Log.w("EmailAlarmFragment", "Verification" + firebaseAuth.getCurrentUser().isEmailVerified());
        if(firebaseAuth.getCurrentUser().isEmailVerified()){
            Log.w("EmailAlarmFragment", "Verification success");
            Intent mainIntent = new Intent(activity, MainActivity.class);
            activity.startActivity(mainIntent);
            activity.finish();
        }
    }
}