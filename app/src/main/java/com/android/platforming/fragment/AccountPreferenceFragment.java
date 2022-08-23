package com.android.platforming.fragment;

import static com.android.platforming.clazz.FirestoreManager.getFirebaseAuth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;

import com.android.platforming.activity.SignInActivity;
import com.android.platforming.clazz.User;
import com.android.platforming.view.PasswordPreference;
import com.android.platforming.view.TimePreference;
import com.android.platforming.view.TimePreferenceCompat;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;

public class AccountPreferenceFragment extends PreferenceFragmentCompat {

    SharedPreferences pref;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        pref = PreferenceManager.getDefaultSharedPreferences(requireActivity());

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
            }
        else{
            changOfPassword.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(@NonNull Preference preference) {

                    return true;
                }
            });
        }

        signOut.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {

                return true;
            }
        });
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
            if(key.equals("changOfPassword")){

            }
            else if(key.equals("signOut")) {
                getFirebaseAuth().signOut();
                Intent loginIntent = new Intent(getContext(), SignInActivity.class);
                startActivity(loginIntent);
                getActivity().finish();
            }
        }
    };
}