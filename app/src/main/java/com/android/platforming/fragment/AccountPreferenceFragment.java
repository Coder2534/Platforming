package com.android.platforming.fragment;

import static com.android.platforming.clazz.FirestoreManager.getFirebaseAuth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.android.platforming.view.TimePreference;
import com.android.platforming.view.TimePreferenceCompat;

public class AccountPreferenceFragment extends PreferenceFragmentCompat {

    SharedPreferences pref;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        pref = PreferenceManager.getDefaultSharedPreferences(requireActivity());

        PreferenceScreen uid = findPreference("uid");
        uid.setSummary(User.getUser().getUid());

        PreferenceScreen email = findPreference("email");
        email.setSummary(User.getUser().getEmail());
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
            if(key.equals("signOut")) {
                getFirebaseAuth().signOut();
                Intent loginIntent = new Intent(getContext(), SignInActivity.class);
                startActivity(loginIntent);
                getActivity().finish();
            }
        }
    };
}