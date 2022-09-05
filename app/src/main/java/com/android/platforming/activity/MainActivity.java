package com.android.platforming.activity;

import static com.android.platforming.InitApplication.HOMEPAGE;
import static com.android.platforming.InitApplication.RIROSCHOOL;
import static com.android.platforming.clazz.Post.FREE_BULLETIN_BOARD;
import static com.android.platforming.clazz.Post.QUESTION_BULLETIN_BOARD;
import static com.android.platforming.clazz.Post.SCHOOL_BULLETIN_BOARD;
import static com.android.platforming.clazz.User.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.platforming.InitApplication;
import com.android.platforming.clazz.CustomDialog;
import com.android.platforming.clazz.ExpandableList;
import com.android.platforming.fragment.MainPageFragment;
import com.android.platforming.fragment.MyInfoFragment;
import com.android.platforming.fragment.MyPostFragment;
import com.android.platforming.fragment.PointStoreFragment;
import com.android.platforming.fragment.SchoolIntroduceFragment;
import com.android.platforming.fragment.SchoolScheduleFragment;
import com.android.platforming.fragment.SchoolmealFragment;
import com.android.platforming.fragment.TelephoneFragment;
import com.android.platforming.interfaze.ListenerInterface;
import com.android.platforming.interfaze.OnChildClickInterface;
import com.example.platforming.R;
import com.android.platforming.fragment.InitialSettingFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ExpandableList mainExpandableList;

    int homeFragmentIdentifier = -1;

    private static Activity activity;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("timeline", "MainActivity");
        InitApplication initApplication = ((InitApplication)getApplication());
        initApplication.refreshAppliedTheme();
        initApplication.refreshAppliedFont();
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
        setContentView(R.layout.activity_main);

        activity = this;

        Toolbar toolbar = findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24); //왼쪽 상단 버튼 아이콘 지정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기

        drawerLayout = findViewById(R.id.dl_main);
        navigationView = findViewById(R.id.nv_main);

        setDrawerLayoutView();

        if(user == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.cl_main, new InitialSettingFragment()).commit();
        else
            setting();
    }

    public void setting(){
        setHeader();
        homeFragmentIdentifier = getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.cl_main, new MainPageFragment()).commit();
    }

    TextView point;
    private void setHeader(){
        View header = navigationView.getHeaderView(0);
        ImageView profile = header.findViewById(R.id.iv_navigation_header_profile);
        profile.setImageResource(user.getProfile());
        point = header.findViewById(R.id.tv_navigation_header_point);
        point.setText(user.getPoint() + "p");
        TextView username = header.findViewById(R.id.tv_navigation_header_username);
        username.setText(user.getUsername());
        TextView info = header.findViewById(R.id.tv_navigation_header_info);
        String studentId = user.getStudentId();
        info.setText(String.format("%c학년 %s반 %s번", studentId.charAt(0), studentId.substring(1, 3).replaceFirst("^0+(?!$)", ""), studentId.substring(3, 5).replaceFirst("^0+(?!$)", "")));
    }

    private void setDrawerLayoutView(){
        RelativeLayout relativeLayout = findViewById(R.id.ll_main);

        mainExpandableList = new ExpandableList(this);
        relativeLayout.addView(mainExpandableList, 0);

        mainExpandableList.addParent("내정보", R.drawable.ic_baseline_person_24);
        mainExpandableList.addChild(0, "내정보", new MyInfoFragment());
        mainExpandableList.addChild(0, "나의 게시물", new MyPostFragment());

        mainExpandableList.addParent("학교 정보", R.drawable.ic_baseline_school_24);
        mainExpandableList.addChild(1, "학교소개", new SchoolIntroduceFragment());
        mainExpandableList.addChild(1, "전화번호", new TelephoneFragment());
        mainExpandableList.addChild(1, "식단표", new SchoolmealFragment());
        mainExpandableList.addChild(1, "학사일정", new SchoolScheduleFragment());

        mainExpandableList.addParent("게시판", R.drawable.ic_baseline_format_list_bulleted_24);
        mainExpandableList.addChild(2, "자유게시판", toggleActivity(BulletinBoardActivity.class, FREE_BULLETIN_BOARD));
        mainExpandableList.addChild(2, "질문게시판", toggleActivity(BulletinBoardActivity.class, QUESTION_BULLETIN_BOARD));
        mainExpandableList.addChild(2, "학교게시판", toggleActivity(BulletinBoardActivity.class, SCHOOL_BULLETIN_BOARD));

        mainExpandableList.addParent("포인트 상점", R.drawable.ic_baseline_shopping_cart_24);
        mainExpandableList.addChild(3, "디자인", new PointStoreFragment());

        mainExpandableList.addParent("학교 홈페이지", R.drawable.ic_baseline_web_24);
        mainExpandableList.addChild(4, "공식 홈페이지", toggleActivity(WebViewActivity.class, HOMEPAGE));
        mainExpandableList.addChild(4, "리로스쿨", toggleActivity(WebViewActivity.class, RIROSCHOOL));

        mainExpandableList.setAdapter();

        mainExpandableList.setListner(getSupportFragmentManager(), () -> drawerLayout.closeDrawer(GravityCompat.START));

        TextView setting = findViewById(R.id.tv_main_setting);
        setting.setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(settingIntent);
        });
    }

    private OnChildClickInterface toggleActivity(Class clazz, int type){
        return () -> {
            Intent intent = new Intent(this, clazz);
            intent.putExtra("type", type);
            startActivity(intent);
            overridePendingTransition(R.anim.start_activity_noticeboard, R.anim.none);
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("FragmentIdentifier : ", String.valueOf(homeFragmentIdentifier));
        if(homeFragmentIdentifier != -1){
            switch (item.getItemId()){
                case android.R.id.home:
                    drawerLayout.openDrawer(GravityCompat.START);
                    return true;

                case R.id.action_home:
                    getSupportFragmentManager().popBackStack(homeFragmentIdentifier, 0);
                    return true;

                case R.id.action_dailytask:
                    ListenerInterface listenerInterface = new ListenerInterface() {
                        @Override
                        public void onSuccess() {
                            user.setPoint(user.getPoint() + user.getPoint_receipt());
                            user.setPoint_receipt(0);
                            point.setText(user.getPoint() + "p");
                        }
                    };

                    user.attendanceCheck(new ListenerInterface() {
                        @Override
                        public void onSuccess(long timeInMillis) {
                            user.setPoint_receipt(10);
                            user.setDailyTasks(Arrays.asList(1L, 0L, 0L, 0L));
                            user.setLastSignIn(timeInMillis);
                            runOnUiThread(() -> {
                                CustomDialog customDialog = new CustomDialog();
                                customDialog.dailyTaskDialog(activity, listenerInterface);
                            });
                        }

                        @Override
                        public void onFail() {
                            runOnUiThread(() -> {
                                CustomDialog customDialog = new CustomDialog();
                                customDialog.dailyTaskDialog(activity, listenerInterface);
                            });
                        }
                    });
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() { //뒤로가기 했을 때
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if(getSupportFragmentManager().getBackStackEntryCount() < 2){
            finish();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }

    public static void reCreate(){
        activity.recreate();
    }
}