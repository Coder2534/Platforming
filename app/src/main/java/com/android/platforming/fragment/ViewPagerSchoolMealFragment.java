package com.android.platforming.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.platforming.clazz.SchoolApi;
import com.example.platforming.R;

import java.util.ArrayList;

public class ViewPagerSchoolMealFragment extends Fragment {
    private SchoolApi api;

    ArrayAdapter<String> adapter;
    ArrayList data = new ArrayList();

    ListView lv_schoolMeal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager_schoolmeal, container, false);
        lv_schoolMeal =view.findViewById(R.id.lv_viewpager_schoolMeal);

        api = new SchoolApi();
        try {
            api.getSchoolMeal();
            api.joinThreadMeal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showFood();

        return view;
    }

    public void showFood(){
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, data);
        lv_schoolMeal.setAdapter(adapter);
        data.clear();
        data.addAll(api.getResult());
        adapter.notifyDataSetChanged();
    }
}
