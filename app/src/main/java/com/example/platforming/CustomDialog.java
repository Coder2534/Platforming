package com.example.platforming;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class CustomDialog {
    public static void ErrorDialog(Context context, String message){
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
}