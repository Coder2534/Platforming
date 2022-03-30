package com.example.platforming;

import static com.example.platforming.Variable.firebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout_signIn, new SignInFragment()).commit();
    }
}