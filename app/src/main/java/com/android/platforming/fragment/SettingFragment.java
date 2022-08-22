package com.android.platforming.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import androidx.annotation.Nullable;

import com.android.platforming.clazz.Alarm;
import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.User;
import com.android.platforming.recevier.AlarmReceiver;
import com.android.platforming.view.TimePreferenceCompat;
import com.example.platforming.R;

import java.util.Calendar;

public class SettingFragment extends PreferenceFragmentCompat {
    SharedPreferences pref;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_setting);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        context = getActivity().getBaseContext();

        TimePreferenceCompat compat = new TimePreferenceCompat();

        PreferenceScreen uid = (PreferenceScreen) findPreference("uid");
        uid.setSummary(User.getUser().getUid());
        PreferenceScreen email = (PreferenceScreen) findPreference("email");
        email.setSummary(FirestoreManager.getFirebaseAuth().getCurrentUser().getEmail());
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {

    }

    @Override
    public void onDisplayPreferenceDialog(@NonNull Preference preference) {
        super.onDisplayPreferenceDialog(preference);
    }

    @Override
    public void onResume() {
        super.onResume();
        pref.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        pref.unregisterOnSharedPreferenceChangeListener(listener);
    }

    SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals("selfDiagnosis")) {
                if(pref.getBoolean("selfDiagnosis", false)) {
                    Alarm.setAlarm(context, pref.getLong("time_selfDiagnosis", 0), AlarmReceiver.NOTIFICATION_SELFDIAGNOSIS_ID);
                } else{
                    Alarm.cancelAlarm(context, AlarmReceiver.NOTIFICATION_SELFDIAGNOSIS_ID);
                }
            } else if(key.equals("time_selfDiagnosis")) {
                Alarm.cancelAlarm(context, AlarmReceiver.NOTIFICATION_SELFDIAGNOSIS_ID);
                Alarm.setAlarm(context, pref.getLong("time_selfDiagnosis", 0), AlarmReceiver.NOTIFICATION_SELFDIAGNOSIS_ID);
            } else if(key.equals("schoolMeal")){
                if(pref.getBoolean("schoolMeal", false)) {
                    Alarm.setAlarm(context, pref.getLong("time_schoolMeal", 0), AlarmReceiver.NOTIFICATION_SCHOOLMEAL_ID);
                } else{
                    Alarm.cancelAlarm(context, AlarmReceiver.NOTIFICATION_SCHOOLMEAL_ID);
                }
            } else if(key.equals("time_schoolMeal")) {
                Alarm.cancelAlarm(context, AlarmReceiver.NOTIFICATION_SCHOOLMEAL_ID);
                Alarm.setAlarm(context, pref.getLong("time_schoolMeal", 0), AlarmReceiver.NOTIFICATION_SCHOOLMEAL_ID);
            }
        }
    };
}
