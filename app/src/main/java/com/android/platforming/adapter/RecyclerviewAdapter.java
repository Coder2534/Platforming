package com.android.platforming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.platforming.R;

import java.util.ArrayList;

class ViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;

    ViewHolder(Context context, View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.textView);
    }
}

public class RecyclerviewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<String> arrayList;

    public RecyclerviewAdapter() {
        arrayList = new ArrayList<>();
    }

    private ArrayList<String> mData = null ;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_recyclerview_setting, parent, false);

        ViewHolder viewholder = new ViewHolder(context, view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = arrayList.get(position);
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // 데이터를 입력
    public void setArrayData(String strData) {
        arrayList.add(strData);
    }
}
