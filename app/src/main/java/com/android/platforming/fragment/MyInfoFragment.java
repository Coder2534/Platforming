package com.android.platforming.fragment;

import static com.android.platforming.clazz.User.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.media.Image;
import android.os.Bundle;
import android.text.InputFilter;
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
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

import java.util.HashMap;
import java.util.Map;

public class MyInfoFragment extends Fragment {
    FirestoreManager firestoreManager;
    TextView tv_myinfo_name,tv_myinfo_email,tv_myinfo_point,tv_myinfo_rivise;
    ImageButton ibtn_myinfo_profile, ibtn_myinfo_rivise;
    EditText et_myinfo_nickname,et_myinfo_class,et_myinfo_phonenumber;
    Button btn_myinfo_finish;

    AlertDialog dialog;
    private void setDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.customdialog_profile, null);

        ImageButton profile1 = view.findViewById(R.id.ibtn_profile_1);
        profile1.setImageResource(User.getProfiles().get(0));
        profile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ibtn_myinfo_profile.setImageResource(User.getProfiles().get(0));
                dialog.dismiss();
            }
        });

        ImageButton profile2 = view.findViewById(R.id.ibtn_profile_2);
        profile2.setImageResource(User.getProfiles().get(1));
        profile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ibtn_myinfo_profile.setImageResource(User.getProfiles().get(1));
                dialog.dismiss();
            }
        });

        ImageButton profile3 = view.findViewById(R.id.ibtn_profile_3);
        profile3.setImageResource(User.getProfiles().get(2));
        profile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ibtn_myinfo_profile.setImageResource(User.getProfiles().get(2));
                dialog.dismiss();
            }
        });

        builder.setView(view);

        dialog = builder.create();
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

        tv_myinfo_name.setText(user.getUsername());
        et_myinfo_nickname.setText(user.getNickname());
        et_myinfo_class.setText(user.getGrade());
        tv_myinfo_email.setText(FirestoreManager.getFirebaseAuth().getCurrentUser().getEmail());
        tv_myinfo_point.setText(user.getPoint() + "p");
        et_myinfo_phonenumber.setText(user.getTelephone());
        ibtn_myinfo_profile.setImageResource(user.getProfile());

        et_myinfo_nickname.setClickable(false);
        et_myinfo_nickname.setFocusable(false);
        et_myinfo_phonenumber.setClickable(false);
        et_myinfo_phonenumber.setFocusable(false);
        et_myinfo_class.setClickable(false);
        et_myinfo_class.setFocusable(false);

        ibtn_myinfo_rivise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            tv_myinfo_rivise.setText("수정중");
            btn_myinfo_finish.setVisibility(View.VISIBLE);
            ibtn_myinfo_profile.setClickable(true);
            et_myinfo_nickname.setFocusableInTouchMode(true);
            et_myinfo_nickname.setFocusable(true);
            et_myinfo_class.setFocusableInTouchMode(true);
            et_myinfo_class.setFocusable(true);
            et_myinfo_class.setFilters(new InputFilter[] {new InputFilter.LengthFilter(16)});
            et_myinfo_phonenumber.setFocusableInTouchMode(true);
            et_myinfo_phonenumber.setFocusable(true);
            et_myinfo_phonenumber.setFilters(new InputFilter[] {new InputFilter.LengthFilter(11)});
            ibtn_myinfo_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setDialog(getActivity());
                }
            });

            btn_myinfo_finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btn_myinfo_finish.setVisibility(View.GONE);
                    tv_myinfo_rivise.setText("");
                    ibtn_myinfo_profile.setClickable(false);
                    et_myinfo_nickname.setClickable(false);
                    et_myinfo_nickname.setFocusable(false);
                    et_myinfo_phonenumber.setClickable(false);
                    et_myinfo_phonenumber.setFocusable(false);
                    et_myinfo_class.setClickable(false);
                    et_myinfo_class.setFocusable(false);

                    Map<String,Object> MyinfoData = new HashMap<>();

                    MyinfoData.put("nickname",et_myinfo_nickname.getText().toString());
                    MyinfoData.put("grade",et_myinfo_class.getText().toString());
                    MyinfoData.put("telephone",et_myinfo_phonenumber.getText().toString());

                    firestoreManager.updateUserData(MyinfoData, new ListenerInterface() {
                        @Override
                        public void onSuccess() {
                            ListenerInterface.super.onSuccess();
                        }
                    });
                }
            });
            }
        });

        return view;

    }

}
