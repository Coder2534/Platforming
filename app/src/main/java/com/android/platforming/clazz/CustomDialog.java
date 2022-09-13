package com.android.platforming.clazz;

import static com.android.platforming.clazz.FirestoreManager.getFirebaseAuth;
import static com.android.platforming.clazz.User.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.widget.ViewPager2;

import com.android.platforming.activity.MainActivity;
import com.android.platforming.adapter.RecyclerViewSliderAdapter;
import com.android.platforming.adapter.TableAdapter;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomDialog {
    AlertDialog dialog;

    public void banDialog(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_message, null);

        TextView message = view.findViewById(R.id.tv_message_message);
        message.setText("현재 계정은 차단되었습니다.");

        Button confirm = view.findViewById(R.id.btn_message_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFirebaseAuth().signOut();
                dialog.dismiss();
                activity.finish();
            }
        });

        builder.setView(view);

        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    public void messageDialog(Activity activity, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_message, null);

        TextView message = view.findViewById(R.id.tv_message_message);
        message.setText(msg);

        Button confirm = view.findViewById(R.id.btn_message_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }

    public void passwordResetDialog(Context context, ListenerInterface listenerInterface){
        final EditText editText = new EditText(context);

        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle("비밀번호 찾기");
        ad.setMessage("이메일을 입력해 주세요.");
        ad.setView(editText);
        ad.setPositiveButton("입력", (dialog, which) -> {
            String email= editText.getText().toString();
            listenerInterface.onSuccess(email);
        });
        ad.setNegativeButton("최소", (dialog, which) -> {
            listenerInterface.onFail();
        });
        ad.show();
    }

    public void selectDialog(Activity activity, String title, ListenerInterface listenerInterface){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_select, null);

        TextView message = view.findViewById(R.id.tv_select_message);
        message.setText("계속하시겠습니까?");

        Button cancel = view.findViewById(R.id.btn_select_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button confirm = view.findViewById(R.id.btn_select_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerInterface.onSuccess();
                dialog.dismiss();
            }
        });

        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }

    public void verificationDialog(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("본인인증");

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_verification, null);

        Button cancel = view.findViewById(R.id.btn_verification_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        EditText et_password = view.findViewById(R.id.et_verification_password);

        Button zontinue = view.findViewById(R.id.btn_verification_continue);
        zontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = et_password.getText().toString();
                FirebaseUser user = getFirebaseAuth().getCurrentUser();
                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);
                user.reauthenticate(credential).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        dialog.dismiss();
                        newPasswordDialog(activity, user);
                    }
                    else{
                        dialog.dismiss();
                        messageDialog(activity, "인증에 실패했습니다.");
                    }
                });
            }
        });

        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }

    private void newPasswordDialog(Activity activity, FirebaseUser user){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("비밀번호 변경");

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_setting_newpassword, null);

        Button cancel = view.findViewById(R.id.btn_newpassword_cancel);
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        EditText newpassword = view.findViewById(R.id.et_newpassword_newpassword);
        EditText passwordcheck = view.findViewById(R.id.et_newpassword_passwordcheck);

        Button change = view.findViewById(R.id.btn_newpassword_change);
        change.setOnClickListener(v -> {
            String newPassword = newpassword.getText().toString();
            if(newPassword.equals(passwordcheck.getText().toString())){
                user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            messageDialog(activity, "비밀번호를 성공적으로 변경했습니다.");
                        } else {
                            messageDialog(activity, "비밀번호 변경을 실패했습니다.");
                        }
                    }
                });
            }
        });

        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }

    public void dailyTaskDialog(Activity activity, int applytheme,ListenerInterface listenerInterface){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_dailytask, null);


        CheckBox signIn = view.findViewById(R.id.cb_dailytask_signin);
        if(user.getDailyTasks().get(0) == 1L)
            signIn.setChecked(true);
        CheckBox selfDiagnosis = view.findViewById(R.id.cb_dailytask_selfdiagnosis);
        if(user.getDailyTasks().get(1) == 1L)
            selfDiagnosis.setChecked(true);
        CheckBox writePost = view.findViewById(R.id.cb_dailytask_writepost);
        if(user.getDailyTasks().get(2) == 1L)
            writePost.setChecked(true);
        CheckBox writeComment = view.findViewById(R.id.cb_dailytask_writecomment);
        writeComment.setText(String.format("댓글 작성(%d/2)", user.getDailyTasks().get(3)));
        if(user.getDailyTasks().get(3) == 2L)
            writeComment.setChecked(true);
        if (applytheme==4){
            signIn.setTextColor(Color.WHITE);
            selfDiagnosis.setTextColor(Color.WHITE);
            writePost.setTextColor(Color.WHITE);
            writeComment.setTextColor(Color.WHITE);
        }
        else{
            signIn.setTextColor(Color.BLACK);
            selfDiagnosis.setTextColor(Color.BLACK);
            writePost.setTextColor(Color.BLACK);
            writeComment.setTextColor(Color.BLACK);
        }

            Button receipt = view.findViewById(R.id.btn_dailytask_receipt);
        if(user.getPoint_receipt() > 0){
            receipt.setText(user.getPoint_receipt() + "포인트 수령");
            receipt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirestoreManager firestoreManager = new FirestoreManager();
                    firestoreManager.updateUserData(new HashMap<String, Object>() {{
                        put("point", user.getPoint() + user.getPoint_receipt());
                        put("point_receipt", 0);
                    }}, listenerInterface);
                    dialog.dismiss();
                }
            });
        }

        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }

    public void themeDialog(Activity activity, List themes, int applytheme){
        int dialogtheme;
        if (applytheme==4){
            dialogtheme = 4;
        }
        else dialogtheme=5;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity,dialogtheme);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_design_theme, null);
        builder.setTitle("테마");

        RadioGroup radioGroup = view.findViewById(R.id.rg_dialog_theme);
        RadioButton[] radioThemeList = new RadioButton[themes.size()];

        for(int i = 0; i< themes.size();i++){
            RadioButton radioButton = new RadioButton(activity);
            radioButton.setTextColor(Color.BLACK);
            if (themes.get(i).equals(0L)){
                radioButton.setId((int)500);
                radioButton.setText("라이트 테마(기본)");
                if (applytheme == 4){
                    radioButton.setTextColor(Color.WHITE);
                }
                else radioButton.setTextColor(Color.BLACK);
            }
            else if(themes.get(i).equals(1L)){
                radioButton.setId(500+1);
                radioButton.setText("핑크 테마");
                if (applytheme == 4){
                    radioButton.setTextColor(Color.WHITE);
                }
                else radioButton.setTextColor(Color.BLACK);
            }
            else if(themes.get(i).equals(2L)){
                radioButton.setId(500+2);
                radioButton.setText("블루 테마");
                if (applytheme == 4){
                    radioButton.setTextColor(Color.WHITE);
                }
                else radioButton.setTextColor(Color.BLACK);
            }
            else if(themes.get(i).equals(3L)){
                radioButton.setId(500+3);
                radioButton.setText("그린 테마");
                if (applytheme == 4){
                    radioButton.setTextColor(Color.WHITE);
                }
                else radioButton.setTextColor(Color.BLACK);
            }
            else if(themes.get(i).equals(4L)){
                radioButton.setId(500+4);
                radioButton.setText("블랙 테마");
                if (applytheme == 4){
                    radioButton.setTextColor(Color.WHITE);
                }
                else radioButton.setTextColor(Color.BLACK);
            }
            radioThemeList[i] = radioButton;
            radioGroup.addView(radioThemeList[i]);

        }
        Log.d("check_themeid", String.valueOf(600+applytheme));
        radioGroup.check((Integer)500+applytheme);

        Button apply = view.findViewById(R.id.btn_dialog_theme_apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = radioGroup.getCheckedRadioButtonId() - (int) 500;
                PreferenceManager.getDefaultSharedPreferences(activity).edit().putInt("theme", index).apply();

                dialog.dismiss();

                //MainActivity.getActivity().recreate();
                // Refresh main activity upon close of dialog box
                Intent refresh = new Intent(MainActivity.getActivity(), MainActivity.class);
                MainActivity.getActivity().startActivity(refresh);
                MainActivity.getActivity().finish();

                activity.finish();
            }
        });

        Button getout = view.findViewById(R.id.btn_dialog_theme_getout);
        getout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }

    public void fontDialog(Activity activity, List fonts, int applyfont, int applytheme){
        int dialogtheme;
        if (applytheme==4){
            dialogtheme = 4;
        }
        else dialogtheme=5;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity,dialogtheme);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_design_theme, null);
        builder.setTitle("폰트");

        RadioGroup radioGroup = view.findViewById(R.id.rg_dialog_theme);
        RadioButton[] radioThemeList = new RadioButton[fonts.size()];

        for(int i = 0; i< fonts.size();i++){
            RadioButton radioButton = new RadioButton(activity);
            radioButton.setTextColor(Color.BLACK);
            if (fonts.get(i).equals(0L)){
                radioButton.setId((int)600);
                radioButton.setText("레페리 포인트(기본)");
                if (applytheme == 4){
                    radioButton.setTextColor(Color.WHITE);
                }
                else radioButton.setTextColor(Color.BLACK);
            }
            else if(fonts.get(i).equals(1L)){
                radioButton.setId(600+1);
                radioButton.setText("평창평화체");
                if (applytheme == 4){
                    radioButton.setTextColor(Color.WHITE);
                }
                else radioButton.setTextColor(Color.BLACK);
            }
            else if(fonts.get(i).equals(2L)){
                radioButton.setId(600+2);
                radioButton.setText("비트로 코어체");
                if (applytheme == 4){
                    radioButton.setTextColor(Color.WHITE);
                }
                else radioButton.setTextColor(Color.BLACK);
            }
            else if(fonts.get(i).equals(3L)){
                radioButton.setId(600+3);
                radioButton.setText("갈무리9");
                if (applytheme == 4){
                    radioButton.setTextColor(Color.WHITE);
                }
                else radioButton.setTextColor(Color.BLACK);
            }
            else if(fonts.get(i).equals(4L)){
                radioButton.setId(600+4);
                radioButton.setText("산토끼체");
                if (applytheme == 4){
                    radioButton.setTextColor(Color.WHITE);
                }
                else radioButton.setTextColor(Color.BLACK);
            }
            radioThemeList[i] = radioButton;
            radioGroup.addView(radioThemeList[i]);


        }
        Log.d("check_fontid", String.valueOf(600+applyfont));
        radioGroup.check((Integer)600+applyfont);

        Button apply = view.findViewById(R.id.btn_dialog_theme_apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = radioGroup.getCheckedRadioButtonId() - (int)600;
                Log.d("check_index_", String.valueOf(index));
                PreferenceManager.getDefaultSharedPreferences(activity).edit().putInt("font", index).apply();

                dialog.dismiss();

                //MainActivity.getActivity().recreate();
                // Refresh main activity upon close of dialog box
                Intent refresh = new Intent(MainActivity.getActivity(), MainActivity.class);
                MainActivity.getActivity().startActivity(refresh);
                MainActivity.getActivity().finish();

                activity.finish();
            }
        });

        Button getout = view.findViewById(R.id.btn_dialog_theme_getout);
        getout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }

    public void editSchedule(FragmentActivity activity, ArrayList<ArrayList<TableItem>> schedules_, ListenerInterface listenerInterface){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_schedule_edit, null);

        TextView dayOfWeek = view.findViewById(R.id.tv_schedule_edit_dayofweek);

        ArrayList<ArrayList<TableItem>> schedules = new ArrayList<>();
        for (ArrayList<TableItem> tableItems : schedules_)
            schedules.add(new ArrayList<>(tableItems));


        ViewPager2 viewPager = view.findViewById(R.id.vp_schedule_edit);
        RecyclerViewSliderAdapter sliderAdapter = new RecyclerViewSliderAdapter(schedules);
        viewPager.setAdapter(sliderAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                dayOfWeek.setText(getDayOfWeek(position));
            }
        });
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setOffscreenPageLimit(5);

        ImageButton previous = view.findViewById(R.id.btn_schedule_edit_previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentItem - 1 == -1 ? 4 : currentItem - 1, true);
                dayOfWeek.setText(getDayOfWeek(viewPager.getCurrentItem()));
            }
        });


        ImageButton next = view.findViewById(R.id.btn_schedule_edit_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentItem + 1 == 5 ? 0 : currentItem + 1, true);
                dayOfWeek.setText(getDayOfWeek(viewPager.getCurrentItem()));
            }
        });

        Button add = view.findViewById(R.id.btn_schedule_edit_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sliderAdapter.addSchedule(viewPager.getCurrentItem());
            }
        });

        Button save = view.findViewById(R.id.btn_schedule_edit_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sliderAdapter.saveSchedules();
                for(int i = 0; i < schedules_.size(); ++i){
                    ArrayList<TableItem> tableItems = schedules_.get(i);
                    tableItems.clear();
                    tableItems.addAll(schedules.get(i));
                }
                listenerInterface.onSuccess();
                dialog.dismiss();
            }
        });

        builder.setView(view);

        dialog = builder.create();
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    private String getDayOfWeek(int index){
        switch (index){
            case 0:
                return "월";

            case 1:
                return "화";

            case 2:
                return "수";

            case 3:
                return "목";

            default:
                return "금";
        }
    }

    public void expandSchedule(Activity activity, TableAdapter tableAdapter){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_schedule_expand, null);

        GridView timetable = view.findViewById(R.id.gv_schedule_expand);
        timetable.setAdapter(tableAdapter);

        Button confirm = view.findViewById(R.id.btn_schedule_expand_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }
}