package com.android.platforming.activity;

import static com.android.platforming.clazz.User.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.platforming.InitApplication;
import com.android.platforming.clazz.CustomDialog;
import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.WordFilter;
import com.android.platforming.interfaze.ListenerInterface;
import com.android.platforming.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BulletinBoardRegisterActivity extends AppCompatActivity {

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InitApplication initApplication = ((InitApplication)getApplication());
        switch (initApplication.getAppliedTheme()){
            case 0:setTheme(R.style.WhiteTheme);break;
            case 1:setTheme(R.style.PinkTheme);break;
            case 2:setTheme(R.style.BuleTheme);break;
            case 3:setTheme(R.style.GreenTheme);break;
            case 4:setTheme(R.style.BlackTheme);break;
        }
        switch (initApplication.getAppliedFont()){
            case 0:setTheme(R.style.pretendardFont);break;
            case 1:setTheme(R.style.snowFont);break;
            case 2:setTheme(R.style.bmeFont);break;
            case 3:setTheme(R.style.establishreFont);break;
            case 4:setTheme(R.style.eulyoo1945Font);break;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticeboard_register);

        activity = this;

        int type = getIntent().getIntExtra("type", 0);

        EditText editText_title = findViewById(R.id.et_noticeboard_register_title);
        EditText editText_detail = findViewById(R.id.et_noticeboard_register_detail);
        editText_detail.addTextChangedListener(new TextWatcher(){
            String previousString = "";
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
                previousString= s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText_detail.getLineCount() > 5){
                    editText_detail.setText(previousString);
                    editText_detail.setSelection(editText_detail.length());
                }
            }
        });

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
                button_confirm.setClickable(false);

                String title = editText_title.getText().toString();
                String detail = editText_detail.getText().toString();
                if(title.equals("") || detail.equals("")){
                    button_confirm.setClickable(true);
                    return;
                }

                WordFilter wordFilter = new WordFilter();
                if(wordFilter.isSwearWords(title + detail)){
                    CustomDialog customDialog = new CustomDialog();
                    customDialog.messageDialog(activity, "금지어가 포함되어있습니다.\n" + wordFilter.getFilteredForbiddenWords().toString());
                    button_confirm.setClickable(true);
                    return;
                }


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
                        if(user.getDailyTasks().get(2) < 1){
                            List<Long> dailyTasks = new LinkedList<>(user.getDailyTasks());
                            dailyTasks.set(2, 1L);
                            firestoreManager.updateUserData(new HashMap<String, Object>() {{
                                put("point_receipt", user.getPoint_receipt() + 10);
                                put("dailyTasks", dailyTasks);
                            }}, new ListenerInterface() {
                                @Override
                                public void onSuccess() {
                                    user.addPoint_receipt(10);
                                    user.getDailyTasks().set(2, 1L);
                                }
                            });
                        }

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