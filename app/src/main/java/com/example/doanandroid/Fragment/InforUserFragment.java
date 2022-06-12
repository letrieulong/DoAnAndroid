package com.example.doanandroid.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.doanandroid.LoginActivity;
import com.example.doanandroid.R;

public class InforUserFragment extends Fragment implements View.OnClickListener {

    public InforUserFragment() {
        // Required empty public constructor
    }
    View view;
    Button btn_logout;
    LinearLayout linear_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_infor_user, container, false);
        init();
        btn_logout.setOnClickListener(this::onClick);
        linear_logout.setOnClickListener(this::onClick);
        return view;
    }

    private void init() {
        btn_logout    = view.findViewById(R.id.btn_logout);
        linear_logout       = view.findViewById(R.id.linear_logout);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_logout:
                SharedPreferences preferences = getContext().getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                startActivity(new Intent(getContext(), LoginActivity.class));
            case R.id.linear_logout:
                SharedPreferences preferences1 = getContext().getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences1.edit();
                editor1.putString("remember", "false");
                editor1.apply();
                startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }
}