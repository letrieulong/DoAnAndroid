package com.example.doanandroid.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.doanandroid.Adapter.AdapterNew_Trainning;
import com.example.doanandroid.Model.ContactMechanical;
import com.example.doanandroid.Model.ContentLink;
import com.example.doanandroid.Model.Mechanical;
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

public class RoomTrainingFragment extends Fragment {

    public RoomTrainingFragment() {
        // Required empty public constructor
    }

    ViewFlipper viewFlipper;
    RecyclerView recyNew;
    View view;
    List<ContentLink> contentLinkList = new ArrayList<>();
    List<Mechanical> mechanicalList = new ArrayList<>();
    AdapterNew_Trainning adapterNew_trainning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_room_training, container, false);
        init();
        Acviewflipper();
        getDataFireBase();
        return view;
    }

    private void init() {
        viewFlipper = view.findViewById(R.id.viewflipper);

        recyNew = view.findViewById(R.id.recynew);
        adapterNew_trainning = new AdapterNew_Trainning(getContext(), mechanicalList, contentLinkList);
        recyNew.setLayoutManager(new LinearLayoutManager(getContext()));
        recyNew.setAdapter(adapterNew_trainning);
    }

    //     get data
    private void getDataFireBase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_training");
        String str = SharedPreferencessss.read(getContext(), "trainning", "");
        String[] list;
        for (int i = 0; i < 2; i++) {
            list = str.split(",");
            // lịch tiếp sinh viên
            mDatabase.child("list_new/" + list[i]).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<String> list = new ArrayList<>();
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                    //HashMap này sẽ có kích  thước bằng số Node con bên trong node truy vấn
                    //mỗi phần tử có key là name được định nghĩa trong cấu trúc Json của Firebase
                    if (hashMap.get("link") != null) {
                    }
                    String id = hashMap.get("id").toString();
                    String content_link = hashMap.get("content_link").toString();
                    String date = hashMap.get("date").toString();
                    String size = hashMap.get("size").toString();
                    String title = hashMap.get("title").toString();
                    String views = hashMap.get("views").toString();
                    Log.d("abc", hashMap.get("link").toString());
                    mechanicalList.add(new Mechanical(id, title, content_link, date, views, size));
                    adapterNew_trainning.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            mDatabase.child("list_new/link").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        Toast.makeText(getContext(), snapshot1.getValue()+"", Toast.LENGTH_SHORT).show();
                        ContentLink contentLink = snapshot1.getValue(ContentLink.class);
                        contentLinkList.add(contentLink);
                    }
                    adapterNew_trainning.notifyDataSetChanged();
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