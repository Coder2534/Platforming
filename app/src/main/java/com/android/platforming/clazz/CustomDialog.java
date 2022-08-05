package com.android.platforming.clazz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

public class CustomDialog {
    public void errorDialog(Context context, String message){
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setIcon(R.mipmap.ic_launcher);
        ad.setMessage(message);

        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ad.show();
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
}