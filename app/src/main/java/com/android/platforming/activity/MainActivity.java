package com.android.platforming.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.platforming.clazz.ExpandableList;
import com.android.platforming.fragment.MainPageFragment;
import com.android.platforming.clazz.User;
import com.android.platforming.interfaze.OnChildClickInterface;
import com.example.platforming.R;
import com.android.platforming.fragment.UserInitialSettingFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ExpandableList mainExpandableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24); //왼쪽 상단 버튼 아이콘 지정

        drawerLayout = findViewById(R.id.dl_main);
        navigationView = findViewById(R.id.nv_main);

        setListView();

        if(User.getUser() == null){
            Log.w("Debug", "user isEmpty");
            getSupportFragmentManager().beginTransaction().replace(R.id.cl_main, new UserInitialSettingFragment()).commit();
        }
        else{
            View nav_header_view = navigationView.getHeaderView(0); //헤더 가져오기
            TextView nav_header_id_text = nav_header_view.findViewById(R.id.tv_navigation_header_info);
            nav_header_id_text.setText("Test");
            mainExpandableList.setListner();
            getSupportFragmentManager().beginTransaction().replace(R.id.cl_main, new MainPageFragment()).commit();
        }
    }

    private void setListView(){
        RelativeLayout relativeLayout = findViewById(R.id.ll_main);

        mainExpandableList = new ExpandableList(this, getSupportFragmentManager());
        relativeLayout.addView(mainExpandableList, 0);

        mainExpandableList.addParent("내정보", R.drawable.ic_baseline_dehaze_24);
        mainExpandableList.addChild(0, "내정보", new Fragment());
        mainExpandableList.addChild(0, "나의 게시물", new Fragment());

        mainExpandableList.addParent("학교 정보", R.drawable.ic_baseline_dehaze_24);
        mainExpandableList.addChild(1, "학교소개", new Fragment());

        ExpandableList telExpandableList = new ExpandableList(this, getSupportFragmentManager());
        telExpandableList.addParent("전화번호", R.drawable.ic_baseline_dehaze_24);
        telExpandableList.addChild(0, "1학년", new Fragment());
        telExpandableList.addChild(0, "2학년", new Fragment());
        telExpandableList.addChild(0, "3학년", new Fragment());
        telExpandableList.addChild(0, "기타", new Fragment());

        mainExpandableList.addChild(1, telExpandableList);
        mainExpandableList.addChild(1, "식단표", new Fragment());
        mainExpandableList.addChild(1, "학사일정", new Fragment());

        mainExpandableList.addParent("게시판", R.drawable.ic_baseline_dehaze_24);
        mainExpandableList.addChild(2, "자유게시판", new Fragment());
        mainExpandableList.addChild(2, "질문게시판", new Fragment());
        mainExpandableList.addChild(2, "학습자료 공유", new Fragment());

        mainExpandableList.addParent("커뮤니티", R.drawable.ic_baseline_dehaze_24);
        mainExpandableList.addChild(3, "1학년", new Fragment());
        mainExpandableList.addChild(3, "2학년", new Fragment());
        mainExpandableList.addChild(3, "3학년", new Fragment());
        mainExpandableList.addChild(3, "전체", new Fragment());

        mainExpandableList.addParent("포인트 상점", R.drawable.ic_baseline_dehaze_24);
        mainExpandableList.addChild(4, "디자인", new Fragment());
        mainExpandableList.addChild(4, "기프티콘", new Fragment());

        mainExpandableList.addParent("학교 홈페이지", R.drawable.ic_baseline_dehaze_24);
        mainExpandableList.addChild(5, "공식 홈페이지", (groupPosition, childPosition) -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://school.gyo6.net/geumohs"));
            startActivity(intent);
        });

        mainExpandableList.addChild(5, "리로스쿨", (groupPosition, childPosition) -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://geumo.riroschool.kr/"));
            startActivity(intent);
        });

        mainExpandableList.setAdapter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() { //뒤로가기 했을 때
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public ExpandableList getMainExpandableList() {
        return mainExpandableList;
    }
}