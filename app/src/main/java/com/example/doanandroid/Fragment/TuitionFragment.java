package com.example.doanandroid.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doanandroid.Adapter.AdapterTuition;
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

public class TuitionFragment extends Fragment implements View.OnClickListener {

    public TuitionFragment() {
        // Required empty public constructor
    }

    View view;
    Toolbar toolbar;
    RecyclerView RecyTuition;
    List<Tuition> tuitionList = new ArrayList<>();
    AdapterTuition adapterTuition;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tuition, container, false);
        init();
        Actionbar();
        getDataFireBase();
        view.findViewById(R.id.view_more).setOnClickListener(this::onClick);

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
    // kh???i t???o c??c control
    private void init() {
        // list h???c b???ng
        RecyTuition = view.findViewById(R.id.recyTuition);
        adapterTuition = new AdapterTuition(getContext(), tuitionList);
        RecyTuition.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyTuition.setAdapter(adapterTuition);
    }

    // get data
    private void getDataFireBase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_tuition");
        // get data t??? firebase
        mDatabase.child("tuition").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tuitionList.clear();
                List<String> list = new ArrayList<>();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    list.add(dt.getKey());
                    Tuition rs = dt.getValue(Tuition.class);
                    tuitionList.add(rs);
                    MainActivity.dialog.dismiss();
                }
                adapterTuition.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //H??? tr??? ?????i TEXT
    private void setText(final TextView text, final String value) {
        if (text != null) {
            text.setText(value);
        }
    }

    TextView txt_view_more;
    public static int count = -3;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_more:
                txt_view_more = view.findViewById(R.id.view_more);
                if (txt_view_more.getText().toString().equals("Xem th??m")) {
                    if (tuitionList.size() + count < tuitionList.size()) {
                        count++;
                        adapterTuition.notifyDataSetChanged();
                        if (tuitionList.size() + count == tuitionList.size()) {
                            txt_view_more.setText("Thu nh???");
                        }
                    } else {
                        count = -3;
                        adapterTuition.notifyDataSetChanged();
                    }
                } else {
                    count = -3;
                    txt_view_more.setText("Xem th??m");
                    adapterTuition.notifyDataSetChanged();
                }
                return;
        }
    }
}