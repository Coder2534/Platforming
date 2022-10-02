package com.android.platforming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.platforming.clazz.SchoolIntroduceItem;
import com.android.platforming.R;

import java.util.ArrayList;

public class SchoolIntroduceAdapter extends BaseAdapter {
    ArrayList<SchoolIntroduceItem> items = new ArrayList<SchoolIntroduceItem>();
    Context context;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int pos) {
        return items.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        SchoolIntroduceItem schoolIntroduceItem = items.get(pos);
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_listview_schoolintroduce, viewGroup, false);
        }

        TextView tv_listview_explain = view.findViewById(R.id.tv_listview_explain);
        ImageView iv_listview_schoolintroduce = view.findViewById(R.id.iv_listview_schoolintroduce);

        tv_listview_explain.setText(schoolIntroduceItem.getExplain());
        iv_listview_schoolintroduce.setImageResource(schoolIntroduceItem.getImg());
        return view;
    }
    public void  addItem(SchoolIntroduceItem item){
        items.add(item);
    }
}
