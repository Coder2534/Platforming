package com.android.platforming.fragment;

import static com.android.platforming.object.FirestoreManager.getFirebaseAuth;

import android.app.AlertDialog;
import android.content.Context;
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
import androidx.fragment.app.FragmentManager;

import com.android.platforming.object.CustomDialog;
import com.android.platforming.activity.MainActivity;
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
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        setGoogle(view.findViewById(R.id.google_signIn), "Google로 계속하기");
        setFacebook(view);
        SetListener(view);

        return view;
    }

    //리스너 설정
    private void SetListener(View view){
        Button confirm = view.findViewById(R.id.confirm_signIn);
        Button signOut = view.findViewById(R.id.signUp_signIn);
        Button findPassword = view.findViewById(R.id.findPassword_singIn);
        TextView email = view.findViewById(R.id.email_signIn);
        TextView password = view.findViewById(R.id.password_signIn);
        Switch autoSignIn = view.findViewById(R.id.autoSignIn_signIn);

        confirm.setOnClickListener(v -> SignInWithEmail(email.getText().toString(), password.getText().toString(), autoSignIn.isChecked()));
        signOut.setOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout_signIn, new SignUpFragment()).addToBackStack(null).commit());
        findPassword.setOnClickListener(v -> PasswordResetDialog(getContext(), getActivity().getSupportFragmentManager()));
    }

    //로그인(Email)
    private void SignInWithEmail(String email, String password, boolean autoSignIn){
        getFirebaseAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Log.w("SignInFragment", "signInWithEmailAndPassword Success");
                GetUserData();
            }else{
                Log.w("SignInFragment", "signInWithEmailAndPassword Error");
                CustomDialog.ErrorDialog(getContext(), "아이디가 없거나 비밀번호가 맞지 않습니다.");
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

    private void SignInWithGoogle(GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        getFirebaseAuth().signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Log.w("SignInActivity", "Google SignIn success");
                        GetUserData();
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
        LoginButton loginButton = view.findViewById(R.id.facebook_signIn);
        loginButton.setFragment(this);// If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                SignInWithFacebook(loginResult.getAccessToken());
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

    private void SignInWithFacebook(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        getFirebaseAuth().signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // 로그인 성공
                        Log.w("SignInActivity", "Facebook SignIn success");
                        GetUserData();
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
                SignInWithGoogle(account);
            }
            else{
                //구글 로그인 실패
                Log.w("Google", "signIn fail");
            }
        }

        //Facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //Dialog
    private void PasswordResetDialog(Context context, FragmentManager fragmentManager){
        final EditText editText = new EditText(context);

        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle("비밀번호 찾기");
        ad.setMessage("이메일을 입력해 주세요.");
        ad.setView(editText);
        ad.setPositiveButton("입력", (dialog, which) -> {
            String email= editText.getText().toString();

            PasswordResetEmail(context, fragmentManager, email);
        });
        ad.setNegativeButton("최소", (dialog, which) -> {

        });
        ad.show();
    }

    //비밀번호 재설정 이메일 전송
    private void PasswordResetEmail(Context context, FragmentManager fragmentManager, String email){

        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$");
        Matcher matcher = pattern.matcher(email);

        if(!matcher.find()){
            Log.w("EmailAlarmFragment", "email form Error");
            CustomDialog.ErrorDialog(context, "이메일이 유효하지 않습니다.");
        }

        getFirebaseAuth().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Log.w("EmailAlarmFragment", "sendPasswordResetEmail success");

                Bundle bundle = new Bundle();
                bundle.putString("Type", "findPassword");
                bundle.putString("Email", email);
                EmailAlarmFragment emailAlarmFragment = new EmailAlarmFragment();
                emailAlarmFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.fragmentLayout_signIn, emailAlarmFragment).addToBackStack(null).commit();
            }else{
                Log.w("EmailAlarmFragment", "sendPasswordResetEmail fail");
            }
        });
    }

    private void GetUserData(){

    }
}