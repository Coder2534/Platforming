package com.android.platforming.clazz;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.android.platforming.activity.MainActivity;
import com.android.platforming.adapter.ListviewAdapter;
import com.example.platforming.R;

import java.util.ArrayList;

public class ExpandableList extends ExpandableListView {
    ListviewAdapter adapter;
    ArrayList<ExpandableListItem> groupList = new ArrayList<>(); //부모 리스트
    ArrayList<ArrayList<Object>> childList = new ArrayList<>(); //자식 리스트
    ArrayList<ArrayList<String>> childClssList = new ArrayList<>();
    ArrayList<ArrayList<Fragment>> fragmentList = new ArrayList<>();

    public ExpandableList(Context context){
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setGroupIndicator(null); //리스트뷰 기본 아이콘 표시 여부
    }

    public void addParent(String title){
        groupList.add(new ExpandableListItem(title));
        childList.add(new ArrayList<>());
        childClssList.add(new ArrayList<>());
        fragmentList.add(new ArrayList<>());
    }

    public void addParent(String title, int category){
        groupList.add(new ExpandableListItem(title, category));
        childList.add(new ArrayList<>());
        childClssList.add(new ArrayList<>());
        fragmentList.add(new ArrayList<>());
    }

    public void addChild(int parentIndex, String title, Fragment fragment){
        ExpandableListItem item = new ExpandableListItem(title);
        childList.get(parentIndex).add(item);
        childClssList.get(parentIndex).add("String");
        fragmentList.get(parentIndex).add(fragment);
}

    public void addChild(int parentIndex, String title, int category, Fragment fragment){
        ExpandableListItem item = new ExpandableListItem(title, category);
        childList.get(parentIndex).add(item);
        childClssList.get(parentIndex).add("String");
        fragmentList.get(parentIndex).add(fragment);
    }

    public void addChild(int parentIndex, ExpandableList expandableList){
        childList.get(parentIndex).add(expandableList);
        childClssList.get(parentIndex).add("ExpandableList");
    }

    public void setAdapter() {
        //어댑터에 각각의 배열 등록
        adapter = new ListviewAdapter();
        adapter.setParentItems(groupList);
        adapter.setChildItems(childList, childClssList);

        setAdapter(adapter);
    }

    public void setListner(FragmentManager fragmentManager){
        setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(childClssList.get(groupPosition).get(childPosition).equals("String")){
                    fragmentManager.beginTransaction().replace(R.id.cl_main, fragmentList.get(groupPosition).get(childPosition)).addToBackStack(null).commit();
                }
                return true;
            }
        });
        for(int i = 0; i < childClssList.size(); ++i)
            for(int j = 0; j < childClssList.get(i).size(); ++j)
                if(childClssList.get(i).get(j).equals("ExpandableList"))
                    ExpandableList.class.cast(childList.get(i).get(j)).setListner(fragmentManager);
    }
}
