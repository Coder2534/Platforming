package com.android.platforming.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.platforming.adapter.CommentViewAdapter;
import com.android.platforming.adapter.PostViewAdapter;
import com.android.platforming.clazz.Comment;
import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.Post;
import com.android.platforming.clazz.User;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class NoticeBoardDetailFragment extends Fragment {

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
    Post post;
    String workName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noticeboard_detail, container, false);
        Bundle args = getArguments();
        post = Post.getPosts().get(args.getInt("position", 0));
        workName = args.getString("workName", null);

        ImageView profile = view.findViewById(R.id.iv_noticeboard_detail_profile);
        profile.setImageResource(User.getProfiles().get(post.getProfileIndex()));
        TextView nickname = view.findViewById(R.id.tv_noticeboard_detail_nickname);
        nickname.setText(post.getNickname());
        TextView date = view.findViewById(R.id.tv_noticeboard_detail_date);
        date.setText(dateFormat.format(post.getDate()));
        TextView title = view.findViewById(R.id.tv_noticeboard_detail_title);
        title.setText(post.getTitle());
        TextView detail = view.findViewById(R.id.tv_noticeboard_detail_detail);
        detail.setText(post.getDetail());
        TextView thumb_up = view.findViewById(R.id.tv_noticeboard_detail_thumb_up);
        thumb_up.setText(Integer.toString(post.getThumb_up()));
        TextView comment_count = view.findViewById(R.id.tv_noticeboard_detail_comment);
        comment_count.setText(Integer.toString(post.getComments().size()));

        RecyclerView recyclerView = view.findViewById(R.id.rv_noticeboard_detail_coment);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        CommentViewAdapter commentViewAdapter = new CommentViewAdapter(post.getComments());
        recyclerView.setAdapter(commentViewAdapter);

        EditText comment = view.findViewById(R.id.et_noticeboard_detail_comment);

        FirestoreManager firestoreManager = new FirestoreManager();
        firestoreManager.readCommentData(workName, post, new ListenerInterface() {
            @Override
            public void onSuccess() {
                commentViewAdapter.notifyDataSetChanged();
                comment.setText(Integer.toString(post.getComments().size()));
            }
        });

        ImageButton send = view.findViewById(R.id.btn_noticeboard_detail_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                data.put("uid", User.getUser().getUid());
                data.put("profileIndex", User.getUser().getProfileIndex());
                data.put("nickname", User.getUser().getNickName());
                data.put("date", System.currentTimeMillis());
                data.put("comment", comment.getText().toString());

                FirestoreManager firestoreManager = new FirestoreManager();
                firestoreManager.writeCommentData(workName, post.getId(), data, new ListenerInterface() {
                    @Override
                    public void onSuccess() {
                        post.getComments().add(new Comment(data));
                        commentViewAdapter.notifyDataSetChanged();
                        comment.setText(Integer.toString(post.getComments().size()));
                    }
                });
            }
        });

        return view;
    }
}
