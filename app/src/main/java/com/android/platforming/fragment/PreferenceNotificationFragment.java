package com.android.platforming.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.android.platforming.clazz.Alarm;
import com.android.platforming.recevier.AlarmReceiver;
import com.android.platforming.view.TimePreference;
import com.android.platforming.view.TimePreferenceCompat;
import com.example.platforming.R;

public class PreferenceNotificationFragment extends PreferenceFragmentCompat {

    SharedPreferences pref;
    Context context;

    Preference selfDiagnosis;
    Preference time_selfDiagnosis;
    Preference schoolMeal;
    Preference time_schoolMeal;
    Preference notice;
    Preference thumb_up;
    Preference comment;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_notification, rootKey);

        pref = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        context = getContext();

        if (rootKey == null) {
            selfDiagnosis = findPreference("selfDiagnosis");
            time_selfDiagnosis = findPreference("time_selfDiagnosis");
            schoolMeal = findPreference("schoolMeal");
            time_schoolMeal = findPreference("time_schoolMeal");
            notice = findPreference("notice");
            thumb_up = findPreference("thumb_up");
            comment = findPreference("comment");
        }
    }

    @Override
    public void onDisplayPreferenceDialog(@NonNull Preference preference) {
        Toast.makeText(getContext(), "onDisplayPreferenceDialog", Toast.LENGTH_SHORT).show();
        if(preference instanceof TimePreference){
            DialogFragment dialogFragment = TimePreferenceCompat.newInstance(preference.getKey());
            dialogFragment.setTargetFragment(this, 0); //deprecated
            dialogFragment.show(requireActivity().getSupportFragmentManager(), null);
        } else{
            super.onDisplayPreferenceDialog(preference);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pref.unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onResume() {
        super.onResume();
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