package com.android.platforming.clazz;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.android.platforming.recevier.AlarmReceiver;

public class Alarm {
    private static AlarmManager alarmManager = null;

    private static void setAlarmManager(Context context){
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public static void setAlarm(Context context, long timeInMillis, int requestCode){
        if(alarmManager == null)
            setAlarmManager(context);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("workName", requestCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis,pendingIntent);
        }
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis,pendingIntent);
        }
        else{
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis,pendingIntent);
        }
    }

    public static void cancelAlarm(Context context, int requestCode){
        if(alarmManager == null)
            setAlarmManager(context);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("workName", requestCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE);

        try {
            alarmManager.cancel(pendingIntent);
        }catch (Exception e){
            Log.e("Alarm", e.getMessage());
        }
    }
}
