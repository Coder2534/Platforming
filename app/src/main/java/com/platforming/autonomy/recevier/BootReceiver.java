package com.platforming.autonomy.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.platforming.autonomy.clazz.Alarm;

public class BootReceiver extends BroadcastReceiver {
    public static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Received intent : " + intent);
        if(intent.getAction() == "android.intent.action.BOOT_COMPLETED"){
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
            if(pref.getBoolean("selfDiagnosis", false)){
                Alarm.setAlarm(context, pref.getLong("time_selfDiagnosis", 0), AlarmReceiver.NOTIFICATION_SELFDIAGNOSIS_ID);
            }
            if(pref.getBoolean("schoolMeal", false)){
                Alarm.setAlarm(context, pref.getLong("time_schoolMeal", 0), AlarmReceiver.NOTIFICATION_SCHOOLMEAL_ID);
            }
        }
    }
}
