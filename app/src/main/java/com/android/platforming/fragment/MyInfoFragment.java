package com.android.platforming.fragment;

import android.app.Dialog;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.User;
import com.example.platforming.R;

public class MyInfoFragment extends Fragment {
    FirestoreManager firestoreManager;
    TextView tv_myinfo_name,tv_myinfo_email,tv_myinfo_point,tv_myinfo_rivise;
    ImageButton ibtn_myinfo_profile,ibtn_myinfo_rivise,ibtn_profile_1,ibtn_profile_2,ibtn_profile_3;
    EditText et_myinfo_nickname,et_myinfo_class,et_myinfo_phonenumber;
    Button btn_myinfo_finish;

    Dialog dialog;
    private void setDialog() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.customdialog_profile);
        dialog.show();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_myinfo, container, false);

        firestoreManager = new FirestoreManager();
        tv_myinfo_name = view.findViewById(R.id.tv_myinfo_name);
        tv_myinfo_email = view.findViewById(R.id.tv_myinfo_email);
        tv_myinfo_point = view.findViewById(R.id.tv_myinfo_point);
        tv_myinfo_rivise = view.findViewById(R.id.tv_myinfo_revise);
        ibtn_myinfo_profile = view.findViewById(R.id.ibtn_myinfo_profile);
        ibtn_myinfo_rivise = view.findViewById(R.id.ibtn_myinfo_rivise);
        btn_myinfo_finish = view.findViewById(R.id.btn_myinfo_finish);
        et_myinfo_nickname = view.findViewById(R.id.et_myinfo_nickname);
        et_myinfo_class = view.findViewById(R.id.et_myinfo_class);
        et_myinfo_phonenumber = view.findViewById(R.id.et_myinfo_phonenumber);

        tv_myinfo_name.setText(User.getUser().getUsername());
        tv_myinfo_email.setText(FirestoreManager.getFirebaseAuth().getCurrentUser().getEmail());
        tv_myinfo_point.setText(Integer.toString(User.getUser().getPoint()));

        et_myinfo_nickname.setEnabled(false);
        et_myinfo_phonenumber.setEnabled(false);
        et_myinfo_class.setEnabled(false);

        ibtn_myinfo_rivise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            tv_myinfo_rivise.setText("수정가능");
            btn_myinfo_finish.setVisibility(View.VISIBLE);
            ibtn_myinfo_profile.setEnabled(true);
            et_myinfo_nickname.setEnabled(true);
            et_myinfo_class.setEnabled(true);
            et_myinfo_phonenumber.setEnabled(true);
                ibtn_myinfo_profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setDialog();
                        ibtn_profile_1 = dialog.findViewById(R.id.ibtn_profile_1);
                        ibtn_profile_2 = dialog.findViewById(R.id.ibtn_profile_2);
                        ibtn_profile_3 = dialog.findViewById(R.id.ibtn_profile_3);

                        ibtn_profile_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ibtn_myinfo_profile.setImageResource(R.drawable.ic_baseline_10mp_24);
                                dialog.dismiss();
                            }
                        });
                        ibtn_profile_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ibtn_myinfo_profile.setImageResource(R.drawable.ic_baseline_11mp_24);
                                dialog.dismiss();
                            }
                        });
                        ibtn_profile_3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ibtn_myinfo_profile.setImageResource(R.drawable.ic_baseline_12mp_24);
                                dialog.dismiss();
                            }
                        });
                    }
                });

                btn_myinfo_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_myinfo_finish.setVisibility(View.GONE);
                        tv_myinfo_rivise.setText("");
                        ibtn_myinfo_profile.setEnabled(false);
                        et_myinfo_nickname.setEnabled(false);
                        et_myinfo_class.setEnabled(false);
                        et_myinfo_phonenumber.setEnabled(false);

                    }
                });
            }
        });

        return view;

    }

}
