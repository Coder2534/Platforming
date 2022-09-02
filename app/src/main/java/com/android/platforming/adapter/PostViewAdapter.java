package com.android.platforming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.platforming.clazz.Post;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

import java.util.ArrayList;

public class PostViewAdapter extends RecyclerView.Adapter<PostViewAdapter.ViewHolder> {

    private ArrayList<Post> mData;
    private ArrayList<String> mTypes = null;

    ListenerInterface listenerInterface;

    public void setListenerInterface(ListenerInterface listenerInterface) {
        this.listenerInterface = listenerInterface;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView detail;
        TextView info;
        TextView like;
        TextView comment;
        TextView type;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            title = itemView.findViewById(R.id.tv_recyclerview_post_title);
            detail = itemView.findViewById(R.id.tv_recyclerview_post_detail);
            info = itemView.findViewById(R.id.tv_recyclerview_post_info);
            like = itemView.findViewById(R.id.tv_recyclerview_post_like);
            comment = itemView.findViewById(R.id.tv_recyclerview_post_comment);
            type = itemView.findViewById(R.id.tv_recyclerview_post_type);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        listenerInterface.onSuccess(pos);
                    }
                }
            });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public PostViewAdapter(ArrayList<Post> data) {
        mData = data;
    }

    public PostViewAdapter(ArrayList<Post> list, ArrayList<String> types) {
        mData = list ;
        mTypes = types;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public PostViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.item_recyclerview_post, parent, false) ;
        PostViewAdapter.ViewHolder vh = new PostViewAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(PostViewAdapter.ViewHolder holder, int position) {
        Post post = mData.get(position);
        holder.title.setText(post.getTitle());
        holder.detail.setText(post.getDetail());
        holder.info.setText(formatTimeString(post.getDate()) + " | " + post.getNickname());
        holder.like.setText(String.valueOf(post.getLikes().size()));
        holder.comment.setText(String.valueOf(post.getCommentSize()));
        if(mTypes != null){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.like.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_BASELINE, R.id.tv_recyclerview_post_type);
            holder.like.setLayoutParams(params);
            holder.type.setText(mTypes.get(post.getType()));
            holder.type.setVisibility(View.VISIBLE);
        }
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    public static String formatTimeString(long regTime) {
        long curTime = System.currentTimeMillis();
        long diffTime = (curTime - regTime) / 1000;
        String msg = null;
        if (diffTime < 60) {
            msg = "방금 전";
        } else if ((diffTime /= 60) < 60) {
            msg = diffTime + "분 전";
        } else if ((diffTime /= 60) < 24) {
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= 24) < 30) {
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= 30) < 12) {
            msg = (diffTime) + "달 전";
        } else {
            msg = (diffTime) + "년 전";
        }
        return msg;
    }
}