package com.android.platforming.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimePreferenceCompat extends androidx.preference.PreferenceDialogFragmentCompat {
    TimePicker timePicker;
    Calendar calendar;
    TimePreferenceAndroidx preference;

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
            calendar.set(Calendar.SECOND, 0);

            preference.setSummary(preference.getSummary());
            if (preference.callChangeListener(calendar.getTimeInMillis())) {
                preference.putLong(calendar.getTimeInMillis());
            }
        }
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        Log.d("Test","testeat");
        preference = getPreference();
        calendar = getPreference().getCalendar();
        timePicker = new TimePicker(getContext());
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
    }

    @Override
    public TimePreferenceAndroidx getPreference() {
        return (TimePreferenceAndroidx) super.getPreference();
    }

    public static TimePreferenceCompat newInstance(String key) {
        final TimePreferenceCompat fragment = new TimePreferenceCompat();
        final Bundle bundle = new Bundle(1);
        bundle.putString(ARG_KEY, key);
        fragment.setArguments(bundle);
        return fragment;
    }
}
