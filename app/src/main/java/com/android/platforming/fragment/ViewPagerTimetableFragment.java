package com.android.platforming.fragment;

import static com.android.platforming.clazz.User.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.platforming.adapter.TableAdapter;
import com.android.platforming.clazz.CustomDialog;
import com.android.platforming.clazz.TableItem;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

import java.util.ArrayList;

public class ViewPagerTimetableFragment extends Fragment {

    GridView timetable;
    TableAdapter tableAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager_timetable, container, false);

        timetable = view.findViewById(R.id.gv_timetable);

        tableAdapter = new TableAdapter(user.getSchedules());

        timetable.setAdapter(tableAdapter);

        ImageButton expand = view.findViewById(R.id.btn_timetable_expand);
        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog = new CustomDialog();
                customDialog.expandSchedule(getActivity(), tableAdapter);
            }
        });

        ImageButton edit = view.findViewById(R.id.btn_timetable_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog = new CustomDialog();
                customDialog.editSchedule(getActivity(), new ListenerInterface() {
                    @Override
                    public void onSuccess() {
                        tableAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        return view;
    }
}
