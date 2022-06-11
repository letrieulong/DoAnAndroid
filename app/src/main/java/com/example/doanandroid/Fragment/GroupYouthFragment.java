package com.example.doanandroid.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doanandroid.R;

public class GroupYouthFragment extends Fragment {
    public GroupYouthFragment() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_group_youth, container, false);
        init();
        return view;
    }

    // khởi tạo các control
    private void init() {
    }
}