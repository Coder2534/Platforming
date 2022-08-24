package com.android.platforming.fragment;

import static com.android.platforming.clazz.FirestoreManager.getFirebaseAuth;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.android.platforming.clazz.CustomDialog;
import com.android.platforming.clazz.User;
import com.example.platforming.R;

public class AccountPreferenceFragment extends PreferenceFragmentCompat {

    SharedPreferences pref;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_account, rootKey);

        Preference uid = findPreference("uid");
        Preference email = findPreference("email");
        Preference changOfPassword = findPreference("changOfPassword");
        Preference signOut = findPreference("signOut");

        uid.setSummary(User.getUser().getUid());
        email.setSummary(User.getUser().getEmail());

        String provider = getFirebaseAuth().getCurrentUser().getProviderData().get(0).getProviderId();
        Log.d("FirebaseAuth","provider: "+provider);
        if(!provider.equals("password")){
            PreferenceScreen account = findPreference("account");
            account.removePreference(changOfPassword);
        } else{
            changOfPassword.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(@NonNull Preference preference) {
                    CustomDialog customDialog = new CustomDialog();
                    customDialog.verificationDialog(getActivity());
                    return true;
                }
            });
        }

        signOut.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                CustomDialog customDialog = new CustomDialog();
                customDialog.signOutDialog(getActivity());
                return true;
            }
        });
    }
}