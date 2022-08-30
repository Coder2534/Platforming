package com.android.platforming.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.android.platforming.clazz.ExpandableList;
import com.android.platforming.fragment.MainPageFragment;
import com.android.platforming.clazz.User;
import com.android.platforming.fragment.MyInfoFragment;
import com.android.platforming.fragment.PointStoreFragment;
import com.android.platforming.fragment.SchoolIntroduceFragment;
import com.android.platforming.fragment.SchoolScheduleFragment;
import com.android.platforming.fragment.SchoolmealFragment;
import com.android.platforming.fragment.TelephoneFragment;
import com.android.platforming.interfaze.OnChildClickInterface;
import com.example.platforming.R;
import com.android.platforming.fragment.InitialSettingFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ExpandableList mainExpandableList;

    private static MainActivity mainActivity;

    boolean mainPage = false;
    int applytheme;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applytheme = 0;//형이 폰에 저장 돼있는 적용시키는 테마index넣기
        switch (applytheme){
            case 0:setTheme(R.style.Theme_Platforming);break;
            case 1:setTheme(R.style.PinkTheme);break;
            case 2:setTheme(R.style.BuleTheme);break;
            case 3:setTheme(R.style.GreenTheme);break;
            case 4:setTheme(R.style.BlackTheme);break;
        }
        Log.d("timeline", "MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;

        Toolbar toolbar = findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24); //왼쪽 상단 버튼 아이콘 지정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기

        drawerLayout = findViewById(R.id.dl_main);
        navigationView = findViewById(R.id.nv_main);

        setListView();
        setListener();

        if(User.getUser() == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.cl_main, new InitialSettingFragment()).commit();
        }
        else{
            setting();
        }
    }

    public void setting(){
        setHeader();
        mainPage = true;
        getSupportFragmentManager().beginTransaction().replace(R.id.cl_main, new MainPageFragment()).commit();
    }

    private void setHeader(){
        View header = navigationView.getHeaderView(0);
        ImageView profile = header.findViewById(R.id.iv_navigation_header_profile);
        profile.setImageResource(User.getUser().getProfile());
        TextView point = header.findViewById(R.id.tv_navigation_header_point);
        point.setText(User.getUser().getPoint() + "p");
        TextView username = header.findViewById(R.id.tv_navigation_header_username);
        username.setText(User.getUser().getUsername());
        TextView info = header.findViewById(R.id.tv_navigation_header_info);
        String studentId = User.getUser().getStudentId();
        info.setText(String.format("%c학년 %s반 %s번", studentId.charAt(0), studentId.substring(1, 3).replaceFirst("^0+(?!$)", ""), studentId.substring(3, 5).replaceFirst("^0+(?!$)", "")));
    }

    private void setListView(){
        RelativeLayout relativeLayout = findViewById(R.id.ll_main);

        mainExpandableList = new ExpandableList(this);
        relativeLayout.addView(mainExpandableList, 0);

        mainExpandableList.addParent("내정보", R.drawable.ic_baseline_person_24);
        mainExpandableList.addChild(0, "내정보", new MyInfoFragment());
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

        mainExpandableList.addParent("포인트 상점", R.drawable.ic_baseline_shopping_cart_24);
        mainExpandableList.addChild(3, "디자인", new PointStoreFragment());

        mainExpandableList.addParent("학교 홈페이지", R.drawable.ic_baseline_home_24);
        mainExpandableList.addChild(4, "공식 홈페이지", toggleActivity(WebViewActivity.class, "homepage"));
        mainExpandableList.addChild(4, "리로스쿨", toggleActivity(WebViewActivity.class, "riroschool"));

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


    private void setListener(){
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
        if (item.getItemId() == android.R.id.home) {// 왼쪽 상단 버튼 눌렀을 때
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() { //뒤로가기 했을 때
        if (drawerLayout != null && mainPage && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public static MainActivity getActivity(){
        return mainActivity;
    }

    public void theme(int applytheme){
        switch (applytheme){
            case 0:setTheme(R.style.Theme_Platforming);break;
            case 1:setTheme(R.style.PinkTheme);break;
            case 2:setTheme(R.style.BuleTheme);break;
            case 3:setTheme(R.style.GreenTheme);break;
            case 4:setTheme(R.style.BlackTheme);break;
        }
        TaskStackBuilder.create(getActivity())
                .addNextIntent(new Intent(getActivity(), MainActivity.class))
                .addNextIntent(getActivity().getIntent())
                .startActivities();
        recreate();
    }

}