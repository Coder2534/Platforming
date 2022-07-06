package com.android.platforming.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.platforming.adapter.ListviewAdapter;
import com.android.platforming.clazz.ChildItem;
import com.android.platforming.clazz.ParentItem;
import com.android.platforming.fragment.MainPageFragment;
import com.android.platforming.clazz.User;
import com.example.platforming.R;
import com.android.platforming.fragment.UserInitialSettingFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ExpandableListView listView;
    ListviewAdapter adapter;
    ArrayList<ParentItem> groupList = new ArrayList<>(); //부모 리스트
    ArrayList<ArrayList<ChildItem>> childList = new ArrayList<>(); //자식 리스트
    ArrayList<ArrayList<ChildItem>> monthArray = new ArrayList<>(); //1월 ~ 12

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
        listView = (ExpandableListView) findViewById(R.id.el_main);

        View nav_header_view = navigationView.inflateHeaderView(R.layout.layout_navigation_header); //헤더 동적 생성
        navigationView.inflateMenu(R.layout.);

        if (navigationView.getHeaderView(0) != null) {
            int headerHeight = nav_header_view.getMeasuredHeight();
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(0,headerHeight,0,0);
            Log.w("headerHeight", Integer.toString(headerHeight));
            listView.setLayoutParams(params);
        }

        //monthArray에 1월~12월 배열을 모두 추가
        for (int i = 0; i < 12; i++)
            monthArray.add(new ArrayList<ChildItem>());


        //어댑터에 각각의 배열 등록
        adapter = new ListviewAdapter();
        adapter.setParentItems(groupList);
        adapter.setChildItems(childList);

        listView.setAdapter(adapter);
        listView.setGroupIndicator(null); //리스트뷰 기본 아이콘 표시 여부

        setListItems();

        //리스트 클릭시 지출 항목이 토스트로 나타난다
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), adapter.getChild(groupPosition, childPosition).getTitle(), Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        //리스트 초기화 함수
        if(User.getUser() == null){
            Log.w("Debug", "user isEmpty");
            getSupportFragmentManager().beginTransaction().replace(R.id.cl_main, new UserInitialSettingFragment()).commit();
        }
        else{
            setHeader();
            setListner();
            getSupportFragmentManager().beginTransaction().replace(R.id.cl_main, new MainPageFragment()).commit();
        }
    }

    public void setListItems() {
        groupList.clear();
        childList.clear();

        childList.addAll(monthArray);

        //부모 리스트 내용 추가
        for (int i = 1; i <= 12; i++) {
            groupList.add(new ParentItem(i + "월", childList.get(i - 1).size() + "건"));
        }

        adapter.notifyDataSetChanged();
    }

    public void setHeader(){
        //View nav_header_view = navigationView.inflateHeaderView(R.layout.nav_header_main); //헤더 동적 생성
        View nav_header_view = navigationView.getHeaderView(0); //헤더 가져오기
        TextView nav_header_id_text = (TextView) nav_header_view.findViewById(R.id.tv_navigation_info);
        nav_header_id_text.setText("Test");
    }

    public void setListner(){
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();

            int id = menuItem.getItemId();
            String title = menuItem.getTitle().toString();

            if(id == R.id.item_navigation_info){

            }
            else if(id == R.id.item_navigation_setting){

            }
            else if(id == R.id.item_navigation_report){

            }

            return true;
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