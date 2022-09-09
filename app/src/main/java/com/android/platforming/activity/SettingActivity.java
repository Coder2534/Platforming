package com.android.platforming.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.platforming.InitApplication;
import com.android.platforming.fragment.SettingFragment;
import com.example.platforming.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InitApplication initApplication = ((InitApplication)getApplication());
        initApplication.refreshAppliedTheme();
        initApplication.refreshAppliedFont();
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
            case 4:setTheme(R.style.kyoboFont);break;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.tb_setting);
        toolbar.setTitle("설정");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24); //왼쪽 상단 버튼 아이콘 지정

        getSupportFragmentManager().beginTransaction().replace(R.id.fcv_setting, new SettingFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}