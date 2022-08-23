package com.android.platforming.clazz;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.platforming.R;

public class ChangOfPasswordDialog extends Dialog {
    Context context;

    public ChangOfPasswordDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_setting_changeofpassword);
    }
}
