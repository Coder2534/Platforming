package com.android.platforming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.platforming.clazz.TelephoneItem;
import com.example.platforming.R;

import java.util.ArrayList;

public class TelephoneAdapter extends BaseAdapter {
    ArrayList<TelephoneItem> items = new ArrayList<TelephoneItem>();
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
        TelephoneItem telephoneItem = items.get(pos);
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_listview_telephone, viewGroup, false);
        }

        TextView tv_team = view.findViewById(R.id.tv_team);
        TextView tv_teacher = view.findViewById(R.id.tv_teacher);
        TextView tv_telephone = view.findViewById(R.id.tv_telephone);

        tv_team.setText(telephoneItem.getClazz());
        tv_teacher.setText(telephoneItem.getName());
        tv_telephone.setText(telephoneItem.getPhone());
        return view;
    }
    public void  addItem(TelephoneItem item){
        items.add(item);
    }
}