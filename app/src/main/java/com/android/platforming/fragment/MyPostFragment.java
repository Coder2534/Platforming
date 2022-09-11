package com.android.platforming.fragment;

import static com.android.platforming.clazz.Post.POST_MY;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.platforming.activity.BulletinBoardActivity;
import com.android.platforming.activity.MainActivity;
import com.android.platforming.adapter.PostViewAdapter;
import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.Post;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

import java.util.ArrayList;

public class MyPostFragment extends Fragment {

    PostViewAdapter postViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypost, container, false);

        ((MainActivity)getActivity()).setTitle("나의 게시물");

        RecyclerView recyclerView = view.findViewById(R.id.rv_mypost);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        postViewAdapter = new PostViewAdapter(Post.getMyPosts(), Post.getTypes());
        postViewAdapter.setListenerInterface(new ListenerInterface() {
            @Override
            public void onSuccess(int position) {
                Post post = Post.getMyPosts().get(position);
                Activity activity = getActivity();
                Intent intent = new Intent(activity, BulletinBoardActivity.class);
                intent.putExtra("post", POST_MY);
                intent.putExtra("type", post.getType());
                intent.putExtra("id", post.getId());
                startActivity(intent);
                activity.overridePendingTransition(R.anim.start_activity_noticeboard, R.anim.none);
            }
        });
        recyclerView.setAdapter(postViewAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int start = Post.getMyPosts().size();
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    FirestoreManager firestoreManager = new FirestoreManager();
                    if(start == 0){
                        firestoreManager.readMyPostData(new ListenerInterface() {
                            @Override
                            public void onSuccess() {
                                postViewAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    else{
                        firestoreManager.readExtraMyPostData(new ListenerInterface() {
                            @Override
                            public void onSuccess() {
                                postViewAdapter.notifyItemRangeInserted(start, Post.getMyPosts().size() - 1);
                            }
                        });
                    }
                }
            }
        });

        FirestoreManager firestoreManager = new FirestoreManager();
        firestoreManager.readMyPostData(new ListenerInterface() {
            @Override
            public void onSuccess() {
                postViewAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}
