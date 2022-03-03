package com.example.platforming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    Handler handler_splash;
    Runnable runnalbe_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runnalbe_splash = new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }};

        handler_splash = new Handler();
        handler_splash.postDelayed(runnalbe_splash, SPLASH_TIME_OUT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler_splash.removeCallbacks(runnalbe_splash);
    }

    //Coder2534 Github Platfroming 30days token
}