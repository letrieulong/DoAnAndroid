package com.example.doanandroid.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.doanandroid.Adapter.AdapterRecruit_CNTT;
import com.example.doanandroid.Adapter.AdapterView_CNTT;
import com.example.doanandroid.Model.CNTT_infor;
import com.example.doanandroid.Model.Infor_All_CNTT;
import com.example.doanandroid.Model.Recruit_CNTT;
import com.example.doanandroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DepartmentCNTTFragment extends Fragment {


    public DepartmentCNTTFragment() {
        // Required empty public constructor
    }

    RecyclerView recyRecruit;
    RecyclerView recyInfor;
    List<Recruit_CNTT> recruit_cnttList = new ArrayList<>();
    List<Infor_All_CNTT> infor_all_cntts = new ArrayList<>();
    AdapterRecruit_CNTT adapterRecruit_cntt;
    AdapterView_CNTT adapterView_cntt;
    TextView txt_title_name;
    ImageView img_view;
    ViewFlipper viewFlipper;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_department_c_n_t_t, container, false);
        init();
        getDataFireBase();
        Acviewflipper();
        return view;
    }

    // get data
    private void getDataFireBase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_cntt");
        // get data từ firebase
        mDatabase.child("recruit").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recruit_cnttList.clear();
                List<String> list = new ArrayList<>();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    list.add(dt.getKey());
                    Recruit_CNTT rs = dt.getValue(Recruit_CNTT.class);
                    recruit_cnttList.add(rs);
                }
                adapterRecruit_cntt.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // get data từ firebase
        mDatabase.child("contents").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CNTT_infor rs = snapshot.getValue(CNTT_infor.class);
                txt_title_name.setText(Html.fromHtml(rs.getTitle()));
                Glide.with(getContext())
                        .load(rs.getImage())
                        .into(img_view);
                setText(view.findViewById(R.id.txt_phone),rs.getPhone());
                setText(view.findViewById(R.id.txt_email),rs.getEmail());
                setText(view.findViewById(R.id.txt_address),rs.getAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // get data từ firebase
        mDatabase.child("list_all_content").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                infor_all_cntts.clear();
                List<String> list = new ArrayList<>();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    list.add(dt.getKey());
                    Infor_All_CNTT rs = dt.getValue(Infor_All_CNTT.class);
                    infor_all_cntts.add(rs);
                }
                adapterView_cntt.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // banner
    private void Acviewflipper() {
        ArrayList<String> arrayViewFlipper = new ArrayList<>();
        arrayViewFlipper.add("https://cntt.caothang.edu.vn/wp-content/uploads/2015/04/To-roi-2022-Duyet-short.jpg");
        arrayViewFlipper.add("https://cntt.caothang.edu.vn/wp-content/uploads/2015/04/banner_logo_white.png");
        arrayViewFlipper.add("https://cntt.caothang.edu.vn/wp-content/uploads/2020/03/khao-sat-elearning.png");
        arrayViewFlipper.add("https://cntt.caothang.edu.vn/wp-content/uploads/2020/03/d6db54c43ea7c5f99cb6.jpg");
        arrayViewFlipper.add("https://cntt.caothang.edu.vn/wp-content/uploads/2018/03/presentation-hackathon-2017.jpg");
        for (int i = 0 ; i < arrayViewFlipper.size(); i++){
            ImageView imageView = new ImageView(getContext());
            Glide.with(this).load(arrayViewFlipper.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY); // sét hình ảnh hiển thị full;
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(),R.anim.slide_right_in);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(),R.anim.slide_right_out);
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
        txt_title_name = view.findViewById(R.id.txt_title_name);
        img_view = view.findViewById(R.id.img_view);
        viewFlipper = view.findViewById(R.id.viewflipper);

        // list all
        recyInfor = view.findViewById(R.id.recyInfor);
        adapterView_cntt = new AdapterView_CNTT(getContext(), infor_all_cntts);
        recyInfor.setLayoutManager(new LinearLayoutManager(getContext()));
        recyInfor.setAdapter(adapterView_cntt);

        // list tuyển dụng
        recyRecruit = view.findViewById(R.id.recyRecruit);
        adapterRecruit_cntt = new AdapterRecruit_CNTT(getContext(), recruit_cnttList);
        recyRecruit.setLayoutManager(new LinearLayoutManager(getContext()));
        recyRecruit.setAdapter(adapterRecruit_cntt);
    }

}