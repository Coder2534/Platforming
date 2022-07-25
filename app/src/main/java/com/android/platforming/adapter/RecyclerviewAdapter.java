package com.android.platforming.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.platforming.clazz.Setting;
import com.example.platforming.R;

import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {


    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView description;

        ViewHolder(View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.tv_recyclerview_setting_icon);
            title = itemView.findViewById(R.id.tv_recyclerview_setting_title);
            description = itemView.findViewById(R.id.tv_recyclerview_setting_description);
        }
    }

    public RecyclerviewAdapter(ArrayList<Setting> arrayList) {
        mData = arrayList;
    }

    private ArrayList<Setting> mData = null;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_recyclerview_setting, parent, false);

        ViewHolder viewholder = new ViewHolder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.icon.setImageResource(mData.get(position).getIcon());
        holder.title.setText(mData.get(position).getTitle());
        holder.description.setText(mData.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
