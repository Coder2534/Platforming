package com.example.platforming;

import androidx.fragment.app.FragmentManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Variable {
    public static ArrayList<String> accessCode = new ArrayList<String>(){{
        add("1");
    }};
    public static FirebaseAuth firebaseAuth;
}
