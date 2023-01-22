package com.platforming.autonomy.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.platforming.autonomy.InitApplication;
import com.platforming.autonomy.fragment.SettingFragment;
import com.android.autonomy.R;

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
            case 0:setTheme(R.style.pretendardFont);break;
            case 1:setTheme(R.style.snowFont);break;
            case 2:setTheme(R.style.bmeFont);break;
            case 3:setTheme(R.style.establishreFont);break;
            case 4:setTheme(R.style.eulyoo1945Font);break;
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