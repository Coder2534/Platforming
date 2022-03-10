package com.example.platforming;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SignInFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        SetListener(view);
        return view;
    }

    //리스너 설정
    private void SetListener(View view){
        Button confirm = (Button)view.findViewById(R.id.confirm_signIn);
        Button signOut = (Button)view.findViewById(R.id.signUp_signIn);

        TextView email = (TextView)view.findViewById(R.id.email_signIn);
        TextView password = (TextView)view.findViewById(R.id.password_signIn);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn(email.getText().toString(), password.getText().toString());
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout_signIn, new SignUpFragment()).addToBackStack(null).commit();
            }
        });
    }

    //로그인
    private void SignIn(String Email, String password){
        Variable.firebaseAuth.signInWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.w("LoginActivity", "signInWithEmailAndPassword Success");
                    Intent mainIntent = new Intent(getContext(), MainActivity.class);
                    getActivity().startActivity(mainIntent);
                    getActivity().finish();
                }else{
                    Log.w("LoginActivity", "signInWithEmailAndPassword Error");
                }
            }
        });
    }
}
