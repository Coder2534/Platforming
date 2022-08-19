package com.android.platforming.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.android.platforming.adapter.PostViewAdapter;
import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.Post;
import com.android.platforming.fragment.NoticeBoardDetailFragment;
import com.android.platforming.fragment.NoticeBoardListFragment;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

import org.w3c.dom.Text;

public class NoticeBoardActivity extends AppCompatActivity {

    //1. 게시판, 댓글 두번 들어가야 뜸 - 수정
    //3. comment 추가 이후 보여지지 않음 - 수정
    //4. docutment 자동완성시 순서개판 - 수정
    //5. 전제적인 크기조절

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticeboard);

        String workName = getIntent().getStringExtra("workName");

        Toolbar toolbar = findViewById(R.id.tb_noticeboard);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24); //왼쪽 상단 버튼 아이콘 지정

        TextView title = findViewById(R.id.tv_noticeboard_title);
        switch (workName){
            case "free bulletin board":
                title.setText("자유게시판");
                break;

            case "question bulletin board":
                title.setText("질문게시판");
                break;

            case "school bulletin board":
                title.setText("학교게시판");
                break;
        }

        NoticeBoardListFragment fragment = new NoticeBoardListFragment();
        Bundle args = new Bundle();
        args.putString("workName", workName);
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
}