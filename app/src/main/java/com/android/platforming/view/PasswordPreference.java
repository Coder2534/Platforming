package com.android.platforming.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.format.DateFormat;
import android.util.AttributeSet;

import androidx.preference.DialogPreference;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
