package com.example.app_thong_tin_cao_thang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class post_xem_nhieu extends ListFragment {

    String[] arrayCity = {"1","2","3"};
    ArrayAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        adapter = new ArrayAdapter(getActivity(), R.layout.item_user,arrayCity);
        setListAdapter(adapter);
        return inflater.inflate(R.layout.fragment_xem_nhieu_,container,false);
    }
}
