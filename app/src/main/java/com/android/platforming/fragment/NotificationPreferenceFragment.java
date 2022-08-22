package com.android.platforming.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.platforming.R;

public class NotificationPreferenceFragment extends PreferenceFragmentCompat {

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
}