package com.android.platforming.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.platforming.adapter.CommunityAdapter;
import com.android.platforming.clazz.ChatModel;
import com.android.platforming.clazz.User;
import com.example.platforming.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

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
        myuid = String.valueOf(User.getUser().getDataMap());
        Log.d("check_Uid",myuid);
    }
}