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
import com.example.platforming.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class SchoolScheduleFragment extends Fragment {
    private SchoolApi api;
    private JsonParser jsonParser;
    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
    String y = dateFormat.format(date);
    SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM");
    String m = dateFormat1.format(date);
    SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd");
    String d = dateFormat2.format(date);
    Calendar minDate = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();

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

        api = new SchoolApi();



        try {
            api.getSchoolSchedule();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setCalendarviewDate();
        showPlanCalendarDate();
        showSchedule();
        long now = System.currentTimeMillis();
        calendarview.setDate(now);


        calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Log.d("chenk","ok");
                y = String.valueOf(year);
                m = String.valueOf(month+1);
                d = String.valueOf(dayOfMonth);
                showPlanDate(y+"년"+m+"월"+d+"일  ");
                Log.d("check_selected",y+"년"+m+"월"+d+"일  ");
                getScheduleResult();
                showSchedule();
            }
        });
        return view;
    }
    public void showPlanDate(String time) {
        String dayOfWeek = getDayOfWeek();
        Log.d("checkday3", dayOfWeek);
        tv_diary_date.setText(time+dayOfWeek);
    }

    public void showPlanCalendarDate() {

        y = dateFormat.format(calendarview.getDate());
        m = dateFormat1.format(calendarview.getDate());
        d =  dateFormat2.format(calendarview.getDate());
        String dayOfWeek = getDayOfWeek();
        set0time();
        tv_diary_date.setText(y+"년"+m+"월"+d+"일  "+dayOfWeek);
    }
    public void showSchedule(){
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, data);
        lv_plan.setAdapter(adapter);
        data.clear();
        data.addAll(api.getScheduleResult());
        adapter.notifyDataSetChanged();
    }

    public void setCalendarviewDate() {
        set0time();
        calendarview.setDate(Long.parseLong(y + m + d));
    }
    public void set0time(){
        if(Integer.parseInt(m) < 10)
            m = "0" + m;
        if(Integer.parseInt(d) < 10)
            d = "0" + d;
    }

    public void getScheduleResult() {
        api = new SchoolApi();
        Log.d("getActualMinimum", String.valueOf(calendar.getTime()));
        try {
            api.getSchoolSchedule( y+m+d);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public String getDayOfWeek(){
        try {
            set0time();
            date = TimeFormat.parse(y+m+d);
            Log.d("dayofweek", String.valueOf(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Log.d("checkday1", String.valueOf(day));
        String dayOfWeek = "";
        switch (day){
            case 1:
                dayOfWeek = "(일";
                break;
            case 2:
                dayOfWeek = "(월";
                break;
            case 3:
                dayOfWeek = "(화";
                break;
            case 4:
                dayOfWeek = "(수";
                break;
            case 5:
                dayOfWeek = "(목";
                break;
            case 6:
                dayOfWeek = "(금";
                break;
            case 7:
                dayOfWeek = "(토";
                break;
        }
        dayOfWeek += "요일)";
        Log.d("checkday2", dayOfWeek);
        return dayOfWeek;
    }

}



