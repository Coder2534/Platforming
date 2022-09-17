package com.android.platforming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.platforming.clazz.TableItem;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

import java.util.ArrayList;

public class RecyclerViewSliderAdapter extends RecyclerView.Adapter<RecyclerViewSliderAdapter.MyViewHolder> {
    private ArrayList<ArrayList<TableItem>> schedules;
    private ArrayList<ScheduleEditAdapter> scheduleEditAdapters = new ArrayList<>();

    public RecyclerViewSliderAdapter(ArrayList<ArrayList<TableItem>> schedules) {
        this.schedules = schedules;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.item_recyclerview_slider, parent, false) ;

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ScheduleEditAdapter scheduleEditAdapter = new ScheduleEditAdapter(schedules.get(position));
        scheduleEditAdapter.setHasStableIds(true);
        scheduleEditAdapters.add(scheduleEditAdapter);
        holder.mRecyclerView.setAdapter(scheduleEditAdapter);
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
            mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }

    public void addSchedule(int position){
        if(schedules.get(position).size() < 8){
            schedules.get(position).add(new TableItem());
            scheduleEditAdapters.get(position).notifyItemInserted(schedules.get(position).size() - 1);
        }
    }

    public void saveSchedules(){
        for(ScheduleEditAdapter scheduleEditAdapter : scheduleEditAdapters){
            scheduleEditAdapter.saveTableItems();
        }
    }
}