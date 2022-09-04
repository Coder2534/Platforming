package com.android.platforming.fragment;

import static com.android.platforming.InitApplication.SELFDIAGNOSIS;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.platforming.activity.BulletinBoardActivity;
import com.android.platforming.activity.WebViewActivity;
import com.android.platforming.adapter.FragmentSliderAdapter;
import com.android.platforming.adapter.PostRecentViewAdapter;
import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.Post;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

public class MainPageFragment extends Fragment {
    ViewPager2 viewPager;
    private ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(final int position) {
            super.onPageSelected(position);
            currentPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            super.onPageScrollStateChanged(state);
            if (state == ViewPager2.SCROLL_STATE_IDLE) {
                if (currentPosition == 0) {
                    viewPager.setCurrentItem(fakeSize - 2, false);
                } else if (currentPosition == fakeSize - 1) {
                    viewPager.setCurrentItem(1, false);
                }
            } else if (state == ViewPager2.SCROLL_STATE_DRAGGING && currentPosition == fakeSize) {
                //we scroll too fast and miss the state SCROLL_STATE_IDLE for the previous item
                viewPager.setCurrentItem(2, false);
            }
        }
    };

    private int fakeSize;
    private int realSize;
    private int currentPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mainpage, container, false);

        //상단 배너
        viewPager = view.findViewById(R.id.vp_mainpage);
        FragmentSliderAdapter sliderAdapter = new FragmentSliderAdapter(getActivity());
        sliderAdapter.addFragment(new ViewPagerSchoolMealFragment());
        sliderAdapter.addFragment(new ViewPagerSchoolScheduleFragment());
        sliderAdapter.addFragment(new ViewPagerTimetableFragment());
        sliderAdapter.addFragment(new ViewPagerSchoolMealFragment());
        sliderAdapter.addFragment(new ViewPagerSchoolScheduleFragment());
        viewPager.setAdapter(sliderAdapter);
        viewPager.registerOnPageChangeCallback(pageChangeCallback);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setOffscreenPageLimit(5);

        realSize = 3;
        fakeSize = realSize + 2;
        viewPager.setCurrentItem(2, false);

        //자가진단 배너
        ImageView selfDiagnosis = view.findViewById(R.id.iv_mainpage_selfdiagnosis);
        selfDiagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("type", SELFDIAGNOSIS);
                startActivity(intent);
            }
        });

        //최근 게시물
        RecyclerView recyclerView = view.findViewById(R.id.rv_mainpage_recentpost);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        PostRecentViewAdapter recentViewAdapter = new PostRecentViewAdapter(Post.getPosts());
        recentViewAdapter.setListenerInterface(new ListenerInterface() {
            @Override
            public void onSuccess(int position) {
                Post post = Post.getPosts().get(position);
                Activity activity = getActivity();

                Intent intent = new Intent(activity, BulletinBoardActivity.class);
                intent.putExtra("type", post.getType());
                intent.putExtra("id", post.getId());
                startActivity(intent);
                activity.overridePendingTransition(R.anim.start_activity_noticeboard, R.anim.none);
            }
        });
        recyclerView.setAdapter(recentViewAdapter);

        FirestoreManager firestoreManager = new FirestoreManager();
        firestoreManager.readRecentPostData(new ListenerInterface() {
            @Override
            public void onSuccess() {
                recentViewAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}