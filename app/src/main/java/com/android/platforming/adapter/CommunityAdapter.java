package com.android.platforming.adapter;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.platforming.clazz.ChatModel;
import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.platforming.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder> {
    List<ChatModel.Comment> comments;

        public CommunityAdapter(){
        comments = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView item_messagebox_tv_msg;
        public TextView item_messagebox_tv_nickname;
        public TextView item_messagebox_tv_checktime;
        public ImageView item_messagebox_iv_profile;
        public LinearLayout item_messagebox_LinearLayout;
        public LinearLayout item_messagebox_root;
        public LinearLayout item_messagebox_layout_checktime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_messagebox_tv_msg = (TextView)itemView.findViewById(R.id.item_messagebox_tv_msg);
            item_messagebox_tv_nickname = (TextView)itemView.findViewById(R.id.item_messagebox_tv_nickname);
            item_messagebox_tv_checktime = (TextView)itemView.findViewById(R.id.item_messagebox_tv_checktime);
            item_messagebox_iv_profile = (ImageView)itemView.findViewById(R.id.item_messagebox_iv_profile);
            item_messagebox_LinearLayout = (LinearLayout)itemView.findViewById(R.id.item_messagebox_LinearLayout);
            item_messagebox_root = (LinearLayout)itemView.findViewById(R.id.item_messagebox_root);
            item_messagebox_layout_checktime = (LinearLayout)itemView.findViewById(R.id.item_messagebox_layout_checktime);
        }
    }
}

