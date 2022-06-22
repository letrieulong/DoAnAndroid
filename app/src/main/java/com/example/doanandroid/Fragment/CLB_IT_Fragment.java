package com.example.doanandroid.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Adapter.AdapterClb_CLB;
import com.example.doanandroid.Adapter.AdapterNew_CLB;
import com.example.doanandroid.Adapter.AdapterSearch_CNTT;
import com.example.doanandroid.Object.MainActivity;
import com.example.doanandroid.Model.CNTT_infor;
import com.example.doanandroid.Model.Infor_All_CNTT;
import com.example.doanandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CLB_IT_Fragment extends Fragment {


    public CLB_IT_Fragment() {
        // Required empty public constructor
    }

    RecyclerView recyCLB;
    RecyclerView recyWriteNew;
    RecyclerView recy_search;
    List<Infor_All_CNTT> list_clb = new ArrayList<>();
    List<Infor_All_CNTT> list_clb_new = new ArrayList<>();
    List<Infor_All_CNTT> list_search = new ArrayList<>();
    AdapterClb_CLB adapterClb_clb;
    AdapterNew_CLB adapterNew_clb;
    AdapterSearch_CNTT adapterSearch_cntt;
    TextView txt_title_name;
    ImageView img_view;
    ViewFlipper viewFlipper;
    Toolbar toolbar;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_c_l_b__i_t_, container, false);
        init();
        setHasOptionsMenu(true);
        getDataFireBase();
        Acviewflipper();
        Actionbar();
        return view;
    }
    private void Actionbar() {
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    // get data
    private void getDataFireBase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_clb");
        // get data từ firebase
        mDatabase.child("list_clb_it").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_clb.clear();
                List<String> list = new ArrayList<>();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    list.add(dt.getKey());
                    Infor_All_CNTT rs = dt.getValue(Infor_All_CNTT.class);
                    list_clb.add(rs);
                    list_search.add(rs);
                }
                adapterClb_clb.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference mDatabases = FirebaseDatabase.getInstance().getReference("list_cntt");
        // get data từ firebase
        mDatabases.child("contents").addValueEventListener(new ValueEventListener() {
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
        mDatabase.child("list_clb_new").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_clb_new.clear();
                List<String> list = new ArrayList<>();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    list.add(dt.getKey());
                    Infor_All_CNTT rs = dt.getValue(Infor_All_CNTT.class);
                    list_clb_new.add(rs);
                    list_search.add(rs);
                }
                adapterSearch_cntt.notifyDataSetChanged();
                adapterNew_clb.notifyDataSetChanged();
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

        // tìm kiếm
        recy_search = view.findViewById(R.id.list_item);
        adapterSearch_cntt = new AdapterSearch_CNTT(getContext(), list_search);
        recy_search.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_search.setAdapter(adapterSearch_cntt);

        // list all
        recyCLB = view.findViewById(R.id.recyclb);
        adapterClb_clb = new AdapterClb_CLB(getContext(), list_clb);
        recyCLB.setLayoutManager(new LinearLayoutManager(getContext()));
        recyCLB.setAdapter(adapterClb_clb);

        // list tuyển dụng
        recyWriteNew = view.findViewById(R.id.recywritenew);
        adapterNew_clb = new AdapterNew_CLB(getContext(), list_clb_new);
        recyWriteNew.setLayoutManager(new LinearLayoutManager(getContext()));
        recyWriteNew.setAdapter(adapterNew_clb);
    }

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);

                    filter(newText);
                    recy_search.setVisibility(View.VISIBLE);
                    if (newText.equals("")){
                        recy_search.setVisibility(View.GONE);
                    }
                    adapterSearch_cntt.notifyDataSetChanged();
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    // Tìm kiếm giá trị theo mssv
    private void filter(String text) {
        // tạo một danh sách mảng mới để lọc dữ liệu
        ArrayList<Infor_All_CNTT> filteredlist = new ArrayList<>();

        // so sánh các phần từ trong adapter
        for (Infor_All_CNTT item : list_search) {
            // kiểm tra chuỗi vừa nhập có khớp với giá trị cần so sánh hay không
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        // kiểm tra data vừa nhập có chứa nội dung trong adapter hay không
        if (filteredlist.isEmpty()) {
        } else {
            // nếu có sẽ add vào classAdapter
            adapterSearch_cntt.filterList(filteredlist);
            adapterSearch_cntt.notifyDataSetChanged();
        }
    }
}