package com.android.platforming.fragment;

import static com.android.platforming.clazz.FirestoreManager.getFirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.platforming.activity.MainActivity;
import com.android.platforming.interfaze.ListenerInterface;
import com.android.platforming.clazz.CustomDialog;
import com.android.platforming.activity.SignActivity;
import com.android.platforming.clazz.FirestoreManager;
import com.example.platforming.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

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

        confirm.setOnClickListener(v -> signInWithEmail(email.getText().toString(), password.getText().toString()));
        signOut.setOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cl_sign, new SignUpFragment()).addToBackStack(null).commit());
        findPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog = new CustomDialog();
                customDialog.passwordResetDialog(getActivity(), new ListenerInterface() {
                    @Override
                    public void onSuccess(String msg) {
                        String email = msg;
                        getFirebaseAuth().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Log.w("EmailAlarmFragment", "sendPasswordResetEmail success");


                                Bundle bundle = new Bundle();
                                bundle.putString("Type", "findPassword");
                                bundle.putString("Email", email);
                                EmailAlarmFragment emailAlarmFragment = new EmailAlarmFragment();
                                emailAlarmFragment.setArguments(bundle);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cl_sign, emailAlarmFragment).addToBackStack(null).commit();
                            }else{
                                Log.w("EmailAlarmFragment", "sendPasswordResetEmail fail");
                                customDialog.messageDialog(getActivity(),"실패했습니다.");
                            }
                        });
                    }
                });
            }
        });
    }

    //로그인(Email)
    private void signInWithEmail(String email, String password){
        if(email.isEmpty() || password.isEmpty())
            return;

        getFirebaseAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                readData();
            }else{
                CustomDialog customDialog = new CustomDialog();
                try{
                    throw task.getException();
                } catch(FirebaseAuthInvalidCredentialsException e) {
                    customDialog.messageDialog(getActivity(), "입력하신 정보가 유효하지 않습니다.");
                } catch(Exception e) {
                    customDialog.messageDialog(getActivity(), "로그인 중 오류가 발생했습니다.");
                }
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
            CustomDialog customDialog = new CustomDialog();
            customDialog.schoolCodeDialog(getActivity(), new ListenerInterface() {
                @Override
                public void onSuccess() {
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(((SignActivity)getActivity()).getGoogleClient());
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                }
            });
        });
    }

    private void signInWithGoogle(GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        getFirebaseAuth().signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    Log.w("SignInActivity", "Google SignIn with google");
                    if(task.isSuccessful()){
                        Log.w("SignInActivity", "Google SignIn success");
                        readData();
                    }else{
                        LoginManager.getInstance().logOut();
                        CustomDialog customDialog = new CustomDialog();
                        try{
                            throw task.getException();
                        } catch(FirebaseAuthUserCollisionException e) {
                            customDialog.messageDialog(getActivity(), "이미 사용중인 이메일 입니다.");
                        } catch(Exception e) {
                            customDialog.messageDialog(getActivity(), "로그인 중 오류가 발생했습니다.");
                        }
                    }
                });
    }

    //로그인(Facebook)
    private CallbackManager callbackManager;
    Button loginButton_facebook;
    private void setFacebook(View view){

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        signInWithFacebook(loginResult.getAccessToken());
                        Log.w("SignInActivity", "Facebook SignIn onSuccess");
                    }

                    @Override
                    public void onCancel() {
                        Log.w("SignInActivity", "Facebook SignIn onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.w("SignInActivity", "Facebook SignIn onError");
                    }
                });


        loginButton_facebook = view.findViewById(R.id.lbtn_signin_facebook);
        loginButton_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LoginManager - 요청된 읽기 또는 게시 권한으로 로그인 절차를 시작합니다.
                CustomDialog customDialog = new CustomDialog();
                customDialog.schoolCodeDialog(getActivity(), new ListenerInterface() {
                    @Override
                    public void onSuccess() {
                        LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("email", "public_profile"));
                    }
                });
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
                        readData();
                    } else {
                        LoginManager.getInstance().logOut();
                        CustomDialog customDialog = new CustomDialog();
                        try{
                            throw task.getException();
                        } catch(FirebaseAuthUserCollisionException e) {
                            customDialog.messageDialog(getActivity(), "이미 사용중인 이메일 입니다.");
                        } catch(Exception e) {
                            customDialog.messageDialog(getActivity(), "로그인 중 오류가 발생했습니다.");
                        }
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
                Log.w("Google", "signIn fail: " + result.getStatus());
            }
        }

        //Facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void readData(){
        FirestoreManager firestoreManager = new FirestoreManager();
        firestoreManager.readUserData(new ListenerInterface() {

            @Override
            public void onSuccess() {
                Intent mainIntent = new Intent(getContext(), MainActivity.class);
                startActivity(mainIntent);
                getActivity().finish();
            }
        });
    }
}