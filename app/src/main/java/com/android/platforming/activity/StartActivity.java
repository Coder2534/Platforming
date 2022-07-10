package com.android.platforming.activity;

import static com.android.platforming.clazz.FirestoreManager.getFirebaseAuth;
import static com.android.platforming.clazz.FirestoreManager.setFirebaseAuth;
import static com.android.platforming.clazz.FirestoreManager.setFirestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.platforming.interfaze.ListenerInterface;
import com.android.platforming.clazz.FirestoreManager;
import com.example.platforming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.atomic.AtomicBoolean;

public class StartActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;
    Handler handler_splash;
    Runnable runnalbe_splash;
    AtomicBoolean timeout = new AtomicBoolean(false);
    AtomicBoolean done = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        setFirestore(FirebaseFirestore.getInstance());
        setFirebaseAuth(FirebaseAuth.getInstance());

        if(getFirebaseAuth().getCurrentUser() != null && getFirebaseAuth().getCurrentUser().isEmailVerified()){
            FirestoreManager firestoreManager = new FirestoreManager();
            firestoreManager.readUserData(new ListenerInterface() {
                AtomicBoolean timeout = StartActivity.this.timeout;
                AtomicBoolean done = StartActivity.this.done;

                @Override
                public void onSuccess() {
                    done.set(true);
                    if(timeout.get()){
                        Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }
            });

            runnalbe_splash = () -> {
                timeout.set(true);
                if(done.get()){
                    Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            };
        }
        else{
            if(getFirebaseAuth().getCurrentUser() != null)
                getFirebaseAuth().signOut();
            runnalbe_splash = () -> {
                Intent loginIntent = new Intent(StartActivity.this, SignInActivity.class);
                startActivity(loginIntent);
                finish();
            };
        }

        handler_splash = new Handler();
        handler_splash.postDelayed(runnalbe_splash, SPLASH_TIME_OUT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler_splash.removeCallbacks(runnalbe_splash);
    }
}