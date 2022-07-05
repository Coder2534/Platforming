package com.android.platforming.fragment;

import static com.android.platforming.object.FirestoreManager.getFirebaseAuth;

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

import com.android.platforming.object.CustomDialog;
import com.example.platforming.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        setListener(view);
        return view;
    }

    //리스너 설정
    private void setListener(View view){
        Button confirm = view.findViewById(R.id.btn_signup);
        TextView email = view.findViewById(R.id.et_signup_email);
        TextView password = view.findViewById(R.id.et_signup_password);
        TextView passwordCheck = view.findViewById(R.id.et_signup_passwordcheck);
        TextView accessCode = view.findViewById(R.id.et_signup_accesscode);

        confirm.setOnClickListener(v -> createAccount(email.getText().toString(), password.getText().toString(), passwordCheck.getText().toString(), accessCode.getText().toString()));
    }

    //계정 생성
    FirebaseUser tempUser;
    ArrayList<String> ACCESS_CODE = new ArrayList<String>(){{
        add("1");
    }};

    private void createAccount(String email, String password, String passwordCheck, String accessCode){
        CustomDialog customDialog = new CustomDialog();

        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$");
        Matcher matcher = pattern.matcher(email);

        if(!matcher.find()){
            Log.w("SignUpFragment", "email form Error");
            customDialog.errorDialog(getContext(), "이메일이 유효하지 않습니다.");
            return;
        }

        //대문자, 소문자, 특수문자, 숫자 중 2가지 포함(8~20자)
        pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[a-z!@#$%^&*=])(?=.*[0-9!@#$%^&*=])(?=.*[A-Z0-9])(?=.*[a-z0-9])(?=.*[A-Z!@#$%^&*=]).{8,20}$");
        matcher = pattern.matcher(password);
        if(!matcher.find()){
            Log.w("SignUpFragment", "password form Error");
            customDialog.errorDialog(getContext(), "비밀번호가 유효하지 않습니다.\n\n8자리 이상과 두 종류 이상의 문자구성\n※문자종류: 대문자, 소문자, 특수문자, 숫자");
            return;
        }

        if(!password.equals(passwordCheck)){
            Log.w("SignUpFragment", "passwordCheck Error");
            customDialog.errorDialog(getContext(), "비밀번호가 일치하지 않습니다.");
            return;
        }

        if(!ACCESS_CODE.contains(accessCode)){
            Log.w("SignUpFragment", "accessCode Error");
            customDialog.errorDialog(getContext(), "학교코드가 유효하지 않습니다.");
            return;
        }


        getFirebaseAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                if(tempUser != null && !tempUser.isEmailVerified()) tempUser.delete();
                tempUser = getFirebaseAuth().getCurrentUser();
                sendEmailVerification(getActivity().getSupportFragmentManager());
            }else{
                Log.w("SignUpFragment", "createUserWithEmailAndPassword Error");
                customDialog.errorDialog(getContext(), "이미 사용중인 이메일 입니다.");
            }
        });
    }

    //이메일 인증메일 전송
    void sendEmailVerification(FragmentManager fragmentManager){
        getFirebaseAuth().getCurrentUser().sendEmailVerification().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                //로그 출력
                Log.w("SignUpFragment", "sendEmailVerification success");

                //Fragment 변경
                Bundle bundle = new Bundle();
                bundle.putString("Type", "emailVerification");
                EmailAlarmFragment emailAlarmFragment = new EmailAlarmFragment();
                emailAlarmFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.cl_signin, emailAlarmFragment).addToBackStack(null).commit();
            }else{
                //로그 출력
                Log.w("SignUpFragment", "sendEmailVerification fail");
            }
        });
    }
}
