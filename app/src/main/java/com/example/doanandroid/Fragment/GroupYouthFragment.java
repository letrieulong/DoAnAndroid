package com.example.doanandroid.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Adapter.AdapterGroup_New_update;
import com.example.doanandroid.Adapter.AdapterGroup_SV;
import com.example.doanandroid.Adapter.AdapterGroup_Youth;
import com.example.doanandroid.Adapter.AdapterPolicy_Admin;
import com.example.doanandroid.Adapter.AdapterRecruit_Admin;
import com.example.doanandroid.Model.ContactMechanical;
import com.example.doanandroid.Model.New_Tranning;
import com.example.doanandroid.Model.Policy;
import com.example.doanandroid.Model.Recruit_Admin;
import com.example.doanandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GroupYouthFragment extends Fragment {
    public GroupYouthFragment() {
        // Required empty public constructor
    }

    View view;
    ViewFlipper viewFlipper;
    AdapterGroup_SV adapterGroup_sv;
    AdapterGroup_Youth adapterGroup_youth;
    AdapterGroup_New_update adapterGroup_new_update;
    List<New_Tranning> new_List_sv = new ArrayList<>();
    List<New_Tranning> new_List_youth = new ArrayList<>();
    List<New_Tranning> new_List_update = new ArrayList<>();
    RecyclerView recy_youth;
    RecyclerView recy_group_sv;
    RecyclerView recy_new_update;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_group_youth, container, false);
        init();
        Acviewflipper();
        getDataFireBase();
        return view;
    }

    // khởi tạo các control
    private void init() {
        viewFlipper = view.findViewById(R.id.viewflipper);

        // hội sinh viên
        recy_group_sv = view.findViewById(R.id.recygroupSV);
        adapterGroup_sv = new AdapterGroup_SV(getContext(), new_List_sv);
        recy_group_sv.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_group_sv.setAdapter(adapterGroup_sv);

        // đoàn thanh niên
        recy_youth = view.findViewById(R.id.recyyouth);
        adapterGroup_youth = new AdapterGroup_Youth(getContext(), new_List_youth);
        recy_youth.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_youth.setAdapter(adapterGroup_youth);

        // tin mới
        recy_new_update = view.findViewById(R.id.recy_news_update);
        adapterGroup_new_update = new AdapterGroup_New_update(getContext(), new_List_update);
        recy_new_update.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_new_update.setAdapter(adapterGroup_new_update);
    }
    //Hỗ trợ đổi TEXT
    private void setText(final TextView text, final String value) {
        if (text != null) {
            text.setText(value);
        }
    }

    // banner
    private void Acviewflipper() {
        ArrayList<String> arrayViewFlipper = new ArrayList<>();
        arrayViewFlipper.add("https://hcqt.caothang.edu.vn/images/banner/1569581178_caothang.jpg");
        for (int i = 0; i < arrayViewFlipper.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            Glide.with(this).load(arrayViewFlipper.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY); // sét hình ảnh hiển thị full;
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_right_in);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_right_out);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    //     get data
    private void getDataFireBase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_group");
        // get data từ firebase
        mDatabase.child("list_group_student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                new_List_sv.clear();
                List<String> list = new ArrayList<>();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    list.add(dt.getKey());
                    New_Tranning rs = dt.getValue(New_Tranning.class);
                    new_List_sv.add(rs);
                }
                adapterGroup_sv.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // get data từ firebase
        mDatabase.child("list_group_youth").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                new_List_youth.clear();
                List<String> list = new ArrayList<>();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    list.add(dt.getKey());
                    New_Tranning rs = dt.getValue(New_Tranning.class);
                    new_List_youth.add(rs);
                }
                adapterGroup_youth.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // get data từ firebase
        mDatabase.child("list_new_update").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                new_List_update.clear();
                List<String> list = new ArrayList<>();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    list.add(dt.getKey());
                    New_Tranning rs = dt.getValue(New_Tranning.class);
                    new_List_update.add(rs);
                }
                adapterGroup_new_update.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // contact
        mDatabase.child("contact").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ContactMechanical ct = snapshot.getValue(ContactMechanical.class);
                setText(view.findViewById(R.id.txt_email), ct.getEmail());
                setText(view.findViewById(R.id.txt_phone), ct.getPhone());
                setText(view.findViewById(R.id.txt_address), ct.getAddress());
                // truy cập vào từng phần tử
//                HashMap<String,Object> hashMap= (HashMap<String, Object>) snapshot.getValue();
                //HashMap này sẽ có kích  thước bằng số Node con bên trong node truy vấn
                //mỗi phần tử có key là name được định nghĩa trong cấu trúc Json của Firebase
//                Log.d("abc",hashMap.get("email").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // access
        mDatabase.child("access").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<String> access = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    access.add(snap.getValue().toString());
                }
                setText(view.findViewById(R.id.txt_access_today), "Hôm nay : " + access.get(1));
                setText(view.findViewById(R.id.access_month), "Tháng này : " + access.get(0));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}