package com.example.platforming;

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
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignOutFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    EmailVerificationFragment emailVerificationFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_out, container, false);
        firebaseAuth = Variable.firebaseAuth;
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        emailVerificationFragment = new EmailVerificationFragment();
        SetListener(view);
        return view;
    }

    //리스너 설정
    private void SetListener(View view){
        Button confirm = (Button)view.findViewById(R.id.confirm_signOut);
        TextView email = (TextView)view.findViewById(R.id.email_signOut);
        TextView password = (TextView)view.findViewById(R.id.password_signOut);
        TextView accessCode = (TextView)view.findViewById(R.id.accessCode_signOut);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount(email.getText().toString(), password.getText().toString(), accessCode.getText().toString());
            }
        });
    }

    //계정 생성
    private void CreateAccount(String email, String password, String accessCode){
        if(Variable.accessCode_All.contains(accessCode)){
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        SendEmailVerification();
                    }else{
                        Log.w("LoginActivity", "createUserWithEmailAndPassword Error");
                    }
                }
            });
        }
        else{
            Log.w("LoginActivity", "accessCode Error");
        }
    }

    //이메일 인증메일 전송
    private void SendEmailVerification(){
        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.w("LoginActivity", "sendEmailVerification Success");
                    fragmentTransaction.replace(R.id.fragmentLayout_signIn, emailVerificationFragment);
                    fragmentTransaction.commit();
                }else{
                    Log.w("LoginActivity", "sendEmailVerification Error");
                }
            }
        });
    }
}
