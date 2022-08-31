package com.android.platforming.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.platforming.adapter.PostViewAdapter;
import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.Post;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

public class MyPostFragment extends Fragment {

    PostViewAdapter postViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypost, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_mypost);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        postViewAdapter = new PostViewAdapter(Post.getPosts());
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
        recyclerView.setAdapter();

        return view;
    }

    private void refreshPostList(){
        FirestoreManager firestoreManager = new FirestoreManager();
        firestoreManager.
    }
}
