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

    int itemPosition = 0;
    final int numColumns;

    TableItem crossCriterion;
    ArrayList<TableItem> columnCriteria;
    ArrayList<TableItem> rowCriteria;
    ArrayList<TableItem> tableItems;

    public TableAdapter(TableItem crossCriterion, ArrayList<TableItem> columnCriteria, ArrayList<TableItem> rowCriteria, ArrayList<TableItem> tableItems){
        Log.d("TableAdapter", "public");
        numColumns = columnCriteria.size() + 1;
        this.crossCriterion = crossCriterion;
        this.columnCriteria = columnCriteria;
        this.rowCriteria = rowCriteria;
        this.tableItems = tableItems;
    }

    @Override
    public int getCount() {
        return 1 + columnCriteria.size() + rowCriteria.size() + tableItems.size();
    }


    int itemPosition2 = 0;
    @Override
    public Object getItem(int position) {
        TableItem tableItem;

        if(position == 0)
            tableItem = crossCriterion;
        else if(position / numColumns == 0)
            tableItem = columnCriteria.get(position % numColumns - 1);
        else if(position % numColumns == 0){
            if(position / numColumns < rowCriteria.size() + 1)
                tableItem = rowCriteria.get(position / numColumns - 1);
            else
                tableItem = new TableItem();
        }
        else
            tableItem = tableItems.get(itemPosition2++);;

        return tableItem;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        Log.d("TableAdapter", String.valueOf(position));

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_recyclerview_tableitem, parent, false);
        }

        TextView mainText = convertView.findViewById(R.id.tv_schedule_maintext);
        TextView subText = convertView.findViewById(R.id.tv_schedule_subtext);

        TableItem tableItem;

        if(position == 0)
            tableItem = crossCriterion;
        else if(position / numColumns == 0)
            tableItem = columnCriteria.get(position % numColumns - 1);
        else if(position % numColumns == 0){
            if(position / numColumns < rowCriteria.size() + 1)
                tableItem = rowCriteria.get(position / numColumns - 1);
            else
                tableItem = new TableItem();
        }
        else
            tableItem = tableItems.get(itemPosition++);

        if(tableItem.getMainText() == null)
            mainText.setVisibility(View.GONE);
        else
            mainText.setText(tableItem.getMainText());

        if(tableItem.getSubText() == null)
            subText.setVisibility(View.GONE);
        else
            subText.setText(tableItem.getSubText());

        return convertView;
    }
}
