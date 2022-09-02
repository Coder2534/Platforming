package com.android.platforming.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.platforming.clazz.JsonParser;
import com.android.platforming.clazz.SchoolApi;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class SchoolScheduleFragment extends Fragment {
    SchoolApi api;

    ListenerInterface listenerInterface;

    ArrayAdapter<String> adapter;
    ArrayList data = new ArrayList();

    TextView tv_diary_date;
    ListView lv_plan;
    CalendarView calendarview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schoolschedule, container, false);
        tv_diary_date = view.findViewById(R.id.tv_diary_date);
        calendarview = view.findViewById(R.id.calendarview);
        lv_plan = view.findViewById(R.id.lv_plan);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, data);
        lv_plan.setAdapter(adapter);

        listenerInterface = new ListenerInterface() {
            @Override
            public void onSuccess() {
                data.clear();
                data.addAll(api.getResult());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };

        Calendar calendar = Calendar.getInstance();
        api = new SchoolApi();
        try {
            api.getSchoolSchedule(calendar.get(Calendar.DAY_OF_WEEK), listenerInterface);
            tv_diary_date.setText(api.getDate() + api.getDayOfWeek());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        calendarview.setDate(calendar.getTimeInMillis());
        calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                api = new SchoolApi();
                try {
                    api.getSchoolSchedule( Integer.toString(year),Integer.toString(month + 1), Integer.toString(dayOfMonth), calendar.get(Calendar.DAY_OF_WEEK), listenerInterface);
                    tv_diary_date.setText(api.getDate() + api.getDayOfWeek());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}



