package com.android.platforming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.platforming.clazz.ExpandableList;
import com.android.platforming.clazz.ExpandableListItem;
import com.example.platforming.R;

import java.util.ArrayList;

public class ListviewAdapter extends BaseExpandableListAdapter {
    ArrayList<ExpandableListItem> parentItems; //부모 리스트를 담을 배열
    ArrayList<ArrayList<ExpandableListItem>> childItems; //자식 리스트를 담을 배열
    ArrayList<ArrayList<String>> childClasses;

    public void setParentItems(ArrayList<ExpandableListItem> parentItems){
        this.parentItems = parentItems;
    }

    public void setChildItems(ArrayList<ArrayList<ExpandableListItem>> childItems){
        this.childItems = childItems;
    }

    //각 리스트의 크기 반환
    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childItems.get(groupPosition).size();
    }

    //리스트의 아이템 반환
    @Override
    public ExpandableListItem getGroup(int groupPosition) {
        return parentItems.get(groupPosition);
    }

    @Override
    public ExpandableListItem getChild(int groupPosition, int childPosition) {
        return childItems.get(groupPosition).get(childPosition);
    }

    //리스트 아이템의 id 반환
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //동일한 id가 항상 동일한 개체를 참조하는지 여부를 반환
    @Override
    public boolean hasStableIds() {
        return true;
    }

    //리스트 각각의 row에 view를 설정
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;
        Context context = parent.getContext();

        //convertView가 비어있을 경우 xml파일을 inflate 해줌
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_listview_parent, parent, false);
        }

        //View들은 반드시 아이템 레이아웃을 inflate한 뒤에 작성할 것
        TextView title = v.findViewById(R.id.tv_listview_parent_title);
        title.setText(parentItems.get(groupPosition).getTitle());

        if(parentItems.get(groupPosition).getCategory() != -1){
            ImageView category = v.findViewById(R.id.iv_listview_parent_category);
            category.setImageResource(parentItems.get(groupPosition).getCategory());
        }

        //그룹 펼쳐짐 여부에 따라 아이콘 변경
        ImageView arrowIcon = v.findViewById(R.id.iv_listview_parent_expand);
        if (isExpanded)
            arrowIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
        else
            arrowIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);

        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
        Context context = parent.getContext();


        ExpandableListItem item = ExpandableListItem.class.cast(getChild(groupPosition, childPosition));
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_listview_child, parent, false);
        }

        TextView title = v.findViewById(R.id.tv_listview_child_title);
        title.setText(item.getTitle());
        if(item.getCategory() != -1){
            ImageView imageView = v.findViewById(R.id.iv_listview_child_category);
            imageView.setImageResource(item.getCategory());
        }
        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //리스트에 새로운 아이템을 추가
    public void addItem(int groupPosition, ExpandableListItem item) {
        childItems.get(groupPosition).add(item);
    }

    //리스트 아이템을 삭제
    public void removeChild(int groupPosition, int childPosition) {
        childItems.get(groupPosition).remove(childPosition);
    }
}
