package com.example.platforming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
    }

    //계정 생성
    private void CreateAccount(String email, String password, String accessCode){
        if(Variable.accessCode_All.contains(accessCode)){
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        SendEmailVerification();
                    }else{

                    }
                }
            });
        }
        else{

        }
    }

    //이메일 인증메일 전송
    private void SendEmailVerification(){
        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                }else{

                }
            }
        });
    }

    //이메일 인증여부 확인
    private void CheckEmailVerification(){
        if(auth.getCurrentUser().isEmailVerified()){

        }else{

        }
    }

    //로그인
    private void Login(String Email, String password){
        auth.signInWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                }else{

                }
            }
        });
    }
}

/*
    로그인 방식
    개인 이메일과 페스워드
    첫 로그인시 학교에서 제공한 코드 입력
    코드 중복 사용 불가
     */