package com.android.platforming.activity;

import static com.android.platforming.clazz.User.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.platforming.InitApplication;
import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BulletinBoardRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InitApplication initApplication = ((InitApplication)getApplication());
        switch (initApplication.getAppliedTheme()){
            case 0:setTheme(R.style.Theme_Platforming);break;
            case 1:setTheme(R.style.PinkTheme);break;
            case 2:setTheme(R.style.BuleTheme);break;
            case 3:setTheme(R.style.GreenTheme);break;
            case 4:setTheme(R.style.BlackTheme);break;
        }
        switch (initApplication.getAppliedFont()){
            case 0:setTheme(R.style.leferipointwhiteobliqueFont);break;
            case 1:setTheme(R.style.SlowFont);break;
            case 2:setTheme(R.style.AgainFont);break;
            case 3:setTheme(R.style.Galmuri9Font);break;
            case 4:setTheme(R.style.MugunghwaFont);break;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticeboard_register);

        int type = getIntent().getIntExtra("type", 0);

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
                data.put("uid", user.getUid());
                data.put("type", type);
                data.put("profileIndex", user.getProfileIndex());
                data.put("nickname", user.getNickname());
                data.put("date", System.currentTimeMillis());
                data.put("title", editText_title.getText().toString());
                data.put("detail", editText_detail.getText().toString());
                data.put("likes", new ArrayList<String>());
                FirestoreManager firestoreManager = new FirestoreManager();
                firestoreManager.writePostData(data, new ListenerInterface() {
                    @Override
                    public void onSuccess() {
                        //refresh NoticeBoardListFragment
                        setResult(RESULT_OK);
                        onBackPressed();
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