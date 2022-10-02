package com.android.platforming.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.platforming.activity.MainActivity;
import com.android.platforming.adapter.SchoolIntroduceAdapter;
import com.android.platforming.clazz.SchoolIntroduceItem;
import com.android.platforming.R;


public class SchoolIntroduceFragment extends Fragment {
    SchoolIntroduceAdapter adapter;

    ListView lv_schoolintroduce;
    ImageButton ibtn_schoolintroduce_map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schoolintroduce, container, false);

        ((MainActivity)getActivity()).setTitle("학교소개");

        adapter = new SchoolIntroduceAdapter();
        lv_schoolintroduce = view.findViewById(R.id.lv_schoolintroduce);
        ibtn_schoolintroduce_map = view.findViewById(R.id.ibtn_schoolintroduce_map);
        lv_schoolintroduce.setAdapter(adapter);

        adapter.addItem(new SchoolIntroduceItem("교목 : 소나무\n십장생의 하나로 장수를 상징하며\n세한삼우의 하나로 절개, 지조를 표상함",R.drawable.img_pine));
        adapter.addItem(new SchoolIntroduceItem("교화 : 영산홍\n진달래과에 속하며 붉은 향,\n붉은 꽃,붉은 잎에는 \n붉은 마음이 담겨 있고,\n명예를 존중하며\n청렴결백한 선비정신을 상징함",R.drawable.img_azalea));

        ibtn_schoolintroduce_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.kr/maps/place/%EA%B8%88%EC%98%A4%EA%B3%A0%EB%93%B1%ED%95%99%EA%B5%90/data=!4m5!3m4!1s0x3565c0d916567c35:0x38efabb53d57ddfb!8m2!3d36.1082025!4d128.3551143?hl=ko"));
                startActivity(urlintent);
            }
        });
        return view;
    }
}
