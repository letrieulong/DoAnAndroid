package com.example.doanandroid.Object;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.doanandroid.Adapter.AdapterSearch_Trainning;
import com.example.doanandroid.Adapter.AdapterTrainBTN;
import com.example.doanandroid.Adapter.AdapterTrainBTN_HKB;
import com.example.doanandroid.Fragment.RoomTrainingFragment;
import com.example.doanandroid.Model.CLA_Model;
import com.example.doanandroid.Model.ContactMechanical;
import com.example.doanandroid.Model.HKB_Model;
import com.example.doanandroid.Model.New_Tranning;
import com.example.doanandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TKBActivity extends AppCompatActivity implements View.OnClickListener {
    public static Toolbar toolbar;
    RecyclerView recy_tkb;
    AdapterTrainBTN adapterTrainBTN;
    AdapterTrainBTN_HKB adapterTrainBTN_hkb;
    List<CLA_Model> cla_modelList = new ArrayList<>();
    List<HKB_Model> hkb_modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tkbactivity);
        init();
        Actionbar();
        getDataFireBase();
        findViewById(R.id.view_more_tkb).setOnClickListener(this::onClick);
    }

    private void init() {
        recy_tkb = findViewById(R.id.recy_tkb);

        if (RoomTrainingFragment.str.equals("HK.PHỤ,GHÉP")){
            adapterTrainBTN_hkb = new AdapterTrainBTN_HKB(TKBActivity.this, hkb_modelList);
            recy_tkb.setLayoutManager(new LinearLayoutManager(TKBActivity.this));
            recy_tkb.setAdapter(adapterTrainBTN_hkb);
        }else {
            adapterTrainBTN = new AdapterTrainBTN(this, cla_modelList);
            recy_tkb.setLayoutManager(new LinearLayoutManager(this));
            recy_tkb.setAdapter(adapterTrainBTN);
        }
    }

    //     get data
    private void getDataFireBase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_training");
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference("list_train");
        if (RoomTrainingFragment.str.equals("THỜI KHÓA BIỂU")) {
            mDatabase.child("list_TKB").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<String> list = new ArrayList<>();
                    cla_modelList.clear();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        CLA_Model n = snap.getValue(CLA_Model.class);
                        cla_modelList.add(n);
                    }
                    adapterTrainBTN.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else if (RoomTrainingFragment.str.equals("LỊCH THI")) {
            mDatabase.child("list_claexam").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<String> list = new ArrayList<>();
                    cla_modelList.clear();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        CLA_Model n = snap.getValue(CLA_Model.class);
                        cla_modelList.add(n);
                    }
                    adapterTrainBTN.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if (RoomTrainingFragment.str.equals("HK.PHỤ,GHÉP")) {
            mDatabase.child("list_HKP").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    hkb_modelList.clear();
                    for (DataSnapshot dt : snapshot.getChildren()) {
                        HKB_Model h = dt.getValue(HKB_Model.class);
                        hkb_modelList.add(h);
                        Log.d("abc", h.getTitle());
                    }
                    adapterTrainBTN_hkb.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            mData.child("list_TKB").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<String> list = new ArrayList<>();
                    cla_modelList.clear();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        CLA_Model n = snap.getValue(CLA_Model.class);
                        cla_modelList.add(n);
                    }
                    adapterTrainBTN.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void Actionbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(RoomTrainingFragment.str);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    TextView txt_view_tkb;
    public static int count_tkb = -1;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_more_tkb:
                txt_view_tkb = view.findViewById(R.id.view_more_tkb);
                if (RoomTrainingFragment.str.equals("HK.PHỤ,GHÉP")){
                    if (txt_view_tkb.getText().toString().equals("Xem thêm")) {
                        if (hkb_modelList.size() + count_tkb < hkb_modelList.size()) {
                            count_tkb++;
                            adapterTrainBTN_hkb.notifyDataSetChanged();
                            if (hkb_modelList.size() + count_tkb == hkb_modelList.size()) {
                                txt_view_tkb.setText("Thu nhỏ");
                            }
                        } else {
                            count_tkb = -1;
                            adapterTrainBTN_hkb.notifyDataSetChanged();
                        }
                    } else {
                        count_tkb = -1;
                        txt_view_tkb.setText("Xem thêm");
                        adapterTrainBTN_hkb.notifyDataSetChanged();
                    }
                }else {
                    if (txt_view_tkb.getText().toString().equals("Xem thêm")) {
                        if (cla_modelList.size() + count_tkb < cla_modelList.size()) {
                            count_tkb++;
                            adapterTrainBTN.notifyDataSetChanged();
                            if (cla_modelList.size() + count_tkb == cla_modelList.size()) {
                                txt_view_tkb.setText("Thu nhỏ");
                            }
                        } else {
                            count_tkb = -1;
                            adapterTrainBTN.notifyDataSetChanged();
                        }
                    } else {
                        count_tkb = -1;
                        txt_view_tkb.setText("Xem thêm");
                        adapterTrainBTN.notifyDataSetChanged();
                    }
                }
                return;
        }
    }
}