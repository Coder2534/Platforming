package com.example.platforming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Variable.firebaseAuth = FirebaseAuth.getInstance();
    }
}

/*
    로그인 방식
    개인 이메일과 페스워드
    첫 로그인시 학교에서 제공한 코드 입력
    코드 중복 사용 불가
     */