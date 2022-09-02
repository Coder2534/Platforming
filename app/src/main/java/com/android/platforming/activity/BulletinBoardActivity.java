package com.android.platforming.activity;

import static com.android.platforming.clazz.Post.FREE_BULLETIN_BOARD;
import static com.android.platforming.clazz.Post.QUESTION_BULLETIN_BOARD;
import static com.android.platforming.clazz.Post.SCHOOL_BULLETIN_BOARD;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.platforming.InitApplication;
import com.android.platforming.fragment.BulletinBoardListFragment;
import com.example.platforming.R;

public class BulletinBoardActivity extends AppCompatActivity {

    //1. 게시판, 댓글 두번 들어가야 뜸 - 수정
    //3. comment 추가 이후 보여지지 않음 - 수정
    //4. docutment 자동완성시 순서개판 - 수정
    //5. 전제적인 크기조절

    private static AppCompatActivity activity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InitApplication initApplication = ((InitApplication)getApplication());
        switch (initApplication.getAppliedTheme()){
            case 0:setTheme(R.style.Theme_Platforming);break;
            case 1:setTheme(R.style.PinkTheme);break;
            case 2:setTheme(R.style.BuleTheme);break;
            case 3:setTheme(R.style.GreenTheme);break;
            case 4:setTheme(R.style.BlackTheme);break;
        }
        switch (initApplication.getAppliedFont()){
            case 0:setTheme(R.style.leferipointwhiteobliqueFont);break;
            case 1:setTheme(R.style.SlowFont);break;
            case 2:setTheme(R.style.AgainFont);break;
            case 3:setTheme(R.style.Galmuri9Font);break;
            case 4:setTheme(R.style.MugunghwaFont);break;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticeboard);

        activity = this;

        Toolbar toolbar = findViewById(R.id.tb_noticeboard);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24); //왼쪽 상단 버튼 아이콘 지정

        int type = getIntent().getIntExtra("type", 0);
        String id = getIntent().getStringExtra("id");

        TextView title = findViewById(R.id.tv_noticeboard_title);
        switch (type){
            case FREE_BULLETIN_BOARD:
                title.setText("자유게시판");
                break;

            case QUESTION_BULLETIN_BOARD:
                title.setText("질문게시판");
                break;

            case SCHOOL_BULLETIN_BOARD:
                title.setText("학교게시판");
                break;
        }

        BulletinBoardListFragment fragment = new BulletinBoardListFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        if(id != null)
            args.putString("id", id);
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