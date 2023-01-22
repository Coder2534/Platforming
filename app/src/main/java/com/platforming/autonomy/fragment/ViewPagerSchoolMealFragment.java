package com.platforming.autonomy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.platforming.autonomy.clazz.SchoolApi;
import com.platforming.autonomy.interfaze.ListenerInterface;
import com.android.autonomy.R;

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
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, data);
        lv_schoolMeal.setAdapter(adapter);

        api = new SchoolApi();
        try {
            api.getSchoolMeal(new ListenerInterface() {
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
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return view;
    }
}
