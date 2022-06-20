package com.example.app_thong_tin_cao_thang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        String title ="";
//        switch (position){
//            case 0:
//                title = "Xem nhiều";
//                break;
//            case 1:
//                title = "Mới nhất";
//                break;
//        }
//        return title;
//    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Xem_nhieu_Fragment();
            default:
                return new Bai_moi_Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
