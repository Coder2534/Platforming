package com.android.platforming.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
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

import com.android.platforming.InitApplication;
import com.android.platforming.activity.MainActivity;
import com.android.platforming.clazz.SchoolApi;
import com.android.platforming.interfaze.ListenerInterface;
import com.android.platforming.R;

import java.util.ArrayList;
import java.util.Calendar;

public class SchoolmealFragment extends Fragment {
    private SchoolApi api;

    ListenerInterface listenerInterface;

    Calendar minDate = Calendar.getInstance();
    ArrayAdapter<String> adapter;
    ArrayList data = new ArrayList();

    TextView tv_date;
    ListView lv_foodName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schoolmeal, container, false);

        ((MainActivity)getActivity()).setTitle("식단표");

        Button btn_calender = view.findViewById(R.id.btn_calender);
        tv_date = view.findViewById(R.id.tv_date);
        lv_foodName =view.findViewById(R.id.lv_foodlist);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, data);
        lv_foodName.setAdapter(adapter);

        listenerInterface = new ListenerInterface() {
            @Override
            public void onSuccess() {
                tv_date.setText(api.getDate());
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

        api = new SchoolApi();
        try {
            api.getSchoolMeal(listenerInterface);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        btn_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate();
            }
        });

        return view;
    }

    public void showDate() {
        int[] styles = {R.style.datepicker_white, R.style.datepicker_pink, R.style.datepicker_blue, R.style.datepicker_green, R.style.datepicker_black};

        int appliedTheme = ((InitApplication)getActivity().getApplication()).getAppliedTheme();

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), styles[appliedTheme], new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                api = new SchoolApi();
                try {
                    api.getSchoolMeal(Integer.toString(year), Integer.toString(month + 1), Integer.toString(dayOfMonth), listenerInterface);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, Integer.parseInt(api.getYear()), Integer.parseInt(api.getMonth()) - 1, Integer.parseInt(api.getDay()));

        minDate.set(2020,0,1);
        datePickerDialog.getDatePicker().setMinDate(minDate.getTime().getTime());
        datePickerDialog.show();
    }
}
