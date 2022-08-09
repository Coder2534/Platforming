package com.android.platforming.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    public static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Received intent : " + intent);
        if(intent.getAction() == "android.intent.action.BOOT_COMPLETED"){

        }
    }
}
