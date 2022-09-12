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
            case 0:setTheme(R.style.LeferipointwhiteobliqueFont);break;
            case 1:setTheme(R.style.pyeongFont);break;
            case 2:setTheme(R.style.vitorFont);break;
            case 3:setTheme(R.style.Galmuri9Font);break;
            case 4:setTheme(R.style.tokkiFont);break;
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
        args.putInt("post", getIntent().getIntExtra("post", 0));
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