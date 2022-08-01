package com.android.platforming;

import android.app.Application;

import com.android.platforming.clazz.NotificationHelper;

public class InitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationHelper.createChannels(getApplicationContext());
    }
}
