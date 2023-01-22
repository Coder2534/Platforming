package com.platforming.autonomy.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.platforming.autonomy.adapter.TableAdapter;
import com.platforming.autonomy.clazz.CustomDialog;
import com.platforming.autonomy.clazz.TableItem;
import com.platforming.autonomy.interfaze.ListenerInterface;
import com.android.autonomy.R;

import java.util.ArrayList;

public class ViewPagerTimetableFragment extends Fragment {

    GridView timetable;
    TableAdapter tableAdapter;

    private ArrayList<ArrayList<TableItem>> schedules = new ArrayList<>();

    String[] keys = new String[]{
            "schedule_mon",
            "schedule_tue",
            "schedule_wed",
            "schedule_thu",
            "schedule_fri"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager_timetable, container, false);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        for(String key : keys){
            Log.d("timetableFragment", key + " : " + pref.getString(key, ""));
            schedules.add(decodeSchedule(pref.getString(key, "")));
        }

        timetable = view.findViewById(R.id.gv_timetable);
        tableAdapter = new TableAdapter(schedules);
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
                customDialog.editSchedule(getActivity(), schedules, new ListenerInterface() {
                    @Override
                    public void onSuccess() {
                        SharedPreferences.Editor editor = pref.edit();
                        for(int i = 0; i < schedules.size(); ++i){
                            editor.putString(keys[i], encodeSchedule(schedules.get(i)));
                        }
                        editor.apply();
                        tableAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        return view;
    }

    private String encodeSchedule(ArrayList<TableItem> tableItems){
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < tableItems.size(); ++i){
            TableItem tableItem = tableItems.get(i);
            result.append(String.format("%s,%s", tableItem.getMainText(), tableItem.getSubText()));
            if(i < tableItems.size() - 1)
                result.append("#");
        }

        return result.toString();
    }

    private ArrayList<TableItem> decodeSchedule(String text){
        ArrayList<TableItem> result = new ArrayList<>();

        if(text != null && !text.equals("")){
            String[] array = text.split("#");
            for(String str : array){
                String[] data = str.split(",");
                if(data.length == 0 && str.contains(","))
                    result.add(new TableItem());
                else if(data.length == 1)
                    result.add(new TableItem(data[0]));
                else
                    result.add(new TableItem(data[0], data[1]));
            }
        }

        return result;
    }
}
