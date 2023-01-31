package com.platforming.autonomy.fragment;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.platforming.autonomy.activity.BulletinBoardRegisterActivity;
import com.platforming.autonomy.adapter.PostViewAdapter;
import com.platforming.autonomy.clazz.FirestoreManager;
import com.platforming.autonomy.clazz.BulletinBoard;
import com.platforming.autonomy.interfaze.ListenerInterface;
import com.android.autonomy.R;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.IntStream;

public class BulletinBoardListFragment extends Fragment {

    private ActivityResultLauncher<Intent> resultLauncher;

    BulletinBoard bulletinBoard;
    PostViewAdapter postViewAdapter;
    Button write;
    ListenerInterface listenerInterface = new ListenerInterface() {
        @Override
        public void onSuccess() {
            postViewAdapter.notifyItemInserted(bulletinBoard.getPosts().size() - 1);
        }

        @Override
        public void onSuccess(int msg) {
            postViewAdapter.notifyItemChanged(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bulletinboard_list, container, false);
        Bundle args = getArguments();

        String bulletinId = args.getString("bulletinId");
        String postId = args.getString("postId");

        bulletinBoard = BulletinBoard.Manager.bulletinBoards.get(bulletinId);
        RecyclerView recyclerView = view.findViewById(R.id.rv_bulletinboard_list_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        postViewAdapter = new PostViewAdapter(bulletinBoard);
        postViewAdapter.setListenerInterface(new ListenerInterface() {
            @Override
            public void onSuccess(int position) {
                showDetail(bulletinBoard.getId(), position);
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

                        firestoreManager.readPostData(bulletinBoard.getId(), new ListenerInterface() {
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
                        firestoreManager.readExtraPostData(bulletinBoard.getId(), new ListenerInterface() {
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

        write = view.findViewById(R.id.btn_bulletinboard_list_write);
        write.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BulletinBoardRegisterActivity.class);
            intent.putExtra("bulletinId", bulletinId);
            resultLauncher.launch(intent);
            getActivity().overridePendingTransition(R.anim.start_activity_noticeboard, R.anim.none);
        });

        TextView title = getActivity().findViewById(R.id.tv_noticeboard_title);
        if (Objects.equals(bulletinId, "_RECENT") || Objects.equals(bulletinId, "_MY")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && postId != null) {
                int position = IntStream.range(0, bulletinBoard.getPosts().size())
                        .filter(i -> bulletinBoard.getPosts().get(i).getId().equals(postId))
                        .findFirst()
                        .orElse(-1);
                showDetail(bulletinBoard.getId(), position);
                args.putString("postId", null);

                resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    if(result.getResultCode() == RESULT_OK){
                        refreshPostList(bulletinBoard.getPosts().get(position).getBulletinId());
                    }
                });
                title.setText(bulletinBoard.getPosts().get(position).getBulletinId());
            }
        }
        else{
            resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if(result.getResultCode() == RESULT_OK){
                    refreshPostList(bulletinBoard.getId());
                }
            });
            title.setText(bulletinId);
            refreshPostList(bulletinId);
        }

        return view;
    }

    private void showDetail(String bulletinId, int position){
        BulletinBoardPostFragment fragment = new BulletinBoardPostFragment();
        Bundle args = new Bundle();
        args.putString("bulletinId", bulletinId);
        args.putInt("position", position);
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cl_noticeboard, fragment).addToBackStack(null).commit();
    }

    private void refreshPostList(String bulletinId){
        bulletinBoard.getPosts().clear();
        postViewAdapter.notifyDataSetChanged();
        FirestoreManager firestoreManager = new FirestoreManager();
        firestoreManager.readPostData(bulletinId, listenerInterface);
    }
}