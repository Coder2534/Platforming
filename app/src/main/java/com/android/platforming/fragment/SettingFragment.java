package com.android.platforming.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.platforming.adapter.RecyclerviewAdapter;
import com.example.platforming.R;

public class SettingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emailalarm, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_setting);

        RecyclerviewAdapter adapter = new RecyclerviewAdapter();
        for (int i = 0; i < 100; i++) {
            String str = i + "번째 아이템";
            adapter.setArrayData(str);
        }

        recyclerView.setAdapter(adapter);

        return view;
    }
}
