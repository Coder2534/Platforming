package com.android.platforming.clazz;

import android.app.Application;

public class ThemeApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ThemeUtil.applyTheme(this);
    }
}
