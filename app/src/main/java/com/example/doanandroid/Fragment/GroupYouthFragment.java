package com.example.doanandroid.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.doanandroid.Adapter.AdapterGroup_New_update;
import com.example.doanandroid.Adapter.AdapterGroup_SV;
import com.example.doanandroid.Adapter.AdapterGroup_Search;
import com.example.doanandroid.Adapter.AdapterGroup_Youth;
import com.example.doanandroid.Object.Doan_HoiActivity;
import com.example.doanandroid.Object.MainActivity;
import com.example.doanandroid.Model.ContactMechanical;
import com.example.doanandroid.Model.New_Tranning;
import com.example.doanandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GroupYouthFragment extends Fragment implements AdapterGroup_Youth.ClickItemPost, AdapterGroup_New_update.ClickItemNewUpdate, AdapterGroup_SV.ClickItemSV, View.OnClickListener {
    public GroupYouthFragment() {
        // Required empty public constructor
    }

    View view;
    ViewFlipper viewFlipper;
    AdapterGroup_SV adapterGroup_sv;
    AdapterGroup_Youth adapterGroup_youth;
    AdapterGroup_New_update adapterGroup_new_update;
    AdapterGroup_Search adapterGroup_search;
    List<New_Tranning> new_List_sv = new ArrayList<>();
    List<New_Tranning> new_List_youth = new ArrayList<>();
    List<New_Tranning> new_List_update = new ArrayList<>();
    List<New_Tranning> new_List_search = new ArrayList<>();
    RecyclerView recy_youth;
    RecyclerView recy_group_sv;
    RecyclerView recy_new_update;
    RecyclerView recy_list_item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_group_youth, container, false);
        init();
        Acviewflipper();
        getDataFireBase();
        setHasOptionsMenu(true);
        Actionbar();
        view.findViewById(R.id.view_more_youth).setOnClickListener(this::onClick);
        view.findViewById(R.id.view_more_sv).setOnClickListener(this::onClick);
        view.findViewById(R.id.view_more_new).setOnClickListener(this::onClick);

        return view;
    }

    private void Actionbar() {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    // khởi tạo các control
    private void init() {
        viewFlipper = view.findViewById(R.id.viewflipper);

        // tìm kiếm
        recy_list_item = view.findViewById(R.id.list_item);
        adapterGroup_search = new AdapterGroup_Search(getContext(), new_List_search);
        recy_list_item.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_list_item.setAdapter(adapterGroup_search);

        // hội sinh viên
        recy_group_sv = view.findViewById(R.id.recygroupSV);
        adapterGroup_sv = new AdapterGroup_SV(getContext(), new_List_sv, this);
        recy_group_sv.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_group_sv.setAdapter(adapterGroup_sv);

        // đoàn thanh niên
        recy_youth = view.findViewById(R.id.recyyouth);
        adapterGroup_youth = new AdapterGroup_Youth(getContext(), new_List_youth, this);
        recy_youth.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_youth.setAdapter(adapterGroup_youth);

        // tin mới
        recy_new_update = view.findViewById(R.id.recy_news_update);
        adapterGroup_new_update = new AdapterGroup_New_update(getContext(), new_List_update, this);
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
                    new_List_search.add(rs);
                    MainActivity.dialog.dismiss();
                }
                adapterGroup_search.notifyDataSetChanged();
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
                    new_List_search.add(rs);
                }
                adapterGroup_search.notifyDataSetChanged();
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

    /**
     * Tìm kiếm
     **/
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
                    recy_list_item.setVisibility(View.VISIBLE);
                    adapterGroup_search.notifyDataSetChanged();
                    if (newText.equals("")) {
                        recy_list_item.setVisibility(View.GONE);
                    }
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
        ArrayList<New_Tranning> filteredlist = new ArrayList<>();

        // so sánh các phần từ trong adapter
        for (New_Tranning item : new_List_search) {
            // kiểm tra chuỗi vừa nhập có khớp với giá trị cần so sánh hay không
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        // kiểm tra data vừa nhập có chứa nội dung trong adapter hay không
        if (filteredlist.isEmpty()) {
        } else {
            // nếu có sẽ add vào classAdapter
            adapterGroup_search.filterList(filteredlist);
            adapterGroup_search.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickItem(New_Tranning new_tranning) {
        Intent i = new Intent(getContext(), Doan_HoiActivity.class);
        new_tranning.setTypePost(0);
        i.putExtra("recruit", new_tranning);
        startActivity(i);
    }

    @Override
    public void onClickItemNewUpdate(New_Tranning new_tranning) {
        Intent i = new Intent(getContext(), Doan_HoiActivity.class);
        new_tranning.setTypePost(1);
        i.putExtra("recruit", new_tranning);
        startActivity(i);
    }

    @Override
    public void onClickItemSV(New_Tranning new_tranning) {
        Intent i = new Intent(getContext(), Doan_HoiActivity.class);
        new_tranning.setTypePost(2);
        i.putExtra("recruit", new_tranning);
        startActivity(i);
    }

    TextView txt_view_youth;
    TextView txt_view_sv;
    TextView txt_view_new;
    public static int count_youth = -2;
    public static int count_sv = -2;
    public static int count_new = -2;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_more_sv:
                txt_view_sv = view.findViewById(R.id.view_more_sv);
                if (txt_view_sv.getText().toString().equals("Xem thêm")) {
                    if (new_List_sv.size() + count_sv < new_List_sv.size()) {
                        count_sv++;
                        adapterGroup_sv.notifyDataSetChanged();
                        if (new_List_sv.size() + count_sv == new_List_sv.size()) {
                            txt_view_sv.setText("Thu nhỏ");
                        }
                    } else {
                        count_sv = -3;
                        adapterGroup_sv.notifyDataSetChanged();
                    }
                } else {
                    count_sv = -3;
                    txt_view_sv.setText("Xem thêm");
                    adapterGroup_sv.notifyDataSetChanged();
                }
                return;
            case R.id.view_more_youth:
                txt_view_youth = view.findViewById(R.id.view_more_youth);
                if (txt_view_youth.getText().toString().equals("Xem thêm")) {
                    if (new_List_youth.size() + count_youth < new_List_youth.size()) {
                        count_youth++;
                        adapterGroup_youth.notifyDataSetChanged();
                        if (new_List_youth.size() + count_youth == new_List_youth.size()) {
                            txt_view_youth.setText("Thu nhỏ");
                        }
                    } else {
                        count_youth = -3;
                        adapterGroup_youth.notifyDataSetChanged();
                    }
                } else {
                    count_youth = -3;
                    txt_view_youth.setText("Xem thêm");
                    adapterGroup_youth.notifyDataSetChanged();
                }
                return;
            case R.id.view_more_new:
                txt_view_new = view.findViewById(R.id.view_more_new);
                if (txt_view_new.getText().toString().equals("Xem thêm")) {
                    if (new_List_update.size() + count_new < new_List_update.size()) {
                        count_new++;
                        adapterGroup_new_update.notifyDataSetChanged();
                        if (new_List_update.size() + count_new == new_List_update.size()) {
                            txt_view_new.setText("Thu nhỏ");
                        }
                    } else {
                        count_new = -3;
                        adapterGroup_new_update.notifyDataSetChanged();
                    }
                } else {
                    count_new = -3;
                    txt_view_new.setText("Xem thêm");
                    adapterGroup_new_update.notifyDataSetChanged();
                }
                return;
        }
    }
}