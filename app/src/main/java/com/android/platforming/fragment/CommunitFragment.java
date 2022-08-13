package com.android.platforming.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.platforming.adapter.RecyclerViewAdapter;
import com.android.platforming.clazz.ChatModel;
import com.android.platforming.clazz.ChatUser;
import com.android.platforming.clazz.User;
import com.example.platforming.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

public class CommunitFragment extends Fragment {
    private String chatRoomUid;
    private String myuid;       //나의 id
    private String destUid;     //상대방 uid

    private RecyclerView rv_chat;
    private EditText et_chat;
    private Button btn_send;

    private FirebaseDatabase firebaseDatabase;

    private User destUser;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy.MM.dd HH:mm");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_communit, container, false);
        btn_send=(Button)view.findViewById(R.id.btn_send);
        et_chat = (EditText)view.findViewById(R.id.et_chat);
        rv_chat = (RecyclerView)view.findViewById(R.id.rv_chat);
        init();

        return view;
    }
    private void init()
    {
        myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        destUid = String.valueOf(firebaseDatabase.getReference().child("destUid"));//채팅 상대
        Log.d("check",destUid);



        firebaseDatabase = FirebaseDatabase.getInstance();

        if(et_chat.getText().toString() == null) btn_send.setEnabled(false);
        else btn_send.setEnabled(true);

        checkChatRoom();
    }
    private void sendMsg()
    {
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatModel chatModel = new ChatModel();
                chatModel.users.put(myuid,true);
                chatModel.users.put(destUid,true);

                //push() 데이터가 쌓이기 위해 채팅방 key가 생성
                if(chatRoomUid == null){
                    btn_send.setEnabled(false);
                    firebaseDatabase.getReference().child("chatrooms").push().setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            checkChatRoom();
                        }
                    });
                }else{
                    sendMsgToDataBase();
                }
            }
        });
    }
    private void sendMsgToDataBase()
    {
        if(!et_chat.getText().toString().equals(""))
        {
            ChatModel.Comment comment = new ChatModel.Comment();
            comment.uid = myuid;
            comment.message = et_chat.getText().toString();
            comment.timestamp = ServerValue.TIMESTAMP;
            firebaseDatabase.getReference().child("chatrooms").child(chatRoomUid).child("comments").push().setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    et_chat.setText("");
                }
            });
        }
    }
    private void checkChatRoom()
    {
        //자신 key == true 일때 chatModel 가져온다.
        /* chatModel
        public Map<String,Boolean> users = new HashMap<>(); //채팅방 유저
        public Map<String, ChatModel.Comment> comments = new HashMap<>(); //채팅 메시지
        */
        firebaseDatabase.getReference().child("chatrooms").orderByChild("users/"+myuid).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) //나, 상대방 id 가져온다.
                {
                    ChatModel chatModel = dataSnapshot.getValue(ChatModel.class);
                    if(chatModel.users.containsKey(destUid)){           //상대방 id 포함돼 있을때 채팅방 key 가져옴
                        chatRoomUid = dataSnapshot.getKey();
                        btn_send.setEnabled(true);

                        //동기화
                        rv_chat.setLayoutManager(new LinearLayoutManager(getContext()));
                        rv_chat.setAdapter(new RecyclerViewAdapter());

                        //메시지 보내기
                        sendMsgToDataBase();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
