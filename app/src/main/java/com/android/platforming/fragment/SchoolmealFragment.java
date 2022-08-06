package com.android.platforming.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.platforming.clazz.SchoolApi;
import com.example.platforming.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SchoolmealFragment extends Fragment {
    private SchoolApi api;

    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
    String y = dateFormat.format(date);
    SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM");
    String m = dateFormat1.format(date);
    SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd");
    String d = dateFormat2.format(date);

    Calendar calendar = Calendar.getInstance();
    Calendar minDate = Calendar.getInstance();
    ArrayAdapter<String> adapter;
    ArrayList data = new ArrayList();

    TextView tv_date;
    ListView lv_foodName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schoolmeal, container, false);
        Button btn_calender = view.findViewById(R.id.btn_calender);
        tv_date = view.findViewById(R.id.tv_date);
        lv_foodName =view.findViewById(R.id.lv_foodlist);

        tv_date.setText((y+"년"+m+"월"+d+"일"));
        api = new SchoolApi();
        try {
            api.getSchoolMeal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showFood();


        btn_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate();
            }
        });

        return view;
    }

    //달력
    public void showFood(){
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, data);
        lv_foodName.setAdapter(adapter);
        data.clear();
        data.addAll(api.getResult());
        adapter.notifyDataSetChanged();
    }

    public void showDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                y = String.valueOf(year);
                m = String.valueOf(month);
                if(Integer.parseInt(m) < 10)
                    m = "0" + m;
                d = String.valueOf(dayOfMonth);
                if(Integer.parseInt(d) < 10)
                    d = "0" + d;
                tv_date.setText((y+"년"+m+"월"+d+"일"));
                api = new SchoolApi();
                try {
                    api.getSchoolMeal(y+m+d);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                showFood();
            }
        },Integer.parseInt(y),Integer.parseInt(m),Integer.parseInt(d));
        minDate.set(2020,0,1);
        datePickerDialog.getDatePicker().setMinDate(minDate.getTime().getTime());
        datePickerDialog.setMessage("날짜선택");
        datePickerDialog.show();
    }
}
