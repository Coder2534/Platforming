package com.android.platforming.fragment;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.platforming.activity.NoticeBoardRegisterActivity;
import com.android.platforming.adapter.PostViewAdapter;
import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.Post;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

public class NoticeBoardListFragment extends Fragment {

    private ActivityResultLauncher<Intent> resultLauncher;

    int type;
    PostViewAdapter postViewAdapter;
    Button write;
    ListenerInterface listenerInterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noticeboard_list, container, false);
        Bundle args = getArguments();
        type = args.getInt("type", 0);

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == RESULT_OK){
                listenerInterface = new ListenerInterface() {
                    @Override
                    public void onSuccess() {
                        postViewAdapter.notifyDataSetChanged();
                    }
                };
                refreshPostList();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.rv_noticeboard_list_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        postViewAdapter = new PostViewAdapter(Post.getPosts());
        postViewAdapter.setListenerInterface(new ListenerInterface() {
            @Override
            public void onSuccess(int position) {
                NoticeBoardDetailFragment fragment = new NoticeBoardDetailFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cl_noticeboard, fragment).addToBackStack(null).commit();
            }
        });

        recyclerView.setAdapter(postViewAdapter);

        write = view.findViewById(R.id.btn_noticeboard_list_write);
        listenerInterface = new ListenerInterface() {
            @Override
            public void onSuccess() {
                postViewAdapter.notifyDataSetChanged();
                write.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), NoticeBoardRegisterActivity.class);
                    intent.putExtra("type", type);
                    resultLauncher.launch(intent);
                    getActivity().overridePendingTransition(R.anim.start_activity_noticeboard, R.anim.none);
                });
            }
        };
        refreshPostList();

        return view;
    }

    private void refreshPostList(){
        FirestoreManager firestoreManager = new FirestoreManager();
        firestoreManager.readPostData(type, listenerInterface);
    }
}