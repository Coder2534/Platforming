package com.platforming.autonomy.activity;

import static com.platforming.autonomy.clazz.FirestoreManager.getFirebaseAuth;
import static com.platforming.autonomy.clazz.FirestoreManager.setFirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import com.platforming.autonomy.InitApplication;
import com.platforming.autonomy.clazz.ThemeManager;
import com.platforming.autonomy.interfaze.ListenerInterface;
import com.platforming.autonomy.clazz.FirestoreManager;
import com.android.autonomy.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.atomic.AtomicBoolean;

public class StartActivity extends AppCompatActivity {

    //startactivity에서 crash발생 2022-08-10

    private static int SPLASH_TIME_OUT = 1000;
    Handler handler_splash;
    Runnable runnalbe_splash;
    AtomicBoolean timeout = new AtomicBoolean(false);
    AtomicBoolean done = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("timeline", "StartActivity");
        ThemeManager.TFCall(this, (InitApplication)getApplication());

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start);

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
                Intent loginIntent = new Intent(StartActivity.this, SignActivity.class);
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