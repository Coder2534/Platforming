package com.android.platforming.fragment;

import static com.android.platforming.clazz.Post.POST;
import static com.android.platforming.clazz.Post.POST_MY;
import static com.android.platforming.clazz.Post.POST_RECENT;
import static com.android.platforming.clazz.User.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.platforming.adapter.PostCommentViewAdapter;
import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.Post;
import com.android.platforming.clazz.User;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BulletinBoardPostFragment extends Fragment {

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
    Post post;

    ListenerInterface listenerInterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bulletinboard_post, container, false);
        Bundle args = getArguments();

        switch (args.getInt("post", 0)){
            case POST:
                post = Post.getPosts().get(args.getInt("position", 0));
                break;

            case POST_RECENT:
                post = Post.getRecentPosts().get(args.getInt("position", 0));
                break;

            case POST_MY:
                post = Post.getMyPosts().get(args.getInt("position", 0));
                break;
        }


        ImageView profile = view.findViewById(R.id.iv_bulletinboard_detail_profile);
        profile.setImageResource(User.getProfiles().get(post.getProfileIndex()));

        TextView nickname = view.findViewById(R.id.tv_bulletinboard_detail_nickname);
        nickname.setText(post.getNickname());

        TextView date = view.findViewById(R.id.tv_bulletinboard_detail_date);
        date.setText(dateFormat.format(post.getDate()));

        TextView title = view.findViewById(R.id.tv_bulletinboard_detail_title);
        title.setText(post.getTitle());

        TextView detail = view.findViewById(R.id.tv_bulletinboard_detail_detail);
        detail.setText(post.getDetail());

        TextView like = view.findViewById(R.id.tv_bulletinboard_detail_like);
        ArrayList<String> likes = post.getLikes();
        String uid = user.getUid();
        like.setText(String.valueOf(likes.size()));
        if(likes.contains(uid))
            like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_thumb_up_alt_24, 0, 0, 0);
        else
            like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_thumb_up_off_alt_24, 0, 0, 0);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like.setClickable(false);
                ListenerInterface listenerInterface;
                if (likes.contains(uid)) {
                    likes.remove(uid);
                    listenerInterface = new ListenerInterface() {
                        @Override
                        public void onSuccess() {
                            like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_thumb_up_off_alt_24, 0, 0, 0);
                            like.setText(String.valueOf(likes.size()));
                            like.setClickable(true);
                        }

                        @Override
                        public void onFail() {
                            likes.add(uid);
                            like.setClickable(true);
                        }
                    };
                } else {
                    likes.add(uid);
                    listenerInterface = new ListenerInterface() {
                        @Override
                        public void onSuccess() {
                            like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_thumb_up_alt_24, 0, 0, 0);
                            like.setText(String.valueOf(likes.size()));
                            like.setClickable(true);
                        }

                        @Override
                        public void onFail() {
                            likes.remove(uid);
                            like.setClickable(true);
                        }
                    };
                }
                Map<String, Object> data = new HashMap<String, Object>(){{
                    put("likes", likes);
                }};
                FirestoreManager firestoreManager = new FirestoreManager();
                firestoreManager.updatePostData(post.getId(), data, listenerInterface);
            }
        });

        FirestoreManager firestoreManager = new FirestoreManager();

        TextView comment_count = view.findViewById(R.id.tv_bulletinboard_detail_comment);
        firestoreManager.readCommentSize(post, new ListenerInterface() {
            @Override
            public void onSuccess() {
                comment_count.setText(String.valueOf(post.getCommentSize()));
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.rv_bulletinboard_detail_coment);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        PostCommentViewAdapter commentViewAdapter = new PostCommentViewAdapter(getActivity(), post.getId(), post.getComments());
        listenerInterface = new ListenerInterface() {
            @Override
            public void onSuccess() {
                //refresh commentList
                commentViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSuccess(int pos) {
                //refresh commentList
                commentViewAdapter.removeData(pos);
                commentViewAdapter.notifyItemRemoved(pos);
                comment_count.setText(String.valueOf(post.getComments().size()));
            }
        };
        commentViewAdapter.setListenerInterface(listenerInterface);
        recyclerView.setAdapter(commentViewAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int start = post.getComments().size();
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    FirestoreManager firestoreManager = new FirestoreManager();
                    if(start == 0){
                        post.getComments().clear();
                        commentViewAdapter.notifyDataSetChanged();

                        firestoreManager.readCommentData(post, new ListenerInterface() {
                            @Override
                            public void onSuccess() {
                                commentViewAdapter.notifyItemInserted(post.getComments().size() - 1);
                            }

                            @Override
                            public void onSuccess(int msg) {
                                commentViewAdapter.notifyItemChanged(msg);
                            }
                        });
                    }
                    else{
                        firestoreManager.readExtraCommentData(post, new ListenerInterface() {
                            @Override
                            public void onSuccess() {
                                commentViewAdapter.notifyItemInserted(post.getComments().size() - 1);
                            }

                            @Override
                            public void onSuccess(int msg) {
                                commentViewAdapter.notifyItemChanged(msg);
                            }
                        });
                    }
                }
            }
        });


        post.getComments().clear();
        commentViewAdapter.notifyDataSetChanged();
        firestoreManager.readCommentData(post, listenerInterface);

        EditText comment = view.findViewById(R.id.et_bulletinboard_detail_comment);
        comment.addTextChangedListener(new TextWatcher(){
            String previousString = "";
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
                previousString= s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (comment.getLineCount() > 2){
                    comment.setText(previousString);
                    comment.setSelection(editText_detail.length());
                }
            }
        });

        ImageButton write = view.findViewById(R.id.btn_bulletinboard_detail_write);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write.setClickable(false);
                Map<String, Object> data = new HashMap<>();
                data.put("uid", user.getUid());
                data.put("profileIndex", user.getProfileIndex());
                data.put("nickname", user.getNickname());
                data.put("date", System.currentTimeMillis());
                data.put("comment", comment.getText().toString());

                FirestoreManager firestoreManager = new FirestoreManager();
                firestoreManager.writeCommentData(post.getId(), data, new ListenerInterface() {
                    @Override
                    public void onSuccess() {
                        long count = user.getDailyTasks().get(3);
                        if(count < 2){
                            List<Long> dailyTasks = new LinkedList<>(user.getDailyTasks());
                            dailyTasks.set(3, count + 1L);
                            firestoreManager.updateUserData(new HashMap<String, Object>() {{
                                put("point_receipt", user.getPoint_receipt() + 5);
                                put("dailyTasks", dailyTasks);
                            }}, new ListenerInterface() {
                                @Override
                                public void onSuccess() {
                                    user.addPoint_receipt(5);
                                    user.getDailyTasks().set(3,  count + 1L);
                                }
                            });
                        }

                        firestoreManager.readCommentSize(post, new ListenerInterface() {
                            @Override
                            public void onSuccess() {
                                comment_count.setText(String.valueOf(post.getCommentSize()));
                            }
                        });

                        firestoreManager.readCommentData(post, new ListenerInterface() {
                            @Override
                            public void onSuccess() {
                                commentViewAdapter.notifyItemInserted(post.getComments().size() - 1);
                            }

                            @Override
                            public void onSuccess(int msg) {
                                commentViewAdapter.notifyItemChanged(msg);
                            }
                        });

                        comment.setText("");
                        write.setClickable(true);
                    }
                });
            }
        });

        return view;
    }
}