package com.example.doanandroid.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Adapter.AdapterNew_Electronic;
import com.example.doanandroid.Adapter.AdapterNews_Electronic;
import com.example.doanandroid.Adapter.AdapterNotification_Mechanical;
import com.example.doanandroid.Adapter.AdapterRecruit_CNTT;
import com.example.doanandroid.Adapter.AdapterRecruit_Mechanical;
import com.example.doanandroid.Adapter.AdapterView_CNTT;
import com.example.doanandroid.Model.CNTT_infor;
import com.example.doanandroid.Model.ContactMechanical;
import com.example.doanandroid.Model.ContentLink;
import com.example.doanandroid.Model.Infor_All_CNTT;
import com.example.doanandroid.Model.Mechanical;
import com.example.doanandroid.Model.News_Electronic;
import com.example.doanandroid.Model.Recruit_CNTT;
import com.example.doanandroid.R;
import com.example.doanandroid.Util.SharedPreferencessss;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DepartmentMechanicalFragment extends Fragment {

    RecyclerView recyRecruit;
    RecyclerView recyNotification;
    ViewFlipper viewFlipper;
    AdapterRecruit_Mechanical adapterRecruit_mechanical;
    AdapterNotification_Mechanical adapterNotification_mechanical;
    List<Mechanical> mechanicalList = new ArrayList<>();
    List<Mechanical> mechanicalList_noti = new ArrayList<>();
    List<ContentLink> contentLinksList = new ArrayList<>();
    View view;

    public DepartmentMechanicalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_department_mechanical, container, false);
        init();
        getDataFireBase();
        Acviewflipper();
        return view;
    }

    // get data
    private void getDataFireBase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_Mechanical");
        // get data từ firebase
        mDatabase.child("list_recruit").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mechanicalList.clear();
                List<String> list = new ArrayList<>();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    list.add(dt.getKey());
                    Mechanical rs = dt.getValue(Mechanical.class);
                    mechanicalList.add(rs);
                }
                adapterRecruit_mechanical.notifyDataSetChanged();
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
                adapterRecruit_mechanical.notifyDataSetChanged();
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

        // lịch tiếp sinh viên
        mDatabase.child("list_notification").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mechanicalList_noti.clear();
                List<String> list = new ArrayList<>();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    list.add(dt.getKey());
                    Mechanical rs = dt.getValue(Mechanical.class);
                    mechanicalList_noti.add(rs);
                }
                adapterNotification_mechanical.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // banner
    private void Acviewflipper() {
        ArrayList<String> arrayViewFlipper = new ArrayList<>();
        arrayViewFlipper.add("https://ck.caothang.edu.vn/images/banner/1641185054_tuyen sinh 2022.png");
        arrayViewFlipper.add("https://ck.caothang.edu.vn/images/banner/1558893001_giơi thieu 2 - copy (2).jpg");
        arrayViewFlipper.add("https://ck.caothang.edu.vn/images/banner/1558888296_giơi thieu 1-4.jpg");
        arrayViewFlipper.add("https://ck.caothang.edu.vn/images/banner/1558893044_giơi thieu 4 (1).jpg");
        arrayViewFlipper.add("https://ck.caothang.edu.vn/images/banner/1558888827_giơi thieu 5.jpg");
        arrayViewFlipper.add("https://ck.caothang.edu.vn/images/banner/1558891625_giơi thieu 3 - copy.jpg");
        arrayViewFlipper.add("https://ck.caothang.edu.vn/images/banner/1544591313_banner-TS - copy(2)-3.jpg");
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

    //Hỗ trợ đổi TEXT
    private void setText(final TextView text, final String value) {
        if (text != null) {
            text.setText(value);
        }
    }


    // khởi tạo các control
    private void init() {
        viewFlipper = view.findViewById(R.id.viewflipper);

        // list tuyển dụng
        recyRecruit = view.findViewById(R.id.recyRecruit);
        adapterRecruit_mechanical = new AdapterRecruit_Mechanical(getContext(), mechanicalList);
        recyRecruit.setLayoutManager(new LinearLayoutManager(getContext()));
        recyRecruit.setAdapter(adapterRecruit_mechanical);

        // list tiếp sinh viên
        recyNotification = view.findViewById(R.id.recyNotification);
        adapterNotification_mechanical = new AdapterNotification_Mechanical(getContext(), mechanicalList_noti);
        recyNotification.setLayoutManager(new LinearLayoutManager(getContext()));
        recyNotification.setAdapter(adapterNotification_mechanical);
    }

}