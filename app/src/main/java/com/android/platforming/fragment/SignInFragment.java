package com.android.platforming.fragment;

import static com.android.platforming.object.FirestoreManager.getFirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.platforming.interfaze.ListenerInterface;
import com.android.platforming.object.CustomDialog;
import com.android.platforming.activity.SignInActivity;
import com.example.platforming.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        setGoogle(view.findViewById(R.id.lbtn_signin_google), "Google로 계속하기");
        setFacebook(view);
        setListener(view);

        return view;
    }

    //리스너 설정
    private void setListener(View view){
        Button confirm = view.findViewById(R.id.btn_signin_confirm);
        Button signOut = view.findViewById(R.id.btn_signin_signup);
        Button findPassword = view.findViewById(R.id.btn_signin_findpassword);
        TextView email = view.findViewById(R.id.et_signin_email);
        TextView password = view.findViewById(R.id.et_signin_password);
        Switch autoSignIn = view.findViewById(R.id.autoSignIn_signIn);

        confirm.setOnClickListener(v -> signInWithEmail(email.getText().toString(), password.getText().toString(), autoSignIn.isChecked()));
        signOut.setOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cl_signin, new SignUpFragment()).addToBackStack(null).commit());
        findPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(getContext());
                CustomDialog customDialog = new CustomDialog();
                customDialog.passwordResetDialog(getContext(), new ListenerInterface() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onSuccess(String msg) {
                        String email = msg;

                        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$");
                        Matcher matcher = pattern.matcher(email);

                        if(!matcher.find()){
                            Log.w("EmailAlarmFragment", "email form Error");
                            customDialog.errorDialog(getContext(), "이메일이 유효하지 않습니다.");
                        }

                        getFirebaseAuth().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Log.w("EmailAlarmFragment", "sendPasswordResetEmail success");

                                Bundle bundle = new Bundle();
                                bundle.putString("Type", "findPassword");
                                bundle.putString("Email", email);
                                EmailAlarmFragment emailAlarmFragment = new EmailAlarmFragment();
                                emailAlarmFragment.setArguments(bundle);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cl_signin, emailAlarmFragment).addToBackStack(null).commit();
                            }else{
                                Log.w("EmailAlarmFragment", "sendPasswordResetEmail fail");
                            }
                        });
                    }

                    @Override
                    public void onFail() {

                    }
                });
            }
        });
    }

    //로그인(Email)
    private void signInWithEmail(String email, String password, boolean autoSignIn){
        getFirebaseAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Log.w("SignInFragment", "signInWithEmailAndPassword Success");
                getUserData();
            }else{
                Log.w("SignInFragment", "signInWithEmailAndPassword Error");
                CustomDialog customDialog = new CustomDialog();
                customDialog.errorDialog(getContext(), "아이디가 없거나 비밀번호가 맞지 않습니다.");
            }
        });
    }

    //로그인(Google)
    int RC_SIGN_IN = 9001;

    private void setGoogle(SignInButton signInButton, String buttonText) {
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                break;
            }
        }

        signInButton.setOnClickListener(v -> {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(((SignInActivity)getActivity()).getGoogleClient());
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    private void signInWithGoogle(GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        getFirebaseAuth().signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Log.w("SignInActivity", "Google SignIn success");
                        getUserData();
                    }else{
                        Log.w("SignInActivity", "Google SignIn fail");
                    }
                });
    }

    //로그인(Facebook)
    private CallbackManager callbackManager;
    private void setFacebook(View view){

        callbackManager = CallbackManager.Factory.create();

        //callbackManager
        LoginButton loginButton = view.findViewById(R.id.lbtn_signin_facebook);
        loginButton.setFragment(this);// If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                signInWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
                Log.w("SignInActivity", "Facebook SignIn onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.w("SignInActivity", "Facebook SignIn onError");
            }
        });
    }

    private void signInWithFacebook(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        getFirebaseAuth().signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // 로그인 성공
                        Log.w("SignInActivity", "Facebook SignIn success");
                        getUserData();
                    } else {
                        // 로그인 실패
                        Log.w("SignInActivity", "Facebook SignIn fail");
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.w("SignInActivity", "onActivityReuslt");
        //Google
        if (requestCode == RC_SIGN_IN) {
            Log.w("SignInActivity", "Google");
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.w("Result", result.getStatus().toString());
            if (result.isSuccess()) {
                Log.w("Google", "signIn success");
                //구글 로그인 성공해서 파베에 인증
                GoogleSignInAccount account = result.getSignInAccount();
                signInWithGoogle(account);
            }
            else{
                //구글 로그인 실패
                Log.w("Google", "signIn fail");
            }
        }

        //Facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getUserData(){

    }
}