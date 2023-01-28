package com.platforming.autonomy.fragment;

;
import static com.platforming.autonomy.clazz.User.user;

import android.os.Bundle;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.platforming.autonomy.InitApplication;
import com.platforming.autonomy.clazz.CustomDialog;
import com.android.autonomy.R;

public class PreferenceDesignFragment  extends PreferenceFragmentCompat {
    CustomDialog customDialog;


    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_design, rootKey);
        Log.d("preference:","ㅇ나ㅓㅗㅇ ㅏㅓㅣㅁ농ㅁㄴ회ㅏㅓㅁㄴ ㅣㅗㅓ누확인");
        InitApplication initApplication = ((InitApplication)getActivity().getApplication());

        Preference font = findPreference("font");
        Preference theme = findPreference("theme");
        customDialog = new CustomDialog();

        font.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {

                customDialog.fontDialog(getActivity(), user.getFonts(), initApplication.getAppliedFont(), initApplication.getAppliedTheme());
                return true;
            }
        });

        theme.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {

                customDialog.themeDialog(getActivity(), user.getThemes(), initApplication.getAppliedTheme());
                return false;
            }
        });
    }
}