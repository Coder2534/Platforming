package com.android.platforming.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.User;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NoticeBoardRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board_register);

        String workName = getIntent().getStringExtra("workName");

        ImageButton button_back = findViewById(R.id.btn_noticeboard_register_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button button_confirm = findViewById(R.id.btn_noticeboard_register_confirm);
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText_title = findViewById(R.id.et_noticeboard_register_title);
                EditText editText_detail = findViewById(R.id.et_noticeboard_register_detail);
                Map<String, Object> data = new HashMap<>();
                data.put("uid", User.getUser().getUid());
                data.put("profileIndex", User.getUser().getProfileIndex());
                data.put("nickname", User.getUser().getNickName());
                data.put("date", System.currentTimeMillis());
                data.put("title", editText_title.getText().toString());
                data.put("detal", editText_detail.getText().toString());
                data.put("thumb_up", 0);
                FirestoreManager firestoreManager = new FirestoreManager();
                firestoreManager.writePostData(workName, data, new ListenerInterface() {
                    @Override
                    public void onSuccess() {
                        onBackPressed();
                    }

                    @Override
                    public void onFail() {

                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.none, R.anim.finish_activity_noticeboard);
    }
}