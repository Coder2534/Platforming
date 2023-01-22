package com.platforming.autonomy.clazz;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.platforming.autonomy.adapter.ListviewAdapter;
import com.platforming.autonomy.interfaze.OnChildClickInterface;
import com.android.autonomy.R;

import java.util.ArrayList;

public class ExpandableList extends ExpandableListView {
    ListviewAdapter adapter;
    ArrayList<ExpandableListItem> groupList = new ArrayList<>(); //부모 리스트
    ArrayList<ArrayList<ExpandableListItem>> childList = new ArrayList<>(); //자식 리스트
    ArrayList<ArrayList<OnChildClickInterface>> interfaceList = new ArrayList<>();
    ArrayList<ArrayList<Fragment>> fragmentList = new ArrayList<>();

    public ExpandableList(Context context){
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setGroupIndicator(null); //리스트뷰 기본 아이콘 표시 여부
    }

    public void addParent(String title){
        groupList.add(new ExpandableListItem(title));
        childList.add(new ArrayList<>());
        interfaceList.add(new ArrayList<>());
        fragmentList.add(new ArrayList<>());
    }

    public void addParent(String title, int category){
        groupList.add(new ExpandableListItem(title, category));
        childList.add(new ArrayList<>());
        interfaceList.add(new ArrayList<>());
        fragmentList.add(new ArrayList<>());
    }

    public void addChild(int parentIndex, String title, OnChildClickInterface onChildClickInterface){
        ExpandableListItem item = new ExpandableListItem(title);
        childList.get(parentIndex).add(item);
        interfaceList.get(parentIndex).add(onChildClickInterface);
        fragmentList.get(parentIndex).add(null);
}

    public void addChild(int parentIndex, String title, int category, OnChildClickInterface onChildClickInterface){
        ExpandableListItem item = new ExpandableListItem(title, category);
        childList.get(parentIndex).add(item);
        interfaceList.get(parentIndex).add(onChildClickInterface);
        fragmentList.get(parentIndex).add(null);
    }

    public void addChild(int parentIndex, String title, Fragment fragment){
        ExpandableListItem item = new ExpandableListItem(title);
        childList.get(parentIndex).add(item);
        interfaceList.get(parentIndex).add(null);
        fragmentList.get(parentIndex).add(fragment);
    }

    public void addChild(int parentIndex, String title, int category, Fragment fragment){
        ExpandableListItem item = new ExpandableListItem(title, category);
        childList.get(parentIndex).add(item);
        interfaceList.get(parentIndex).add(null);
        fragmentList.get(parentIndex).add(fragment);
    }

    public void setAdapter() {
        //어댑터에 각각의 배열 등록
        adapter = new ListviewAdapter();
        adapter.setParentItems(groupList);
        adapter.setChildItems(childList);

        setAdapter(adapter);
    }
    //test
    public void setListner(FragmentManager fragmentManager, OnChildClickInterface interfaze){
        setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(interfaceList.get(groupPosition).get(childPosition) != null)
                    interfaceList.get(groupPosition).get(childPosition).onClick();
                else
                    fragmentManager.beginTransaction().replace(R.id.cl_main, fragmentList.get(groupPosition).get(childPosition)).addToBackStack(null).commit();
                interfaze.onClick();
                return true;
            }
        });
    }
}
