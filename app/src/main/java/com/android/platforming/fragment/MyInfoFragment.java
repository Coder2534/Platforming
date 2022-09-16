package com.android.platforming.fragment;

import static com.android.platforming.clazz.User.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.platforming.activity.MainActivity;
import com.android.platforming.clazz.CustomDialog;
import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.User;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

import java.util.HashMap;
import java.util.Map;

public class MyInfoFragment extends Fragment {
    FirestoreManager firestoreManager;
    TextView tv_myinfo_email,tv_myinfo_point,tv_myinfo_rivise;
    ImageButton ibtn_myinfo_profile;
    EditText et_myinfo_username, et_myinfo_nickname,et_myinfo_class,et_myinfo_phonenumber;
    Button btn_myinfo_finish,btn_myinfo_rivise;

    String studentId;
    long profileIndex;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myinfo, container, false);

        ((MainActivity)getActivity()).setTitle("내 정보");

        firestoreManager = new FirestoreManager();
        et_myinfo_username = view.findViewById(R.id.tv_myinfo_uesrname);
        tv_myinfo_email = view.findViewById(R.id.tv_myinfo_email);
        tv_myinfo_point = view.findViewById(R.id.tv_myinfo_point);
        tv_myinfo_rivise = view.findViewById(R.id.tv_myinfo_revise);
        ibtn_myinfo_profile = view.findViewById(R.id.ibtn_myinfo_profile);
        btn_myinfo_rivise = view.findViewById(R.id.btn_myinfo_rivise);
        btn_myinfo_finish = view.findViewById(R.id.btn_myinfo_finish);
        et_myinfo_nickname = view.findViewById(R.id.et_myinfo_nickname);
        et_myinfo_class = view.findViewById(R.id.et_myinfo_class);
        et_myinfo_phonenumber = view.findViewById(R.id.et_myinfo_phonenumber);

        et_myinfo_username.setText(user.getUsername());
        tv_myinfo_point.setText(user.getPoint() + "p");
        et_myinfo_nickname.setText(user.getNickname());
        studentId = user.getStudentId();
        et_myinfo_class.setText(String.format("%c학년 %s반 %s번", studentId.charAt(0), studentId.substring(1, 3).replaceFirst("^0+(?!$)", ""), studentId.substring(3, 5).replaceFirst("^0+(?!$)", "")));
        tv_myinfo_email.setText(FirestoreManager.getFirebaseAuth().getCurrentUser().getEmail());

        et_myinfo_phonenumber.setText(user.getTelephone());
        ibtn_myinfo_profile.setImageResource(user.getProfile());

        et_myinfo_nickname.setClickable(false);
        et_myinfo_nickname.setCursorVisible(false);
        et_myinfo_phonenumber.setClickable(false);
        et_myinfo_phonenumber.setCursorVisible(false);
        et_myinfo_class.setClickable(false);
        et_myinfo_class.setCursorVisible(false);

        btn_myinfo_rivise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                et_myinfo_class.setText(studentId);
                tv_myinfo_rivise.setVisibility(View.VISIBLE);
                btn_myinfo_finish.setVisibility(View.VISIBLE);
                ibtn_myinfo_profile.setClickable(true);
                et_myinfo_username.setCursorVisible(true);
                et_myinfo_nickname.setCursorVisible(true);
                et_myinfo_class.setCursorVisible(true);
                et_myinfo_class.setFilters(new InputFilter[] {new InputFilter.LengthFilter(5)});
                et_myinfo_phonenumber.setCursorVisible(true);
                et_myinfo_phonenumber.setFilters(new InputFilter[] {new InputFilter.LengthFilter(11)});
            }
        });

        ibtn_myinfo_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog(getActivity());
            }
        });

        btn_myinfo_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog();

                //이름
                String userName = et_myinfo_username.getText().toString();
                if(userName.equals("") && !userName.matches("^[a-zA-Z0-9ㄱ-ㅎ가-힣]+$")){
                    customDialog.messageDialog(getActivity(), "옳지 않은 이름입니다.");
                    return;
                }

                //별명
                String nickName = et_myinfo_nickname.getText().toString();
                if(nickName.equals("") && !nickName.matches("^[a-zA-Z0-9ㄱ-ㅎ가-힣]+$")){
                    customDialog.messageDialog(getActivity(), "옳지 않은 별명입니다.");
                    return;
                }

                //학번
                studentId = et_myinfo_class.getText().toString();
                if (studentId.length() != 5){
                    customDialog.messageDialog(getActivity(),"학번 5자리를 정확히 입력해주세요.");
                    return;
                }
                else {
                    int grade = Integer.parseInt(String.valueOf(studentId.charAt(0)));
                    int clazz = Integer.parseInt(studentId.substring(1, 3));
                    int number = Integer.parseInt(studentId.substring(3, 5));
                    if(grade < 0 || grade > 3 || clazz < 0 || clazz > 10 || number < 0 || number > 30){
                        customDialog.messageDialog(getActivity(),"옳지 않은 학번입니다.");
                        return;
                    }
                }

                Map<String,Object> myInfoData = new HashMap<>();

                myInfoData.put("profileIndex",profileIndex);
                myInfoData.put("username",et_myinfo_username.getText().toString());
                myInfoData.put("nickname",et_myinfo_nickname.getText().toString());
                myInfoData.put("studentId",et_myinfo_class.getText().toString());
                myInfoData.put("telephone",et_myinfo_phonenumber.getText().toString());

                firestoreManager.updateUserData(myInfoData, new ListenerInterface() {
                    @Override
                    public void onSuccess() {
                        et_myinfo_class.setFilters(new InputFilter[] {new InputFilter.LengthFilter(11)});
                        et_myinfo_class.setText(String.format("%c학년 %s반 %s번", studentId.charAt(0), studentId.substring(1, 3), studentId.substring(3, 5))); //.replaceFirst("^0+(?!$)", "")
                        user.setNickName(et_myinfo_nickname.getText().toString());
                        user.setStudentId(studentId);
                        user.setTelephone(et_myinfo_phonenumber.getText().toString());
                        user.setProfileIndex(Math.toIntExact(profileIndex));
                        ((MainActivity)getActivity()).setHeader();

                        btn_myinfo_finish.setVisibility(View.GONE);
                        tv_myinfo_rivise.setVisibility(View.GONE);
                        ibtn_myinfo_profile.setClickable(false);
                        et_myinfo_username.setCursorVisible(false);
                        et_myinfo_nickname.setClickable(false);
                        et_myinfo_nickname.setCursorVisible(false);
                        et_myinfo_phonenumber.setClickable(false);
                        et_myinfo_phonenumber.setCursorVisible(false);
                        et_myinfo_class.setClickable(false);
                        et_myinfo_class.setCursorVisible(false);
                    }
                });
            }
        });
        return view;

    }
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
                profileIndex = 0;
                dialog.dismiss();
            }
        });

        ImageButton profile2 = view.findViewById(R.id.ibtn_profile_2);
        profile2.setImageResource(User.getProfiles().get(1));
        profile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ibtn_myinfo_profile.setImageResource(User.getProfiles().get(1));
                profileIndex = 1;
                dialog.dismiss();
            }
        });

        ImageButton profile3 = view.findViewById(R.id.ibtn_profile_3);
        profile3.setImageResource(User.getProfiles().get(2));
        profile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ibtn_myinfo_profile.setImageResource(User.getProfiles().get(2));
                profileIndex = 2;
                dialog.dismiss();
            }
        });

        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }


}
