package com.android.platforming.fragment;

import static com.android.platforming.clazz.FirestoreManager.getFirebaseAuth;
import static com.android.platforming.clazz.User.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.android.platforming.activity.MainActivity;
import com.android.platforming.activity.SignActivity;
import com.android.platforming.clazz.CustomDialog;
import com.android.platforming.interfaze.ListenerInterface;
import com.android.platforming.R;
import com.google.firebase.auth.UserInfo;

public class PreferenceAccountFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_account, rootKey);

        Preference uid = findPreference("uid");
        Preference email = findPreference("email");
        Preference changOfPassword = findPreference("changOfPassword");
        Preference signOut = findPreference("signOut");

        uid.setSummary(user.getUid());
        email.setSummary(user.getEmail());

        boolean isPassword = false;
        for(UserInfo data: getFirebaseAuth().getCurrentUser().getProviderData()){
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
                        getFirebaseAuth().signOut();
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