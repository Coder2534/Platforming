package com.android.platforming.fragment;

;
import static com.android.platforming.clazz.User.user;

import android.os.Bundle;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.android.platforming.clazz.CustomDialog;
import com.example.platforming.R;

public class PreferenceDesignFragment  extends PreferenceFragmentCompat {
    CustomDialog customDialog;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_design, rootKey);

        Preference font = findPreference("font");
        Preference theme = findPreference("theme");
        customDialog = new CustomDialog();
        font.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {

                customDialog.fontDialog(getActivity(), user.getFonts());
                return true;
            }
        });

        theme.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Log.d("check_font_dialog", String.valueOf(user.getThemes()));

                customDialog.themeDialog(getActivity(), user.getThemes());
                Log.d("check_font_dialog2","ok");
                return false;
            }
        });
    }
}