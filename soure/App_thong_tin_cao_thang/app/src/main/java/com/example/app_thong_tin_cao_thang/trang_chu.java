package com.example.app_thong_tin_cao_thang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class trang_chu extends AppCompatActivity {

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private List<Photo> mListPhoto;
    private Timer mTimer;

    private TabLayout mTabLayout;
    private ViewPager2 mtViewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        // Tab lay out
        mTabLayout = findViewById(R.id.tab_layout);
        mtViewPager = findViewById(R.id.view_pager);

        myViewPagerAdapter = new MyViewPagerAdapter(this);
        mtViewPager.setAdapter(myViewPagerAdapter);
       new TabLayoutMediator(mTabLayout, mtViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
           @Override
           public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                   switch (position){
                case 0:
                    tab.setText("Xem nhiều");
                    break;
                case 1:
                   tab.setText("Mới nhất");
                    break;
                }
           }
       }).attach();

        // slide
        viewPager=findViewById(R.id.viewpager);
        circleIndicator=findViewById(R.id.circle_indicator);
        mListPhoto = getListPhoto();
        photoAdapter = new PhotoAdapter(this,mListPhoto);
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlideImages();
    }
    private List<Photo> getListPhoto(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.ct3));
        list.add(new Photo(R.drawable.ct1));
        list.add(new Photo(R.drawable.ct2));
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
    protected void onDestroy() {
        super.onDestroy();
        if(mTimer!=null){
            mTimer.cancel();
            mTimer=null;
        }
    }
}