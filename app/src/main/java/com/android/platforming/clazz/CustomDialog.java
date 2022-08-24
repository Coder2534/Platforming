package com.android.platforming.clazz;

import static com.android.platforming.clazz.FirestoreManager.getFirebaseAuth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.android.platforming.activity.MainActivity;
import com.android.platforming.activity.SignInActivity;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;

import java.util.zip.Inflater;

public class CustomDialog {
    AlertDialog dialog;

    public void errorDialog(Context context, String message){
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setIcon(R.mipmap.ic_launcher);
        ad.setMessage(message);

        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ad.show();
    }

    public void passwordResetDialog(Context context, ListenerInterface listenerInterface){
        final EditText editText = new EditText(context);

        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle("비밀번호 찾기");
        ad.setMessage("이메일을 입력해 주세요.");
        ad.setView(editText);
        ad.setPositiveButton("입력", (dialog, which) -> {
            String email= editText.getText().toString();
            listenerInterface.onSuccess(email);
        });
        ad.setNegativeButton("최소", (dialog, which) -> {
            listenerInterface.onFail();
        });
        ad.show();
    }

    public void signOutDialog(Activity activity){
        AlertDialog.Builder ad = new AlertDialog.Builder(activity);
        ad.setTitle("로그아웃");
        ad.setMessage("계속하시겠습니까?");
        ad.setPositiveButton("확인", (dialog, which) -> {
            getFirebaseAuth().signOut();
            Intent loginIntent = new Intent(activity, SignInActivity.class);
            activity.startActivity(loginIntent);
            activity.finish();
            MainActivity.getActivity().finish();
        });
        ad.setNegativeButton("최소", (dialog, which) -> {
            dialog.dismiss();
        });
        ad.show();
    }

    public void changeOfPasswordDialog(Activity activity){
        EditText editText = new EditText(activity);

        AlertDialog.Builder ad = new AlertDialog.Builder(activity);
        ad.setTitle("비밀번호 변경");
        ad.setView(editText);
        ad.setPositiveButton("확인", (dialog, which) -> {
            String password = editText.getText().toString();
            FirebaseUser user = getFirebaseAuth().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(User.getUser().getEmail(), password);
            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    dialog.dismiss();
                    newPasswordDialog(activity, user);
                }
                else{
                    dialog.dismiss();
                    errorDialog(activity, "인증에 실패했습니다.");
                }
            });
        });
        ad.setNegativeButton("취소", (dialog, which) -> {
            dialog.dismiss();
        });

        ad.show();
    }

    private void newPasswordDialog(Activity activity, FirebaseUser user){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("비밀번호 변경");

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_setting_newpassword, null);

        Button cancel = view.findViewById(R.id.btn_newpassword_cancel);
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        EditText newpassword = view.findViewById(R.id.et_newpassword_newpassword);
        EditText passwordcheck = view.findViewById(R.id.et_newpassword_passwordcheck);

        Button change = view.findViewById(R.id.btn_newpassword_change);
        change.setOnClickListener(v -> {
            String newPassword = newpassword.getText().toString();
            if(newPassword.equals(passwordcheck.getText().toString())){
                user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("CustomDialog", "Password updated");
                        } else {
                            Log.d("CustomDialog", "Error password not updated");
                        }
                    }
                });
            }
        });

        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }
}