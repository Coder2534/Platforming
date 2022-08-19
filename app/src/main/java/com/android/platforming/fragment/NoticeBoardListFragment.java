package com.android.platforming.fragment;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    String workName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noticeboard_list, container, false);
        Bundle args = getArguments();
        workName = args.getString("workName", null);

        RecyclerView recyclerView = view.findViewById(R.id.rv_noticeboard_list_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        PostViewAdapter postViewAdapter = new PostViewAdapter(Post.getPosts());
        postViewAdapter.setListenerInterface(new ListenerInterface() {
            @Override
            public void onSuccess(int position) {
                NoticeBoardDetailFragment fragment = new NoticeBoardDetailFragment();
                Bundle args = new Bundle();
                args.putInt("position", position);
                args.putString("workName", workName);
                fragment.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cl_noticeboard, fragment).addToBackStack(null).commit();
            }
        });

        recyclerView.setAdapter(postViewAdapter);

        FirestoreManager firestoreManager = new FirestoreManager();
        firestoreManager.readPostData(workName, new ListenerInterface() {
            @Override
            public void onSuccess() {
                postViewAdapter.notifyDataSetChanged();

                Button button = view.findViewById(R.id.btn_noticeboard_list_write);
                button.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), NoticeBoardRegisterActivity.class);
                    intent.putExtra("workName", workName);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.start_activity_noticeboard, R.anim.none);
                });
            }
        });

        return view;
    }
}
