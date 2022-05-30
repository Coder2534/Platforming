package com.android.platforming.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.platforming.R;
import com.android.platforming.fragment.UserInitialSettingFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout_main, new UserInitialSettingFragment()).commit();
    }
}