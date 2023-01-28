package com.platforming.autonomy.clazz;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.android.autonomy.R;
import com.platforming.autonomy.InitApplication;

public class ThemeManager {

    public static void TFCall(AppCompatActivity activity, InitApplication initApplication){
        switch (initApplication.getAppliedTheme()){
            case 0:activity.setTheme(R.style.WhiteTheme);break;
            case 1:activity.setTheme(R.style.PinkTheme);break;
            case 2:activity.setTheme(R.style.BuleTheme);break;
            case 3:activity.setTheme(R.style.GreenTheme);break;
            case 4:activity.setTheme(R.style.BlackTheme);break;
        }
        switch (initApplication.getAppliedFont()){
            case 0:activity.setTheme(R.style.pretendardFont);break;
            case 1:activity.setTheme(R.style.snowFont);break;
            case 2:activity.setTheme(R.style.bmeFont);break;
            case 3:activity.setTheme(R.style.establishreFont);break;
            case 4:activity.setTheme(R.style.eulyoo1945Font);break;
        }
    }
    public static void TCall(AppCompatActivity activity, InitApplication initApplication) {
        switch (initApplication.getAppliedTheme()) {
            case 0:
                activity.setTheme(R.style.WhiteTheme);
                break;
            case 1:
                activity.setTheme(R.style.PinkTheme);
                break;
            case 2:
                activity.setTheme(R.style.BuleTheme);
                break;
            case 3:
                activity.setTheme(R.style.GreenTheme);
                break;
            case 4:
                activity.setTheme(R.style.BlackTheme);
                break;
        }
    }
}
