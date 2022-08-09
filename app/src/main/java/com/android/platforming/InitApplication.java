package com.android.platforming;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

public class InitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if(pref.getBoolean("firstActivate", true)){
            Calendar calendar_timer = Calendar.getInstance();
            calendar_timer.set(Calendar.HOUR_OF_DAY, 8);
            calendar_timer.set(Calendar.MINUTE, 10);
            calendar_timer.set(Calendar.SECOND, 0);
            Calendar calendar_now = Calendar.getInstance();

            if(calendar_timer.before(calendar_now))
                calendar_timer.set(Calendar.DATE, calendar_now.get(Calendar.DATE) + 1);

            SharedPreferences.Editor editor = pref.edit();
            editor.putLong("time_selfDiagnosis", calendar_timer.getTimeInMillis());
            editor.putLong("time_schoolMeal", calendar_timer.getTimeInMillis());
            editor.putBoolean("firstActivate", false);
            editor.apply();
        }
    }
}