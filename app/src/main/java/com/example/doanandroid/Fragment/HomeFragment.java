package com.example.doanandroid.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doanandroid.Adapter.PhotoAdapter;
import com.example.doanandroid.Adapter.ViewPageAdapter;
import com.example.doanandroid.Object.MainActivity;
import com.example.doanandroid.Model.Photo;
import com.example.doanandroid.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private View view;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private List<Photo> mListPhoto;
    private Timer mTimer;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity.dialog.dismiss();
        view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.viewpager_trang_chu);
        circleIndicator=view.findViewById(R.id.circle_indicator_trang_chu);

        mListPhoto = getListPhoto();
        photoAdapter = new PhotoAdapter(getContext(),mListPhoto);
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlideImages();

        mTabLayout = view.findViewById(R.id.tab_layout_trang_chu);
        mViewPager =view.findViewById(R.id.view_pager_trang_chu);
        ViewPageAdapter viewPageAdapter=new ViewPageAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.ct1));
        list.add(new Photo(R.drawable.ct2));
        list.add(new Photo(R.drawable.ct3));
        list.add(new Photo(R.drawable.ct4));
        return list;
    }
    private void autoSlideImages(){
        if(mListPhoto==null||mListPhoto.isEmpty()||viewPager ==null){
            return;
        }
        if(mTimer == null){
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = mListPhoto.size()-1;
                        if(currentItem<totalItem){
                            currentItem ++;
                            viewPager.setCurrentItem(currentItem);
                        }else{
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },500,3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mTimer!=null){
            mTimer.cancel();
            mTimer=null;
        }
    }
}