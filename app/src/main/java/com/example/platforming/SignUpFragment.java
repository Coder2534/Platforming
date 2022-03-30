package com.example.platforming;

import static com.example.platforming.Variable.firebaseAuth;

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
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        SetListener(view);
        return view;
    }

    //리스너 설정
    private void SetListener(View view){
        Button confirm = (Button)view.findViewById(R.id.confirm_signUp);
        TextView email = (TextView)view.findViewById(R.id.email_signUp);
        TextView password = (TextView)view.findViewById(R.id.password_signUp);
        TextView passwordCheck = (TextView)view.findViewById(R.id.passwordCheck_signUp);
        TextView accessCode = (TextView)view.findViewById(R.id.accessCode_signUp);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount(email.getText().toString(), password.getText().toString(), passwordCheck.getText().toString(), accessCode.getText().toString());
            }
        });
    }

    //계정 생성
    FirebaseUser tempUser;
    private void CreateAccount(String email, String password, String passwordCheck, String accessCode){
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$");
        Matcher matcher = pattern.matcher(email);
        if(!matcher.find()){
            Log.w("SignUpFragment", "email form Error");
            CustomDialog.ErrorDialog(getContext(), "이메일이 유효하지 않습니다.");
            return;
        }

        //대문자, 소문자, 특수문자, 숫자 중 2가지 포함(8~20자)
        pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[a-z!@#$%^&*])(?=.*[0-9!@#$%^&*])(?=.*[A-Z0-9])(?=.*[a-z0-9])(?=.*[A-Z!@#$%^&*]).{8,20}$");
        matcher = pattern.matcher(password);
        if(!matcher.find()){
            Log.w("SignUpFragment", "password form Error");
            CustomDialog.ErrorDialog(getContext(), "비밀번호가 유효하지 않습니다.\n\n8자리 이상과 두 종류 이상의 문자구성\n※문자종류: 대문자, 소문자, 특수문자, 숫자");
            return;
        }

        if(!password.equals(passwordCheck)){
            Log.w("SignUpFragment", "passwordCheck Error");
            CustomDialog.ErrorDialog(getContext(), "비밀번호가 일치하지 않습니다.");
            return;
        }

        if(!Variable.accessCode.contains(accessCode)){
            Log.w("SignUpFragment", "accessCode Error");
            CustomDialog.ErrorDialog(getContext(), "학교코드가 유효하지 않습니다.");
            return;
        }


        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(tempUser != null && !tempUser.isEmailVerified()) tempUser.delete();
                    tempUser = firebaseAuth.getCurrentUser();
                    SendEmailVerification(getActivity().getSupportFragmentManager());
                }else{
                    Log.w("SignUpFragment", "createUserWithEmailAndPassword Error");
                    CustomDialog.ErrorDialog(getContext(), "이미 사용중인 이메일 입니다.");
                }
            }
        });
    }

    //이메일 인증메일 전송
    void SendEmailVerification(FragmentManager fragmentManager){
        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //로그 출력
                    Log.w("SignUpFragment", "sendEmailVerification success");

                    //Fragment 변경
                    Bundle bundle = new Bundle();
                    bundle.putString("Type", "emailVerification");
                    EmailAlarmFragment emailAlarmFragment = new EmailAlarmFragment();
                    emailAlarmFragment.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.fragmentLayout_signIn, emailAlarmFragment).addToBackStack(null).commit();
                }else{
                    //로그 출력
                    Log.w("SignUpFragment", "sendEmailVerification fail");
                }
            }
        });
    }
}
