package com.platforming.autonomy.activity;

import static com.platforming.autonomy.clazz.User.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.autonomy.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.platforming.autonomy.clazz.ChatRoom;

public class ChatRoomActivity extends AppCompatActivity {

    ChatRoom chatRoom = new ChatRoom();
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    private void sendMsg(Button button)
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatRoom = new ChatRoom();
                chatRoom.getUsers().put(user.getUid(),true);

                //push() 데이터가 쌓이기 위해 채팅방 key가 생성
                if(chatRoom.getId() == null){
                    Toast.makeText(MessageActivity.this, "채팅방 생성", Toast.LENGTH_SHORT).show();
                    button.setEnabled(false);
                    firebaseDatabase.getReference().child("chatrooms").push().setValue(chatRoom).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void checkChatRoom()
    {
        //자신 key == true 일때 chatModel 가져온다.
        /* chatModel
        public Map<String,Boolean> users = new HashMap<>(); //채팅방 유저
        public Map<String, ChatModel.Comment> comments = new HashMap<>(); //채팅 메시지
        */
        FirebaseDatabase.getInstance().getReference().child("chatrooms").orderByChild("users/"+user.getUid()).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) //나, 상대방 id 가져온다.
                {
                    ChatRoom chatRoom = dataSnapshot.getValue(ChatRoom.class);
                    if(chatRoom.users.containsKey(destUid)){           //상대방 id 포함돼 있을때 채팅방 key 가져옴
                        chatRoom.setId(dataSnapshot.getKey());
                        button.setEnabled(true);

                        //동기화
                        recyclerView.setLayoutManager(new LinearLayoutManager(ChatRoomActivity.this));
                        recyclerView.setAdapter(new RecyclerViewAdapter());

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

    private void sendMsgToDataBase()
    {
        if(!editText.getText().toString().equals(""))
        {
            ChatRoom.Chat chat = new ChatRoom.Chat();
            comment.uid = user.getUid();
            comment.message = editText.getText().toString();
            comment.timestamp = ServerValue.TIMESTAMP;
            firebaseDatabase.getReference().child("chatrooms").child(chatRoomUid).child("comments").push().setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    editText.setText("");
                }
            });
        }
    }

    private void getMessageList()
    {
        FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comments.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    comments.add(dataSnapshot.getValue(ChatModel.Comment.class));
                }
                notifyDataSetChanged();

                recyclerView.scrollToPosition(comments.size()-1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}