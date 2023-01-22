package com.platforming.autonomy.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.DialogPreference;

public class PasswordPreference extends DialogPreference {

    public PasswordPreference(Context ctxt) {
        this(ctxt, null);
    }

    public PasswordPreference(Context ctxt, AttributeSet attrs) {
        this(ctxt, attrs, android.R.attr.dialogPreferenceStyle);
    }

    public PasswordPreference(Context ctxt, AttributeSet attrs, int defStyle) {
        super(ctxt, attrs, defStyle);
        setPositiveButtonText("확인");
        setNegativeButtonText("취소");
    }
}
