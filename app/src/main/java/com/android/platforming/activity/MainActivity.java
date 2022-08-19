package com.android.platforming.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.platforming.clazz.Alarm;
import com.android.platforming.clazz.ExpandableList;
import com.android.platforming.fragment.MainPageFragment;
import com.android.platforming.clazz.User;
import com.android.platforming.fragment.SchoolIntroduceFragment;
import com.android.platforming.fragment.SchoolScheduleFragment;
import com.android.platforming.fragment.SchoolmealFragment;
import com.android.platforming.fragment.TelephoneFragment;
import com.android.platforming.interfaze.OnChildClickInterface;
import com.android.platforming.recevier.AlarmReceiver;
import com.example.platforming.R;
import com.android.platforming.fragment.UserInitialSettingFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ExpandableList mainExpandableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("timeline", "MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
            setListener();
            getSupportFragmentManager().beginTransaction().replace(R.id.cl_main, new MainPageFragment()).commit();
        }
    }

    private void setListView(){
        RelativeLayout relativeLayout = findViewById(R.id.ll_main);

        mainExpandableList = new ExpandableList(this);
        relativeLayout.addView(mainExpandableList, 0);

        mainExpandableList.addParent("내정보", R.drawable.ic_baseline_person_24);
        mainExpandableList.addChild(0, "내정보", new Fragment());
        mainExpandableList.addChild(0, "나의 게시물", new Fragment());

        mainExpandableList.addParent("학교 정보", R.drawable.ic_baseline_school_24);
        mainExpandableList.addChild(1, "학교소개", new SchoolIntroduceFragment());
        mainExpandableList.addChild(1, "전화번호", new TelephoneFragment());
        mainExpandableList.addChild(1, "식단표", new SchoolmealFragment());
        mainExpandableList.addChild(1, "학사일정", new SchoolScheduleFragment());

        mainExpandableList.addParent("게시판", R.drawable.ic_baseline_format_list_bulleted_24);
        mainExpandableList.addChild(2, "자유게시판", toggleActivity(NoticeBoardActivity.class, "free bulletin board"));
        mainExpandableList.addChild(2, "질문게시판", toggleActivity(NoticeBoardActivity.class, "question bulletin board"));
        mainExpandableList.addChild(2, "학교게시판", toggleActivity(NoticeBoardActivity.class, "school bulletin board"));

        /*
        mainExpandableList.addParent("커뮤니티", R.drawable.ic_baseline_comment_24);
        mainExpandableList.addChild(3, "1학년", new Fragment());
        mainExpandableList.addChild(3, "2학년", new Fragment());
        mainExpandableList.addChild(3, "3학년", new Fragment());
        mainExpandableList.addChild(3, "전체", toggleActivity(CommunityActivity.class, "all"));
         */

        mainExpandableList.addParent("포인트 상점", R.drawable.ic_baseline_shopping_cart_24);
        mainExpandableList.addChild(4, "디자인", new Fragment());

        mainExpandableList.addParent("학교 홈페이지", R.drawable.ic_baseline_home_24);
        mainExpandableList.addChild(5, "공식 홈페이지", toggleActivity(WebViewActivity.class, "homepage"));
        mainExpandableList.addChild(5, "리로스쿨", toggleActivity(WebViewActivity.class, "riroschool"));

        mainExpandableList.setAdapter();
    }

    private OnChildClickInterface toggleActivity(Class clazz,String workName){
        OnChildClickInterface interfaze = () -> {
            Intent intent = new Intent(this, clazz);
            intent.putExtra("workName", workName);
            startActivity(intent);
            overridePendingTransition(R.anim.start_activity_noticeboard, R.anim.none);
        };
        return interfaze;
    }


    public void setListener(){
        mainExpandableList.setListner(getSupportFragmentManager(), () -> drawerLayout.closeDrawer(GravityCompat.START));

        TextView setting = findViewById(R.id.tv_main_setting);
        setting.setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(settingIntent);
        });
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
}