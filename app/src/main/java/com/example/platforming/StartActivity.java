package com.example.platforming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    Handler handler_splash;
    Runnable runnalbe_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        runnalbe_splash = new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(StartActivity.this, SignInActivity.class);
                startActivity(loginIntent);
                finish();

                /*
                MainActivity 확인
                Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
                 */
            }};

        handler_splash = new Handler();
        handler_splash.postDelayed(runnalbe_splash, SPLASH_TIME_OUT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler_splash.removeCallbacks(runnalbe_splash);
    }
}