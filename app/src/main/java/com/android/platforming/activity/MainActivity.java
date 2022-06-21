package com.android.platforming.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.platforming.fragment.MainPageFragment;
import com.android.platforming.object.User;
import com.example.platforming.R;
import com.android.platforming.fragment.UserInitialSettingFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(User.getUser() == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.cl_main, new UserInitialSettingFragment()).commit();
        }
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.cl_main, new MainPageFragment());
        }
    }
}