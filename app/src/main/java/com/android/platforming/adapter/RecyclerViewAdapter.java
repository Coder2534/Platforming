package com.android.platforming.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.platforming.clazz.ChatModel;
import com.android.platforming.clazz.User;
import com.example.platforming.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    List<ChatModel.Comment> comments;

        public RecyclerViewAdapter(){
        comments = new ArrayList<>();

        getDestUid();
    }

    //상대방 uid 하나(single) 읽기
    private void getDestUid()
    {
        FirebaseDatabase firebaseDatabase = null;
        firebaseDatabase.getReference().child("users").child(destUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                destUser = snapshot.getValue(User.class);

                //채팅 내용 읽어들임
                getMessageList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //채팅 내용 읽어들임
    private void getMessageList()
    {
        FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comments.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    comments.add(dataSnapshot.getValue(ChatModel.Comment.class));
                }
                notifyDataSetChanged();

                recyclerView.scrollToPosition(comments.size()-1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messagebox,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        ViewHolder viewHolder = ((ViewHolder)holder);

        if(comments.get(position).uid.equals(myuid)) //나의 uid 이면
        {
            //나의 말풍선 오른쪽으로
            viewHolder.textViewMsg.setText(comments.get(position).message);
            viewHolder.textViewMsg.setBackgroundResource(R.drawable.rightbubble);
            viewHolder.linearLayoutDest.setVisibility(View.INVISIBLE);        //상대방 레이아웃
            viewHolder.linearLayoutRoot.setGravity(Gravity.RIGHT);
            viewHolder.linearLayoutTime.setGravity(Gravity.RIGHT);
        }else{
            //상대방 말풍선 왼쪽
            Glide.with(holder.itemView.getContext())
                    .load(destUser.profileImgUrl)
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.imageViewProfile);
            viewHolder.textViewName.setText(destUser.name);
            viewHolder.linearLayoutDest.setVisibility(View.VISIBLE);
            viewHolder.textViewMsg.setBackgroundResource(R.drawable.leftbubble);
            viewHolder.textViewMsg.setText(comments.get(position).message);
            viewHolder.linearLayoutRoot.setGravity(Gravity.LEFT);
            viewHolder.linearLayoutTime.setGravity(Gravity.LEFT);
        }
        viewHolder.textViewTimeStamp.setText(getDateTime(position));

    }

    public String getDateTime(int position)
    {
        long unixTime=(long) comments.get(position).timestamp;
        Date date = new Date(unixTime);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String time = simpleDateFormat.format(date);
        return time;
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textViewMsg;   //메시지 내용
        public TextView textViewName;
        public TextView textViewTimeStamp;
        public ImageView imageViewProfile;
        public LinearLayout linearLayoutDest;
        public LinearLayout linearLayoutRoot;
        public LinearLayout linearLayoutTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewMsg = (TextView)itemView.findViewById(R.id.item_messagebox_textview_msg);
            textViewName = (TextView)itemView.findViewById(R.id.item_messagebox_TextView_name);
            textViewTimeStamp = (TextView)itemView.findViewById(R.id.item_messagebox_textview_timestamp);
            imageViewProfile = (ImageView)itemView.findViewById(R.id.item_messagebox_ImageView_profile);
            linearLayoutDest = (LinearLayout)itemView.findViewById(R.id.item_messagebox_LinearLayout);
            linearLayoutRoot = (LinearLayout)itemView.findViewById(R.id.item_messagebox_root);
            linearLayoutTime = (LinearLayout)itemView.findViewById(R.id.item_messagebox_layout_timestamp);
        }
    }
}

