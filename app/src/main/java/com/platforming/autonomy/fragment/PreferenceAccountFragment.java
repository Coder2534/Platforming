package com.platforming.autonomy.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.platforming.autonomy.activity.MainActivity;
import com.platforming.autonomy.activity.SignActivity;
import com.platforming.autonomy.clazz.CustomDialog;
import com.platforming.autonomy.interfaze.ListenerInterface;
import com.android.autonomy.R;
import com.google.firebase.auth.UserInfo;
import com.platforming.autonomy.clazz.FirestoreManager;
import com.platforming.autonomy.clazz.User;

public class PreferenceAccountFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_account, rootKey);

        Preference uid = findPreference("uid");
        Preference email = findPreference("email");
        Preference changOfPassword = findPreference("changOfPassword");
        Preference signOut = findPreference("signOut");

        uid.setSummary(User.user.getUid());
        email.setSummary(User.user.getEmail());

        boolean isPassword = false;
        for(UserInfo data: FirestoreManager.getFirebaseAuth().getCurrentUser().getProviderData()){
            if(data.getProviderId().equals("password"))
                isPassword = true;
        }

        if (isPassword) {
            changOfPassword.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(@NonNull Preference preference) {
                    CustomDialog customDialog = new CustomDialog();
                    customDialog.verificationDialog(getActivity());
                    return true;
                }
            });
        } else {
            PreferenceScreen account = findPreference("account");
            account.removePreference(changOfPassword);
        }

        signOut.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Activity activity = getActivity();

                CustomDialog customDialog = new CustomDialog();
                customDialog.selectDialog(activity, "로그아웃", new ListenerInterface() {
                    @Override
                    public void onSuccess() {
                        FirestoreManager.getFirebaseAuth().signOut();
                        Intent loginIntent = new Intent(activity, SignActivity.class);
                        activity.startActivity(loginIntent);
                        MainActivity.getActivity().finish();
                        activity.finish();
                    }
                });
                return true;
            }
        });
    }
}