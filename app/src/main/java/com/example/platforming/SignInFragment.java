package com.example.platforming;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SignInFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        AutoSignIn();
        SetListener(view);

        return view;
    }

    //리스너 설정
    private void SetListener(View view){
        Button confirm = (Button)view.findViewById(R.id.confirm_signIn);
        Button signOut = (Button)view.findViewById(R.id.signUp_signIn);
        Button findPassword = (Button)view.findViewById(R.id.findPassword_singIn);
        TextView email = (TextView)view.findViewById(R.id.email_signIn);
        TextView password = (TextView)view.findViewById(R.id.password_signIn);
        Switch autoSignIn = (Switch)view.findViewById(R.id.autoSignIn_signIn);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn(email.getText().toString(), password.getText().toString(), autoSignIn.isChecked());
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout_signIn, new SignUpFragment()).addToBackStack(null).commit();
            }
        });
        findPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Type", "findPassword");
                EmailAlarmFragment emailAlarmFragment = new EmailAlarmFragment();
                emailAlarmFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout_signIn, emailAlarmFragment).addToBackStack(null).commit();
            }
        });
    }

    //자동 로그인
    private void AutoSignIn(){
        SharedPreferences auto = getActivity().getSharedPreferences("AutoSignIn", Activity.MODE_PRIVATE);
        if(auto.getString("autoSignIn", null) != null){
            SignIn(auto.getString("Email", null), auto.getString("Password",null), true);
        }
    }

    //로그인
    private void SignIn(String email, String password, boolean autoSignIn){
        Variable.firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //로그 출력
                    Log.w("LoginActivity", "signInWithEmailAndPassword Success");

                    //자동 로그인 설정
                    SharedPreferences auto = getActivity().getSharedPreferences("AutoSignIn", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor autoSignInEdit= auto.edit();
                    autoSignInEdit.clear();
                    if(autoSignIn){
                        autoSignInEdit.putString("Email", email);
                        autoSignInEdit.putString("Password", password);
                        autoSignInEdit.putBoolean("autoSignIn", true);
                    }
                    autoSignInEdit.commit();

                    //Activity 변경
                    Intent mainIntent = new Intent(getContext(), MainActivity.class);
                    getActivity().startActivity(mainIntent);
                    getActivity().finish();
                }else{
                    //로그 출력
                    Log.w("LoginActivity", "signInWithEmailAndPassword Error");
                }
            }
        });
    }
}