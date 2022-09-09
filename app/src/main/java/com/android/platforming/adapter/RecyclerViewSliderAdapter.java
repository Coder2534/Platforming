package com.android.platforming.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.platforming.clazz.TableItem;
import com.example.platforming.R;

import java.util.ArrayList;

public class RecyclerViewSliderAdapter extends RecyclerView.Adapter<RecyclerViewSliderAdapter.MyViewHolder> {
    private ArrayList<TableItem> schedules;

    public RecyclerViewSliderAdapter(ArrayList<TableItem> schedules) {
        this.schedules = schedules;
    }

    @NonNull
    @Override
    public RecyclerViewSliderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_slider, parent, false);
        return new RecyclerViewSliderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewSliderAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView mRecyclerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mRecyclerView = itemView.findViewById(R.id.rv_slider);
        }
    }
}