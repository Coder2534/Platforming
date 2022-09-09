package com.android.platforming.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.android.platforming.fragment.SchoolmealFragment;
import com.android.platforming.fragment.ViewPagerSchoolMealFragment;
import com.android.platforming.fragment.ViewPagerSchoolScheduleFragment;
import com.android.platforming.fragment.ViewPagerTimetableFragment;

import java.util.ArrayList;

public class FragmentSliderAdapter extends FragmentStateAdapter {

    ArrayList<Fragment> fragments;

    public FragmentSliderAdapter(FragmentActivity fa, ArrayList<Fragment> fragments) {
        super(fa);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("FragmentSliderAdapter", "count : " + position);
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
