package com.android.platforming.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.android.platforming.clazz.CustomDialog;
import com.android.platforming.clazz.User;
import com.example.platforming.R;

public class PreferenceDesignFragment  extends PreferenceFragmentCompat {
    AlertDialog dialog;
    CustomDialog customDialog;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_design, rootKey);

        Preference font = findPreference("font");
        Preference theme = findPreference("theme");

        font.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Log.d("check_font_dialog","ok");
                customDialog.themeDialog(getActivity(), User.getUser().getFonts());
                Log.d("check_font_dialog2","ok");
                return true;
            }
        });

        theme.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                return true;
            }
        });
    }
}