package com.example.doanandroid.Object;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanandroid.Adapter.AdapterCTCT_HSSV;
import com.example.doanandroid.Adapter.AdapterCTCT_ROOM;
import com.example.doanandroid.Adapter.AdapterTrainBTN;
import com.example.doanandroid.Adapter.AdapterTrainBTN_HKB;
import com.example.doanandroid.Fragment.RoomCTCT_HSSVFragment;
import com.example.doanandroid.Fragment.RoomTrainingFragment;
import com.example.doanandroid.Model.CLA_Model;
import com.example.doanandroid.Model.HKB_Model;
import com.example.doanandroid.Model.Model_CTCT;
import com.example.doanandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SHCNActivity extends AppCompatActivity implements View.OnClickListener {
    public static Toolbar toolbar;
    RecyclerView recy_shcn;
    AdapterCTCT_HSSV adapterCTCT_hssv;
    AdapterCTCT_ROOM adapterCTCT_room;
    List<Model_CTCT> model_ctcts_list = new ArrayList<>();
    List<CLA_Model> cla_modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shcnactivity);
        init();
        Actionbar();
        getDataFireBase();
        findViewById(R.id.view_more_shcn).setOnClickListener(this::onClick);
    }

    private void init() {
        recy_shcn = findViewById(R.id.recy_tkb);

        if (RoomCTCT_HSSVFragment.btn_count == 1) {
            adapterCTCT_hssv = new AdapterCTCT_HSSV(SHCNActivity.this, model_ctcts_list);
            recy_shcn.setLayoutManager(new LinearLayoutManager(SHCNActivity.this));
            recy_shcn.setAdapter(adapterCTCT_hssv);
        } else if (RoomCTCT_HSSVFragment.btn_count == 2) {
            adapterCTCT_room = new AdapterCTCT_ROOM(SHCNActivity.this, cla_modelList);
            recy_shcn.setLayoutManager(new LinearLayoutManager(SHCNActivity.this));
            recy_shcn.setAdapter(adapterCTCT_room);
        }

    }

    //     get data
    private void getDataFireBase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_ctct_hssv");
        if (RoomCTCT_HSSVFragment.btn_count == 1) {
            model_ctcts_list.clear();
            mDatabase.child("list_shcn").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<String> list = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Model_CTCT n = snap.getValue(Model_CTCT.class);
                        model_ctcts_list.add(n);
                    }
                    adapterCTCT_hssv.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if (RoomCTCT_HSSVFragment.btn_count == 2) {
            cla_modelList.clear();
            mDatabase.child("list_roomschool").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<String> list = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        CLA_Model n = snap.getValue(CLA_Model.class);
                        cla_modelList.add(n);

                        findViewById(R.id.view_more_shcn).setVisibility(View.GONE);

                        setText(findViewById(R.id.txt_content), Html.fromHtml(n.getContent()).toString());
                    }
                    adapterCTCT_room.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if (RoomCTCT_HSSVFragment.btn_count == 3) {
            mDatabase.child("list_HKP").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    hkb_modelList.clear();
//                    for (DataSnapshot dt : snapshot.getChildren()) {
//                        HKB_Model h = dt.getValue(HKB_Model.class);
//                        hkb_modelList.add(h);
//                        Log.d("abc", h.getTitle());
//                    }
//                    adapterTrainBTN_hkb.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    //Hỗ trợ đổi TEXT
    private void setText(final TextView text, final String value) {
        if (text != null) {
            text.setText(value);
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
            case R.id.view_more_shcn:
                txt_view_tkb = view.findViewById(R.id.view_more_shcn);
                if (RoomCTCT_HSSVFragment.btn_count == 1) {
                    if (txt_view_tkb.getText().toString().equals("Xem thêm")) {
                        if (model_ctcts_list.size() + count_tkb < model_ctcts_list.size()) {
                            count_tkb++;
                            adapterCTCT_hssv.notifyDataSetChanged();
                            if (model_ctcts_list.size() + count_tkb == model_ctcts_list.size()) {
                                txt_view_tkb.setText("Thu nhỏ");
                            }
                        } else {
                            count_tkb = -1;
                            adapterCTCT_hssv.notifyDataSetChanged();
                        }
                    } else {
                        count_tkb = -1;
                        txt_view_tkb.setText("Xem thêm");
                        adapterCTCT_hssv.notifyDataSetChanged();
                    }
                }
                return;
        }
    }
}