package com.android.platforming.clazz;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.android.platforming.activity.MainActivity;
import com.android.platforming.activity.WebViewActivity;
import com.example.platforming.R;

public class NotificationHelper extends ContextWrapper {
    private static final Integer WORK_A_NOTIFICATION_CODE = 0;
    private static final Integer WORK_B_NOTIFICATION_CODE = 1;
    public static final String NOTIFICATION_CHANNEL_ID = "channel1ID";

    private Context mContext;

    public NotificationHelper(Context base) {
        super(base);
        mContext = base;
    }

    public static Boolean isNotificationChannelCreated(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                return notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) != null;
            }
            return true;
        } catch (NullPointerException nullException) {
            Toast.makeText(context, "푸시 알림 기능에 문제가 발생했습니다. 앱을 재실행해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
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
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        // Notificatoin을 이루는 공통 부분 정의
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.drawable.ic_baseline_phone_android_24) // 기본 제공되는 이미지
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true); // 클릭 시 Notification 제거

        // A이벤트 알림을 생성한다면
        if (workName.equals("selfDiagnosis")) {
            // Notification 클릭 시 동작할 Intent 입력, 중복 방지를 위해 FLAG_CANCEL_CURRENT로 설정, CODE를 다르게하면 개별 생성
            // Code가 같으면 같은 알림으로 인식하여 갱신작업 진행
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT); // 대기열에 이미 있다면 MainActivity가 아닌 앱 활성화
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.putExtra("workName", "selfDiagnosis");

            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, WORK_A_NOTIFICATION_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            // Notification 제목, 컨텐츠 설정
            notificationBuilder.setContentTitle("자가진단 확인").setContentText("금일 자가진단을 안하셨다면 지금바로 확인하세요.")
                    .setContentIntent(pendingIntent);

            if (notificationManager != null) {
                notificationManager.notify(WORK_A_NOTIFICATION_CODE, notificationBuilder.build());
            }
        } else if (workName.equals("schoolMeal")) {
            SchoolApi schoolApi = new SchoolApi();
            String contentText = null;
            String bigText = null;

            StringBuilder stringBuilder = new StringBuilder();
            for(String result : schoolApi.getResult()){
                stringBuilder.append(result);
                stringBuilder.append("\n");
            }
            bigText = stringBuilder.toString();

            Intent intent = new Intent(mContext, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT); // 대기열에 이미 있다면 MainActivity가 아닌 앱 활성화
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, WORK_B_NOTIFICATION_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            notificationBuilder.setContentTitle("오늘의 급식").setContentText(contentText).setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))
                    .setContentIntent(pendingIntent);

            if (notificationManager != null) {
                notificationManager.notify(WORK_B_NOTIFICATION_CODE, notificationBuilder.build());
            }
        }
    }
}