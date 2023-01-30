package com.platforming.autonomy.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.platforming.autonomy.InitApplication;
import com.platforming.autonomy.fragment.BulletinBoardListFragment;
import com.android.autonomy.R;

public class BulletinBoardActivity extends AppCompatActivity {

    private static AppCompatActivity activity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InitApplication initApplication = ((InitApplication)getApplication());
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
        setContentView(R.layout.activity_noticeboard);

        activity = this;

        Toolbar toolbar = findViewById(R.id.tb_noticeboard);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24); //왼쪽 상단 버튼 아이콘 지정

        String bulletinId = getIntent().getStringExtra("bulletinId");
        String postId = getIntent().getStringExtra("postId");

        BulletinBoardListFragment fragment = new BulletinBoardListFragment();
        Bundle args = new Bundle();
        args.putString("bulletinId", bulletinId);
        if(postId != null)
            args.putString("postId", postId);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.cl_noticeboard, fragment, "NoticeBoardList").commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.none, R.anim.finish_activity_noticeboard);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public static AppCompatActivity getActivity() {
        return activity;
    }
}