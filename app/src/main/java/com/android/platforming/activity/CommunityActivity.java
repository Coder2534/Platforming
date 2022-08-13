package com.android.platforming.activity;

import android.os.Bundle;
import android.util.Log;

import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;
import com.android.platforming.adapter.CommunityAdapter;

import com.android.platforming.clazz.User;
import com.example.platforming.R;


import java.text.SimpleDateFormat;

public class CommunityActivity extends AppCompatActivity {
    private String chatRoomUid;
    private String myuid;       //나의 id
    private String destUid;     //상대방 uid

    private RecyclerView rv_chat;
    private EditText et_chat;
    private Button btn_send;

    private User destUser;
    public CommunityAdapter adapter;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy.MM.dd HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        myuid = User.getUser().getUid();
        Log.d("check_Uid",myuid);
    }
}