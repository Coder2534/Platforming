package com.android.platforming.activity;

import static com.android.platforming.clazz.FirestoreManager.getFirebaseAuth;
import static com.android.platforming.clazz.FirestoreManager.setFirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import com.android.platforming.InitApplication;
import com.android.platforming.clazz.SchoolApi;
import com.android.platforming.fragment.SchoolmealFragment;
import com.android.platforming.interfaze.ListenerInterface;
import com.android.platforming.clazz.FirestoreManager;
import com.example.platforming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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
        InitApplication initApplication = ((InitApplication)getApplication());
        switch (initApplication.getAppliedTheme()){
            case 0:setTheme(R.style.WhiteTheme);break;
            case 1:setTheme(R.style.PinkTheme);break;
            case 2:setTheme(R.style.BuleTheme);break;
            case 3:setTheme(R.style.GreenTheme);break;
            case 4:setTheme(R.style.BlackTheme);break;
        }
        switch (initApplication.getAppliedFont()){
            case 0:setTheme(R.style.LeferipointwhiteobliqueFont);break;
            case 1:setTheme(R.style.pyeongFont);break;
            case 2:setTheme(R.style.vitorFont);break;
            case 3:setTheme(R.style.Galmuri9Font);break;
            case 4:setTheme(R.style.tokkiFont);break;
        }
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