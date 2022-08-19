package com.android.platforming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.platforming.clazz.Post;
import com.example.platforming.R;

import java.util.ArrayList;

public class PostViewAdapter extends RecyclerView.Adapter<PostViewAdapter.ViewHolder> {

    private ArrayList<Post> mData = null ;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView detail;
        TextView info;
        TextView thumb_up;
        TextView comment;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            title = itemView.findViewById(R.id.tv_recyclerview_post_title);
            detail = itemView.findViewById(R.id.tv_recyclerview_post_detail);
            info = itemView.findViewById(R.id.tv_recyclerview_post_info);
            thumb_up = itemView.findViewById(R.id.tv_recyclerview_post_thumb_up);
            comment = itemView.findViewById(R.id.tv_recyclerview_post_comment);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    PostViewAdapter(ArrayList<Post> list) {
        mData = list ;
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
        holder.title.setText(mData.get(position).getTitle());
        holder.detail.setText(mData.get(position).getDetail());
        holder.info.setText(mData.get(position).getTitle());
        holder.thumb_up.setText(mData.get(position).getThumb_up());
        holder.comment.setText(mData.get(position).getTitle());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}
