package com.android.platforming.fragment;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import androidx.annotation.Nullable;

import com.example.platforming.R;

public class SettingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        addPreferencesFromResource(R.xml.fragment_setting);
    }
}
