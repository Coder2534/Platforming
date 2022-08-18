package com.android.platforming.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.platforming.adapter.PostViewAdapter;
import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.Post;
import com.android.platforming.fragment.NoticeBoardDetailFragment;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

public class NoticeBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

        String workName = getIntent().getStringExtra("workName");

        Toolbar toolbar = findViewById(R.id.tb_noticeboard);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24); //왼쪽 상단 버튼 아이콘 지정

        RecyclerView recyclerView = findViewById(R.id.rv_noticeboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PostViewAdapter postViewAdapter = new PostViewAdapter(Post.getPosts());
        postViewAdapter.setListenerInterface(new ListenerInterface() {
            @Override
            public void onSuccess(int position) {
                NoticeBoardDetailFragment fragment = new NoticeBoardDetailFragment();
                Bundle args = new Bundle();
                args.putInt("postion", position);
                fragment.setArguments(args);

                getSupportFragmentManager().beginTransaction().replace(R.id.fl_noticeboard, fragment).commit();
            }
        });

        recyclerView.setAdapter(postViewAdapter);

        FirestoreManager firestoreManager = new FirestoreManager();
        firestoreManager.readPostData(workName, new ListenerInterface() {
            @Override
            public void onSuccess() {
                Button button = findViewById(R.id.btn_noticeboard_post);
                button.setOnClickListener(v -> {
                    postViewAdapter.notifyDataSetChanged();
                    Intent intent = new Intent(getApplicationContext(), NoticeBoardRegisterActivity.class);
                    intent.putExtra("workName", workName);
                    startActivity(intent);
                    overridePendingTransition(R.anim.start_activity_noticeboard, R.anim.none);
                });
            }
        });
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