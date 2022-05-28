package com.example.platforming;

import androidx.appcompat.app.AppCompatActivity;
import static com.example.platforming.Variable.person;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(person.FIRST_LOGIN){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout_main, new UserInitialSettingFragment()).commit();
        }else{
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout_signIn, new SignInFragment()).commit();
        }
    }
}