package com.platforming.autonomy.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.platforming.autonomy.InitApplication;
import com.platforming.autonomy.clazz.ThemeManager;
import com.platforming.autonomy.fragment.SignInFragment;
import com.android.autonomy.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class SignActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InitApplication initApplication = ((InitApplication)getApplication());
        initApplication.refreshAppliedTheme();
        ThemeManager.TCall(this, initApplication);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.firebase_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        //mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        getSupportFragmentManager().beginTransaction().replace(R.id.cl_sign, new SignInFragment()).commit();
    }

    public GoogleApiClient getGoogleClient(){
        return mGoogleApiClient;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.cl_sign);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
