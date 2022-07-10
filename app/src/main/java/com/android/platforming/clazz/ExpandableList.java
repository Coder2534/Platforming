package com.android.platforming.clazz;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.platforming.adapter.ListviewAdapter;
import com.android.platforming.interfaze.OnChildClickInterface;
import com.example.platforming.R;

import java.util.ArrayList;

public class ExpandableList extends ExpandableListView {
    ListviewAdapter adapter;
    ArrayList<ExpandableListItem> groupList = new ArrayList<>(); //부모 리스트
    ArrayList<ArrayList<Object>> childList = new ArrayList<>(); //자식 리스트
    ArrayList<ArrayList<String>> childClssList = new ArrayList<>();
    ArrayList<ArrayList<OnChildClickInterface>> interfazeList = new ArrayList<>();

    FragmentManager fragmentManager;

    public ExpandableList(Context context, FragmentManager fragmentManager) {
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setGroupIndicator(null); //리스트뷰 기본 아이콘 표시 여부
        this.fragmentManager = fragmentManager;
    }

    public void addParent(String title){
        groupList.add(new ExpandableListItem(title));
        childList.add(new ArrayList<>());
        childClssList.add(new ArrayList<>());
        interfazeList.add(new ArrayList<>());
    }

    public void addParent(String title, int category){
        groupList.add(new ExpandableListItem(title, category));
        childList.add(new ArrayList<>());
        childClssList.add(new ArrayList<>());
        interfazeList.add(new ArrayList<>());
    }

    private void addChild(int parentIndex, ExpandableListItem item, OnChildClickInterface interfaze){
        childList.get(parentIndex).add(item);
        childClssList.get(parentIndex).add("String");
        interfazeList.get(parentIndex).add(interfaze);
    }

    public void addChild(int parentIndex, String title, Fragment fragment){
        addChild(parentIndex, new ExpandableListItem(title), onClickFragment(fragment));
    }

    public void addChild(int parentIndex, String title, int category, Fragment fragment){
        addChild(parentIndex, new ExpandableListItem(title, category), onClickFragment(fragment));
    }

    public void addChild(int parentIndex, String title, OnChildClickInterface interfaze){
        addChild(parentIndex, new ExpandableListItem(title), interfaze);
    }

    public void addChild(int parentIndex, String title, int category, OnChildClickInterface interfaze){
        addChild(parentIndex, new ExpandableListItem(title, category), interfaze);
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

    public void setListner(){
        setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(childClssList.get(groupPosition).get(childPosition).equals("String")){
                    interfazeList.get(groupPosition).get(childPosition).onClick(groupPosition, childPosition);
                    Toast.makeText(getContext(), ExpandableListItem.class.cast(childList.get(groupPosition).get(childPosition)).getTitle(), Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        for(int i = 0; i < childClssList.size(); ++i)
            for(int j = 0; j < childClssList.get(i).size(); ++j)
                if(childClssList.get(i).get(j).equals("ExpandableList"))
                    ExpandableList.class.cast(childList.get(i).get(j)).setListner();
    }

    private OnChildClickInterface onClickFragment(Fragment fragment){
        return (groupPosition, childPosition) -> fragmentManager.beginTransaction().replace(R.id.cl_main, fragment).addToBackStack(null).commit();
    }
}
