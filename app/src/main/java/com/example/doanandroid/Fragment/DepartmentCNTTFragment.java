package com.example.doanandroid.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Adapter.AdapterRecruit_CNTT;
import com.example.doanandroid.Adapter.AdapterSearch_CNTT;
import com.example.doanandroid.Adapter.AdapterSearch_Mechanical;
import com.example.doanandroid.Adapter.AdapterView_CNTT;
import com.example.doanandroid.MainActivity;
import com.example.doanandroid.Model.CNTT_infor;
import com.example.doanandroid.Model.Infor_All_CNTT;
import com.example.doanandroid.Model.Mechanical;
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
    RecyclerView recy_search;
    List<Recruit_CNTT> recruit_cnttList = new ArrayList<>();
    List<Infor_All_CNTT> infor_all_cntts = new ArrayList<>();
    List<Infor_All_CNTT> list_search = new ArrayList<>();
    AdapterRecruit_CNTT adapterRecruit_cntt;
    AdapterView_CNTT adapterView_cntt;
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
        view = inflater.inflate(R.layout.fragment_department_c_n_t_t, container, false);
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
                    Infor_All_CNTT rss = dt.getValue(Infor_All_CNTT.class);
                    recruit_cnttList.add(rs);
                    list_search.add(rss);
                }
                adapterSearch_cntt.notifyDataSetChanged();
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
                    list_search.add(rs);
                }
                adapterSearch_cntt.notifyDataSetChanged();
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

        // tìm kiếm
        recy_search = view.findViewById(R.id.list_item);
        adapterSearch_cntt = new AdapterSearch_CNTT(getContext(), list_search);
        recy_search.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_search.setAdapter(adapterSearch_cntt);

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
                    for (Recruit_CNTT rc : recruit_cnttList){
                        if (rc.getTitle().toLowerCase().contains(query.toLowerCase())){
                            Log.d("abc", rc.getTitle());
                        }
                    }
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