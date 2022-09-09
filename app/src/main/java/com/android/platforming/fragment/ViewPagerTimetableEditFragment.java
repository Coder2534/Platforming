package com.android.platforming.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.platforming.R;

public class ViewPagerTimetableEditFragment extends Fragment {


    String dayOfWeek;
    public ViewPagerTimetableEditFragment(String dayOfWeek){
        this.dayOfWeek = dayOfWeek;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_schedule_edit, container, false);

        TextView tv_dayOfWeek = view.findViewById(R.id.tv_schedule_edit_dayofweek);
        tv_dayOfWeek.setText(dayOfWeek);

        return view;
    }
}
