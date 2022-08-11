package com.android.platforming.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.platforming.adapter.TelephoneAdapter;
import com.android.platforming.clazz.TelephoneItem;
import com.android.platforming.clazz.User;
import com.example.platforming.R;
import java.util.ArrayList;
import java.util.List;

public class TelephoneFragment extends Fragment {

    TelephoneAdapter adapter;
    ArrayList data = new ArrayList();

    ListView lv_info;
    TextView tv_class;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_telephone, container, false);
        lv_info = view.findViewById(R.id.lv_info);
        tv_class = view.findViewById(R.id.tv_class);
        showTelephone();

        lv_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                final TelephoneItem items = (TelephoneItem) adapter.getItem(pos);
                    startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:"+items.getPhone())));
                }
        });

        return view;
    }
    private void showTelephone(){
        adapter = new TelephoneAdapter();
        lv_info.setAdapter(adapter);
        getTelephone(User.getUser().getGrade());
        Log.d("grade", String.valueOf(User.getUser().getGrade()));

    }

    public void getTelephone(int grade){
        switch (grade){
            case 1 : getTelephone1();
                break;

            case 2 : getTelephone2();
                break;

            case 3 : getTelephone3();
                break;
        }
    }
    private void getTelephone1() {
        Log.d("1","ok");
        tv_class.setText("1학년");
        adapter.addItem(new TelephoneItem("", "교무실", "054-456-4362"));
        adapter.addItem(new TelephoneItem("1반", "담임선생님", "0508-4171-2218"));
        adapter.addItem(new TelephoneItem("2반", "담임선생님", "0508-4171-2242"));
        adapter.addItem(new TelephoneItem("3반", "담임선생님", "0508-4171-2243"));
        adapter.addItem(new TelephoneItem("4반", "담임선생님", "0508-4171-2244"));
        adapter.addItem(new TelephoneItem("5반", "담임선생님", "0508-4171-2245"));
        adapter.addItem(new TelephoneItem("6반", "담임선생님", "0508-4171-2246"));
        adapter.addItem(new TelephoneItem("7반", "담임선생님", "0508-4171-2248"));
        adapter.addItem(new TelephoneItem("8반", "담임선생님", "0508-4171-2249"));
        adapter.addItem(new TelephoneItem("9반", "담임선생님", "0508-4171-2250"));
        adapter.addItem(new TelephoneItem("10반", "담임선생님", "0508-4171-2251"));

        lv_info.setAdapter(adapter);

    }
    private void getTelephone2() {
        Log.d("2","ok");
        tv_class.setText("2학년");
        adapter.addItem(new TelephoneItem("", "교무실", "054-456-4363"));
        adapter.addItem(new TelephoneItem("1반", "담임선생님", "0508-4171-2252"));
        adapter.addItem(new TelephoneItem("2반", "담임선생님", "0508-4171-2219"));
        adapter.addItem(new TelephoneItem("3반", "담임선생님", "0508-4171-2253"));
        adapter.addItem(new TelephoneItem("4반", "담임선생님", "0508-4171-2254"));
        adapter.addItem(new TelephoneItem("5반", "담임선생님", "0508-4171-2255"));
        adapter.addItem(new TelephoneItem("6반", "담임선생님", "0508-4171-2256"));
        adapter.addItem(new TelephoneItem("7반", "담임선생님", "0508-4171-2257"));
        adapter.addItem(new TelephoneItem("8반", "담임선생님", "0508-4171-2258"));
        adapter.addItem(new TelephoneItem("9반", "담임선생님", "0508-4171-2259"));
        adapter.addItem(new TelephoneItem("10반", "담임선생님", "0508-4171-2260"));

        lv_info.setAdapter(adapter);
    }
    private void getTelephone3() {
        Log.d("3","ok");
        tv_class.setText("3학년");
        adapter.addItem(new TelephoneItem("", "교무실", "054-456-4381"));
        adapter.addItem(new TelephoneItem("1반", "담임선생님", "0508-4171-2261"));
        adapter.addItem(new TelephoneItem("2반", "담임선생님", "0508-4171-2262"));
        adapter.addItem(new TelephoneItem("3반", "담임선생님", "0508-4171-2263"));
        adapter.addItem(new TelephoneItem("4반", "담임선생님", "0508-4171-2264"));
        adapter.addItem(new TelephoneItem("5반", "담임선생님", "0508-4171-2265"));
        adapter.addItem(new TelephoneItem("6반", "담임선생님", "0508-4171-2220"));
        adapter.addItem(new TelephoneItem("7반", "담임선생님", "0508-4171-2266"));
        adapter.addItem(new TelephoneItem("8반", "담임선생님", "0508-4171-2267"));
        adapter.addItem(new TelephoneItem("9반", "담임선생님", "0508-4171-2268"));
        //adapter.addItem(new TelephoneItem("10반", "담임선생님", "0508-4171-2269")); 확실하지 않음

        lv_info.setAdapter(adapter);
    }
}
