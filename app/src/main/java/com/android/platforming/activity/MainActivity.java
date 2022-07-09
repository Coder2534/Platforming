package com.android.platforming.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

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
            setHeader();
            mainExpandableList.setListner(getSupportFragmentManager());
            getSupportFragmentManager().beginTransaction().replace(R.id.cl_main, new MainPageFragment()).commit();
        }
    }

    private void setListView(){
        RelativeLayout relativeLayout = findViewById(R.id.ll_main);

        mainExpandableList = new ExpandableList(this);
        relativeLayout.addView(mainExpandableList, 0);

        mainExpandableList.addParent("내정보", R.drawable.ic_baseline_dehaze_24);

        mainExpandableList.addParent("커뮤니티", R.drawable.ic_baseline_dehaze_24);
        mainExpandableList.addChild(1, "1학년", new Fragment());
        mainExpandableList.addChild(1, "2학년", new Fragment());
        mainExpandableList.addChild(1, "3학년", new Fragment());

        mainExpandableList.addParent("포인트 상점", R.drawable.ic_baseline_dehaze_24);
        mainExpandableList.addChild(2, "꾸미기", new Fragment());
        mainExpandableList.addChild(2, "기프티콘", new Fragment());

        mainExpandableList.setAdapter();
    }

    public void setHeader(){
        //View nav_header_view = navigationView.inflateHeaderView(R.layout.nav_header_main); //헤더 동적 생성
        View nav_header_view = navigationView.getHeaderView(0); //헤더 가져오기
        TextView nav_header_id_text = nav_header_view.findViewById(R.id.tv_navigation_info);
        nav_header_id_text.setText("Test");
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