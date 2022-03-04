package com.example.platforming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Variable.firebaseAuth = FirebaseAuth.getInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout_signIn, new SignInFragment()).addToBackStack(null).commit();
    }
}

/*
    로그인 방식
    개인 이메일과 페스워드
    첫 로그인시 학교에서 제공한 코드 입력
    코드 중복 사용 불가
    01(학교)1(1: 학생 2: 교사)10101{학년(1~3: 재학생 4: 졸업생) 반 번호}
     */