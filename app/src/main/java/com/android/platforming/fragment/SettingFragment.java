package com.android.platforming.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.platforming.adapter.RecyclerviewAdapter;
import com.android.platforming.clazz.Setting;
import com.example.platforming.R;

import java.util.ArrayList;

public class SettingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_setting);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Setting> settingList = new ArrayList<>();
        settingList.add(new Setting(R.drawable.bg_indicator_active, "알림", "자가진단, 오늘의 급식, 공지, 게시물"));
        settingList.add(new Setting(R.drawable.bg_indicator_active, "디자인", "테마, 폰트"));
        settingList.add(new Setting(R.drawable.bg_indicator_active, "계정", "이메일, 비밀번호 변경, 로그아웃"));
        settingList.add(new Setting(R.drawable.bg_indicator_active, "버전 정보", "현재 애플리케이션의 버전"));

        RecyclerviewAdapter adapter = new RecyclerviewAdapter(settingList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
