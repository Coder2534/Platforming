package com.android.platforming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.platforming.R;

import java.util.ArrayList;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder> {
    private ArrayList<Integer> sliderImage;

    public ImageSliderAdapter(ArrayList<Integer> sliderImage) {
        this.sliderImage = sliderImage;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.item_imageview_slider, parent, false) ;

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindSliderImage(sliderImage.get(position));
    }

    @Override
    public int getItemCount() {
        return sliderImage.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_slider);
        }

        public void bindSliderImage(int drawable) {
            mImageView.setImageResource(drawable);
        }
    }
}