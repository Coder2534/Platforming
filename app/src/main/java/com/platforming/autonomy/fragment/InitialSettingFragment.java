package com.platforming.autonomy.fragment;

import static com.platforming.autonomy.clazz.FirestoreManager.getFirebaseAuth;

import static com.platforming.autonomy.clazz.User.user;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.platforming.autonomy.activity.MainActivity;
import com.platforming.autonomy.adapter.ImageSliderAdapter;
import com.platforming.autonomy.clazz.CustomDialog;
import com.platforming.autonomy.clazz.WordFilter;
import com.platforming.autonomy.interfaze.ListenerInterface;
import com.platforming.autonomy.clazz.FirestoreManager;
import com.platforming.autonomy.clazz.User;
import com.platforming.autonomy.view.ImageSlider;
import com.android.autonomy.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitialSettingFragment extends Fragment {
    ImageSlider imageSlider;

    private final static ArrayList<Integer> profiles = new ArrayList<Integer>(){{
        add(R.drawable.profile_dog);
        add(R.drawable.profile_cat);
        add(R.drawable.profile_rabbit);
    }};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_initialsetting, container, false);
        setListener(view);

        imageSlider = new ImageSlider(view.getContext(), view.findViewById(R.id.vp_initialsetting_profile), view.findViewById(R.id.layoutIndicators));
        imageSlider.setAdapter(new ImageSliderAdapter(profiles));
        imageSlider.setIndicators(User.getProfiles().size());

        return view;
    }

    private void setListener(View view){
        Button confirm = view.findViewById(R.id.btn_initialsetting);

        confirm.setOnClickListener(v -> {
            confirm.setClickable(false);
            CustomDialog customDialog = new CustomDialog();

            Map<String, Object> data = new HashMap<>();

            //이름
            String userName = ((EditText)view.findViewById(R.id.et_initialsetting_username)).getText().toString();
            if(userName.equals("") && !userName.matches("^[a-zA-Z0-9ㄱ-ㅎ가-힣]+$")){
                customDialog.messageDialog(getActivity(), "옳지 않은 이름입니다.");
                confirm.setClickable(true);
                return;
            }
            data.put("username", userName);

            //별명
            String nickName = ((EditText)view.findViewById(R.id.et_initialsetting_nickname)).getText().toString();
            if(nickName.equals("") && !nickName.matches("^[a-zA-Z0-9ㄱ-ㅎ가-힣]+$")){
                customDialog.messageDialog(getActivity(), "옳지 않은 별명입니다.");
                confirm.setClickable(true);
                return;
            }
            WordFilter wordFilter = new WordFilter();
            if(wordFilter.isForbiddenWords(nickName)){
                customDialog.messageDialog(getActivity(), "금지어가 포함되어있습니다.\n" + wordFilter.getFilteredForbiddenWords().toString());
                confirm.setClickable(true);
                return;
            }
            data.put("nickname", nickName);

            //전화번호
            String telephone = ((EditText)view.findViewById(R.id.et_initialsetting_telephone)).getText().toString();
            data.put("telephone", telephone);

            //성별
            boolean isMale = ((RadioButton)view.findViewById(R.id.rbtn_initialsetting_male)).isChecked();
            boolean isFemale = ((RadioButton)view.findViewById(R.id.rbtn_initialsetting_female)).isChecked();
            if(!isMale && !isFemale){
                confirm.setClickable(true);
                return;
            }
            if(isMale)
                data.put("sex", 0);
            else
                data.put("sex", 1);

            //학번
            String studentId = ((EditText)view.findViewById(R.id.et_initialsetting_studentid)).getText().toString();
            if (studentId.length() != 5){
                confirm.setClickable(true);
                customDialog.messageDialog(getActivity(),"학번 5자리를 정확히 입력해주세요.");
                return;
            }
            else {
                int grade = Integer.parseInt(String.valueOf(studentId.charAt(0)));
                int clazz = Integer.parseInt(studentId.substring(1, 3));
                int number = Integer.parseInt(studentId.substring(3, 5));
                if(grade < 0 || grade > 3 || clazz < 0 || clazz > 10 || number < 0 || number > 30){
                    confirm.setClickable(true);
                    customDialog.messageDialog(getActivity(),"옳지 않은 학번입니다.");
                    return;
                }
            }

            //데이터 입력
            data.put("ban", false);

            data.put("studentId", studentId);

            data.put("profileIndex", imageSlider.getPosition());

            data.put("point", 0);

            List<Long> list_design = Collections.singletonList(0L);
            data.put("themes", list_design);
            data.put("fonts", list_design);

            data.put("myPostIds", new ArrayList<String>());

            data.put("lastSignIn", 0);
            data.put("point_receipt", 0);
            data.put("dailyTasks", Arrays.asList(0L, 0L, 0L, 0L));

            FirestoreManager firestoreManager = new FirestoreManager();
            firestoreManager.writeUserData(data, new ListenerInterface() {
                @Override
                public void onSuccess() {
                    user = new User(getFirebaseAuth().getCurrentUser().getUid(), getFirebaseAuth().getCurrentUser().getEmail(), data);
                    ((MainActivity)getActivity()).setting();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cl_main, new MainPageFragment()).commit();
                }
            });

        });
    }
}
