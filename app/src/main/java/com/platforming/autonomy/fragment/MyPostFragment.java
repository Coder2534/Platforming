package com.platforming.autonomy.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.platforming.autonomy.activity.BulletinBoardActivity;
import com.platforming.autonomy.activity.MainActivity;
import com.platforming.autonomy.adapter.PostViewAdapter;
import com.platforming.autonomy.clazz.FirestoreManager;
import com.platforming.autonomy.clazz.BulletinBoard;
import com.platforming.autonomy.interfaze.ListenerInterface;
import com.android.autonomy.R;

public class MyPostFragment extends Fragment {

    PostViewAdapter postViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypost, container, false);

        ((MainActivity)getActivity()).setTitle("내 게시물");
        BulletinBoard bulletinBoard = BulletinBoard.Manager.bulletinBoards.get("_MY");

        RecyclerView recyclerView = view.findViewById(R.id.rv_mypost);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        postViewAdapter = new PostViewAdapter(bulletinBoard);
        postViewAdapter.setListenerInterface(new ListenerInterface() {
            @Override
            public void onSuccess(int position) {
                BulletinBoard.Post post = bulletinBoard.getPosts().get(position);
                Activity activity = getActivity();
                Intent intent = new Intent(activity, BulletinBoardActivity.class);
                intent.putExtra("bulletinId", "_MY");
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
                int start = bulletinBoard.getPosts().size();
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    FirestoreManager firestoreManager = new FirestoreManager();
                    if(start == 0){
                        bulletinBoard.getPosts().clear();
                        postViewAdapter.notifyDataSetChanged();

                        firestoreManager.readMyPostData(new ListenerInterface() {
                            @Override
                            public void onSuccess() {
                                postViewAdapter.notifyItemInserted(bulletinBoard.getPosts().size() - 1);
                            }

                            @Override
                            public void onSuccess(int msg) {
                                postViewAdapter.notifyItemChanged(msg);
                            }
                        });
                    }
                    else{
                        firestoreManager.readExtraMyPostData(new ListenerInterface() {
                            @Override
                            public void onSuccess() {
                                postViewAdapter.notifyItemInserted(bulletinBoard.getPosts().size() - 1);
                            }

                            @Override
                            public void onSuccess(int msg) {
                                postViewAdapter.notifyItemChanged(msg);
                            }
                        });
                    }
                }
            }
        });

        bulletinBoard.getPosts().clear();
        postViewAdapter.notifyDataSetChanged();
        FirestoreManager firestoreManager = new FirestoreManager();
        firestoreManager.readMyPostData(new ListenerInterface() {
            @Override
            public void onSuccess() {
                postViewAdapter.notifyItemInserted(bulletinBoard.getPosts().size() - 1);
            }

            @Override
            public void onSuccess(int msg) {
                postViewAdapter.notifyItemChanged(msg);
            }
        });

        return view;
    }
}
