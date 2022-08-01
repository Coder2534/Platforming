package com.android.platforming.clazz;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.android.platforming.activity.MainActivity;
import com.example.platforming.R;

//https://8iggy.tistory.com/65?category=906411
//https://zladnrms.tistory.com/157

public class NotificationHelper extends ContextWrapper {
    private static final Integer WORK_A_NOTIFICATION_CODE = 0;
    private static final Integer WORK_B_NOTIFICATION_CODE = 1;
    public static final String NOTIFICATION_CHANNEL_ID = "channel1ID";

    private Context mContext;

    public NotificationHelper(Context base) {
        super(base);
        mContext = base;
    }

    public static void createChannels(Context context){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, context.getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("푸시알림");
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500});
            notificationChannel.setLightColor(R.color.purple_200);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void createNotification(String workName) {
        // 클릭 시 MainActivity 호출
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT); // 대기열에 이미 있다면 MainActivity가 아닌 앱 활성화
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        // Notificatoin을 이루는 공통 부분 정의
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.drawable.ic_baseline_phone_android_24) // 기본 제공되는 이미지
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true); // 클릭 시 Notification 제거

        // A이벤트 알림을 생성한다면
        if (workName.equals("self-diagnosis")) {
            // Notification 클릭 시 동작할 Intent 입력, 중복 방지를 위해 FLAG_CANCEL_CURRENT로 설정, CODE를 다르게하면 개별 생성
            // Code가 같으면 같은 알림으로 인식하여 갱신작업 진행
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, WORK_A_NOTIFICATION_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            // Notification 제목, 컨텐츠 설정
            notificationBuilder.setContentTitle("자가진단 확인").setContentText("금일 자가진단을 하셨나요? 안하셨다면 지금바로 확인하세요.")
                    .setContentIntent(pendingIntent);

            if (notificationManager != null) {
                notificationManager.notify(WORK_A_NOTIFICATION_CODE, notificationBuilder.build());
            }
        } else if (workName.equals("school-meal")) {
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, WORK_B_NOTIFICATION_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            notificationBuilder.setContentTitle("오늘의 급식").setContentText("set a Notification contents")
                    .setContentIntent(pendingIntent);

            if (notificationManager != null) {
                notificationManager.notify(WORK_B_NOTIFICATION_CODE, notificationBuilder.build());
            }
        }
    }
}