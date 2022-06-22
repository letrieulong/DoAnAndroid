package com.example.doanandroid.Object;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.doanandroid.Adapter.AdapterSearch_Trainning;
import com.example.doanandroid.Adapter.AdapterTrainBTN;
import com.example.doanandroid.Fragment.RoomTrainingFragment;
import com.example.doanandroid.Model.CLA_Model;
import com.example.doanandroid.Model.ContactMechanical;
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

public class TKBActivity extends AppCompatActivity {
    public static Toolbar toolbar;
    RecyclerView recy_tkb;
    AdapterTrainBTN adapterTrainBTN;
    List<CLA_Model> cla_modelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tkbactivity);
        init();
        Actionbar();
        getDataFireBase();
    }

    private void init(){
        recy_tkb = findViewById(R.id.recy_tkb);

        adapterTrainBTN = new AdapterTrainBTN(this, cla_modelList);
        recy_tkb.setLayoutManager(new LinearLayoutManager(this));
        recy_tkb.setAdapter(adapterTrainBTN);
    }

    //     get data
    private void getDataFireBase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_training");
        if (RoomTrainingFragment.str.equals("THỜI KHÓA BIỂU")){
            mDatabase.child("list_tkb").addValueEventListener(new ValueEventListener() {
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
        else if (RoomTrainingFragment.str.equals("LỊCH THI")){
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
        }else {
            mDatabase.child("list_train").addValueEventListener(new ValueEventListener() {
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
}