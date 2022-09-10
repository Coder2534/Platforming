package com.android.platforming.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.platforming.clazz.TableItem;
import com.example.platforming.R;

import java.util.ArrayList;

public class TableAdapter extends BaseAdapter {
    final int numColumns = 6;

    TableItem crossCriterion = new TableItem("시간");

    ArrayList<ArrayList<TableItem>> schedules;

    public TableAdapter(ArrayList<ArrayList<TableItem>> schedules){
        this.schedules = schedules;
    }

    @Override
    public int getCount() {
        int posY = 0;

        for(int i = 0; i < schedules.size(); ++i){
            Log.d("TableAdapter", String.valueOf(schedules.get(i).size()));
            if(posY < schedules.get(i).size()){
                posY = schedules.get(i).size();
            }
        }

        int count = numColumns + posY * numColumns;
        Log.d("TableAdapter", "getCount : " + count);

        return count;
    }


    @Override
    public Object getItem(int position) {
        return getTableItem(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_recyclerview_tableitem, parent, false);
        }

        TextView mainText = convertView.findViewById(R.id.tv_schedule_maintext);
        TextView subText = convertView.findViewById(R.id.tv_schedule_subtext);

        TableItem tableItem = getTableItem(position);

        if(tableItem.getMainText().equals(""))
            mainText.setVisibility(View.GONE);
        else
            mainText.setText(tableItem.getMainText());

        if(tableItem.getSubText().equals(""))
            subText.setVisibility(View.GONE);
        else
            subText.setText(tableItem.getSubText());

        return convertView;
    }

    private TableItem getTableItem(int position) {
        if (position == 0)
            return crossCriterion;
        else if (position / numColumns == 0)
            return new TableItem(getDayOfWeek(position % numColumns - 1));
        else if (position % numColumns == 0)
            return new TableItem(position / numColumns + "교시");
        else if (position / numColumns <= schedules.get(position % numColumns - 1).size())
            return schedules.get(position % numColumns - 1).get(position / numColumns - 1);
        else
            return new TableItem();
    }

    private String getDayOfWeek(int index){
        switch (index){
            case 0:
                return "월";

            case 1:
                return "화";

            case 2:
                return "수";

            case 3:
                return "목";

            default:
                return "금";
        }
    }
}
