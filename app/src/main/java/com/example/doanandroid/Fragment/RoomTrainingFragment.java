package com.example.doanandroid.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Model.ContactMechanical;
import com.example.doanandroid.Model.Mechanical;
import com.example.doanandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomTrainingFragment extends Fragment {

    public RoomTrainingFragment() {
        // Required empty public constructor
    }

    ViewFlipper viewFlipper;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_room_training, container, false);
        init();
        Acviewflipper();
        return view;
    }

    private void init() {
        viewFlipper = view.findViewById(R.id.viewflipper);
    }

    // get data
//    private void getDataFireBase() {
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_Mechanical");
//        // get data từ firebase
//        mDatabase.child("list_recruit").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                mechanicalList.clear();
//                List<String> list = new ArrayList<>();
//                for (DataSnapshot dt : snapshot.getChildren()) {
//                    list.add(dt.getKey());
//                    Mechanical rs = dt.getValue(Mechanical.class);
////                    mechanicalList.add(rs);
//                }
////                adapterRecruit_mechanical.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        // contact
//        mDatabase.child("contact").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ContactMechanical ct = snapshot.getValue(ContactMechanical.class);
//                setText(view.findViewById(R.id.txt_email), ct.getEmail());
//                setText(view.findViewById(R.id.txt_phone), ct.getPhone());
//                setText(view.findViewById(R.id.txt_address), ct.getAddress());
//
//                // truy cập vào từng phần tử
////                HashMap<String,Object> hashMap= (HashMap<String, Object>) snapshot.getValue();
//                //HashMap này sẽ có kích  thước bằng số Node con bên trong node truy vấn
//                //mỗi phần tử có key là name được định nghĩa trong cấu trúc Json của Firebase
////                Log.d("abc",hashMap.get("email").toString());
//                adapterRecruit_mechanical.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        // access
//        mDatabase.child("access").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                List<String> access = new ArrayList<>();
//                for (DataSnapshot snap : snapshot.getChildren()) {
//                    access.add(snap.getValue().toString());
//                }
//                setText(view.findViewById(R.id.txt_access_today), "Hôm nay : " + access.get(1));
//                setText(view.findViewById(R.id.access_month), "Tháng này : " + access.get(0));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        // lịch tiếp sinh viên
//        mDatabase.child("list_notification").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mechanicalList_noti.clear();
//                List<String> list = new ArrayList<>();
//                for (DataSnapshot dt : snapshot.getChildren()) {
//                    list.add(dt.getKey());
//                    Mechanical rs = dt.getValue(Mechanical.class);
//                    mechanicalList_noti.add(rs);
//                }
//                adapterNotification_mechanical.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    //Hỗ trợ đổi TEXT
    private void setText(final TextView text, final String value) {
        if (text != null) {
            text.setText(value);
        }
    }


    // banner
    private void Acviewflipper() {
        ArrayList<String> arrayViewFlipper = new ArrayList<>();
        arrayViewFlipper.add("https://daotao.caothang.edu.vn/images/banner/1641353501_Banner%20TS%202022%20-%20900x452.jpg");
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
}