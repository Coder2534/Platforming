package com.android.platforming.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.platforming.clazz.Alarm;
import com.android.platforming.clazz.NotificationHelper;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String TAG = "AlarmReceiver";
    public static final int NOTIFICATION_SELFDIAGNOSIS_ID = 0;
    public static final int NOTIFICATION_SCHOOLMEAL_ID = 1;
    public static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Received intent : " + intent);
        int requestCode = intent.getIntExtra("workName", 0);

        String workName = null;
        String key = null;

        switch (requestCode){
            case NOTIFICATION_SELFDIAGNOSIS_ID:
                workName = "selfDiagnosis";
                key = "time_selfDiagnosis";
                break;
            case NOTIFICATION_SCHOOLMEAL_ID:
                workName = "schoolMeal";
                key = "time_schoolMeal";
                break;
        }

        SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(context);
        long timeMillis = preferenceManager.getLong(key, 0);
        Calendar calendar_timer = Calendar.getInstance();
        calendar_timer.setTimeInMillis(timeMillis);
        Calendar calendar_now = Calendar.getInstance();
        if(calendar_timer.before(calendar_now)){
            SharedPreferences.Editor editor = preferenceManager.edit();
            calendar_timer.set(Calendar.DATE, calendar_now.get(Calendar.DATE) + 1);
            timeMillis = calendar_timer.getTimeInMillis();
            editor.putLong(key, timeMillis).commit();
        }

        NotificationHelper.createChannels(context);
        NotificationHelper notificationHelper = new NotificationHelper(context);
        notificationHelper.createNotification(workName);

        Alarm.cancelAlarm(context, requestCode);
        Alarm.setAlarm(context, timeMillis, requestCode);
    }
}
