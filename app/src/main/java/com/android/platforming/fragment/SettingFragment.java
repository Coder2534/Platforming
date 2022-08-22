package com.android.platforming.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import androidx.annotation.Nullable;

import com.android.platforming.clazz.Alarm;
import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.User;
import com.android.platforming.recevier.AlarmReceiver;
import com.android.platforming.view.TimePreference;
import com.android.platforming.view.TimePreferenceAndroidx;
import com.android.platforming.view.TimePreferenceCompat;
import com.example.platforming.R;

import java.util.Calendar;

public class SettingFragment extends PreferenceFragmentCompat {
    SharedPreferences pref;
    Context context;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        addPreferencesFromResource(R.xml.fragment_setting);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        context = getActivity().getBaseContext();
    }

    @Override
    public void onDisplayPreferenceDialog(@NonNull Preference preference) {
        Toast.makeText(getContext(), "onDisplayPreferenceDialog", Toast.LENGTH_SHORT).show();
        if(preference instanceof TimePreferenceAndroidx){
            DialogFragment dialogFragment = TimePreferenceCompat.newInstance(preference.getKey());
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(getFragmentManager(), null);
        } else{
            super.onDisplayPreferenceDialog(preference);
        }
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
