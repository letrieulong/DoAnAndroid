package com.example.doanandroid.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.doanandroid.Fragment.Bai_quan_tam_nhieu_Fragment;
import com.example.doanandroid.Fragment.Bai_viet_moi_Fragment;
import com.example.doanandroid.Fragment.Bai_yeu_thich_Fragment;

public class ViewPageAdapter extends FragmentStatePagerAdapter {
    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Bai_viet_moi_Fragment();
            case 1:
                return new Bai_quan_tam_nhieu_Fragment();
            case 2:
                return new Bai_yeu_thich_Fragment();
            default:
                return new Bai_viet_moi_Fragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position){
            case 0:
                title="Bài viết mới";
                break;
            case 1:
                title="Bài viết quan tâm";
                break;
            case 2:
                title="Bài viết yêu thích";
                break;
        }
        return title;
    }
}
