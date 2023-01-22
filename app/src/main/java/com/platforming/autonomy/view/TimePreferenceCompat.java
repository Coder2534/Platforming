package com.platforming.autonomy.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import com.android.autonomy.R;

import java.util.Calendar;

public class TimePreferenceCompat extends androidx.preference.PreferenceDialogFragmentCompat {
    TimePicker timePicker;
    Calendar calendar;
    TimePreference preference;

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
        preference = getPreference();
        calendar = getPreference().getCalendar();
        timePicker = v.findViewById(R.id.tp_preference_timepicker);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));

    }

    @Override
    public TimePreference getPreference() {
        return (TimePreference) super.getPreference();
    }

    public static TimePreferenceCompat newInstance(String key) {
        final TimePreferenceCompat fragment = new TimePreferenceCompat();
        final Bundle bundle = new Bundle(1);
        bundle.putString(ARG_KEY, key);
        fragment.setArguments(bundle);
        return fragment;
    }
}
