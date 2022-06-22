package com.example.doanandroid.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanandroid.Adapter.AdapterScholarship;
import com.example.doanandroid.Object.MainActivity;
import com.example.doanandroid.Model.Tuition;
import com.example.doanandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ScholarshipFragment extends Fragment {

    public ScholarshipFragment() {
        // Required empty public constructor
    }

    View view;
    Toolbar toolbar;
    RecyclerView RecyTuition;
    List<Tuition> tuitionList = new ArrayList<>();
    AdapterScholarship adapterTuition;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_scholarship, container, false);
        init();
        Actionbar();
        getDataFireBase();
        return view;
    }

    private void Actionbar() {
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    // khởi tạo các control
    private void init() {
        // list học bổng
        RecyTuition = view.findViewById(R.id.recyTuition);
        adapterTuition = new AdapterScholarship(getContext(), tuitionList);
        RecyTuition.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyTuition.setAdapter(adapterTuition);
    }

    // get data
    private void getDataFireBase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_scholarship");
        // get data từ firebase
        mDatabase.child("Scholarship").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tuitionList.clear();
                List<String> list = new ArrayList<>();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    list.add(dt.getKey());
                    Tuition rs = dt.getValue(Tuition.class);
                    tuitionList.add(rs);
                }
                adapterTuition.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //Hỗ trợ đổi TEXT
    private void setText(final TextView text, final String value) {
        if (text != null) {
            text.setText(value);
        }
    }
}