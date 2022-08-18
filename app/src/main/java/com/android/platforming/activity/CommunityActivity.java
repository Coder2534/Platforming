package com.android.platforming.activity;

import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;
import com.android.platforming.adapter.CommunityAdapter;

import com.android.platforming.clazz.ChatModel;
import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.User;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommunityActivity extends AppCompatActivity {
    private String chatRoomUid;
    private String myuid;       //나의 id
    private String destUid;     //상대방 uid

    private RecyclerView rv_chat;
    private EditText et_chat;
    private Button btn_send;

    FirestoreManager firestoreManager = new FirestoreManager();
    private FirebaseFirestore firestore;


    private User destUser;
    public CommunityAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        getView();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMsgToDataBase();
            }
        });


    }
    private void getView(){
        rv_chat = findViewById(R.id.rv_chat);
        et_chat = findViewById(R.id.et_chat);
        btn_send = findViewById(R.id.btn_send);
    }
    private String getNowTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy.MM.d HH시 mm분");
        return DateFormat.format(date);
    }
    private void sendMsgToDataBase()
    {
        if(et_chat.getText().toString() != null ) {
            Map<String,Object> data = new HashMap<>();

            data.put("msg",et_chat.getText());
            data.put("date",getNowTime());
            data.put("uid",User.getUser().getUid());
            data.put("nickname",User.getUser().getNickName());
            data.put("count",);
            data.put("profileIndex",User.getProfiles());
            firestoreManager.writeMagData("allchat", data, new ListenerInterface() {
                @Override
                public void onSuccess() {
                    et_chat.setText("");
                }

                @Override
                public void onFail() {

                }
            });
        }
        else et_chat.isEnabled();
    }
}