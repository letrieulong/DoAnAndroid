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
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Adapter.AdapterNew_Electronic;
import com.example.doanandroid.Adapter.AdapterNews_Electronic;
import com.example.doanandroid.Adapter.AdapterNotification_Mechanical;
import com.example.doanandroid.Adapter.AdapterRecruit_CNTT;
import com.example.doanandroid.Adapter.AdapterRecruit_Mechanical;
import com.example.doanandroid.Adapter.AdapterSearch_Electronic;
import com.example.doanandroid.Adapter.AdapterSearch_Mechanical;
import com.example.doanandroid.Adapter.AdapterView_CNTT;
import com.example.doanandroid.MainActivity;
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
    RecyclerView recy_search;
    ViewFlipper viewFlipper;
    AdapterRecruit_Mechanical adapterRecruit_mechanical;
    AdapterSearch_Mechanical adapterSearch_mechanical;
    AdapterNotification_Mechanical adapterNotification_mechanical;
    List<Mechanical> mechanicalList = new ArrayList<>();
    List<Mechanical> mechanicalList_noti = new ArrayList<>();
    List<Mechanical> list_search = new ArrayList<>();
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
        Actionbar();
        setHasOptionsMenu(true);
        return view;
    }

    private void Actionbar() {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
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
                    list_search.add(rs);
                }
                adapterSearch_mechanical.notifyDataSetChanged();
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
                    list_search.add(rs);
                }
                adapterSearch_mechanical.notifyDataSetChanged();
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
        // tìm kiếm
        recy_search = view.findViewById(R.id.list_item);
        adapterSearch_mechanical = new AdapterSearch_Mechanical(getContext(), list_search);
        recy_search.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_search.setAdapter(adapterSearch_mechanical);

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

    /**
     * Tìm kiếm
     * **/
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
                    adapterSearch_mechanical.notifyDataSetChanged();
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
//                    for (Recruit_CNTT rc : recruit_cnttList){
//                        if (rc.getTitle().toLowerCase().contains(query.toLowerCase())){
//                            Log.d("abc", rc.getTitle());
//                        }
//                    }
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
        ArrayList<Mechanical> filteredlist = new ArrayList<>();

        // so sánh các phần từ trong adapter
        for (Mechanical item : list_search) {
            // kiểm tra chuỗi vừa nhập có khớp với giá trị cần so sánh hay không
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        // kiểm tra data vừa nhập có chứa nội dung trong adapter hay không
        if (filteredlist.isEmpty()) {
        } else {
            // nếu có sẽ add vào classAdapter
            adapterSearch_mechanical.filterList(filteredlist);
            adapterSearch_mechanical.notifyDataSetChanged();
        }
    }
}