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
import com.example.doanandroid.Adapter.AdapterNew_Admin;
import com.example.doanandroid.Adapter.AdapterPolicy_Admin;
import com.example.doanandroid.Adapter.AdapterRecruit_Admin;
import com.example.doanandroid.Adapter.AdapterSearch_AdmininS;
import com.example.doanandroid.Object.MainActivity;
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


public class RoomAdminiStrativeFragment extends Fragment implements View.OnClickListener {

    public RoomAdminiStrativeFragment() {
        // Required empty public constructor
    }

    View view;
    ViewFlipper viewFlipper;
    RecyclerView recy_search;
    AdapterRecruit_Admin adapterRecruit_admin;
    AdapterPolicy_Admin adapterPolicy_admin;
    AdapterSearch_AdmininS adapterSearch_admininS;
    AdapterNew_Admin adapterNew_admin;
    List<New_Tranning> new_List = new ArrayList<>();
    List<Recruit_Admin> recruit_adminList = new ArrayList<>();
    List<Policy> policyList = new ArrayList<>();
    List<Recruit_Admin> list_search = new ArrayList<>();
    RecyclerView recy_recuirt;
    RecyclerView recy_policy;
    RecyclerView recy_new;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_room_admini_strative, container, false);
        init();
        Acviewflipper();
        getDataFireBase();
        setHasOptionsMenu(true);
        Actionbar();
        view.findViewById(R.id.view_more).setOnClickListener(this);
        view.findViewById(R.id.view_more_recruit).setOnClickListener(this);
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

    private void init() {
        viewFlipper = view.findViewById(R.id.viewflipper);
        // tìm kiếm
        recy_search = view.findViewById(R.id.list_item);
        adapterSearch_admininS = new AdapterSearch_AdmininS(getContext(), list_search);
        recy_search.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_search.setAdapter(adapterSearch_admininS);

        // tuyển dụng
        recy_recuirt = view.findViewById(R.id.recyRecruit);
        adapterRecruit_admin = new AdapterRecruit_Admin(getContext(), recruit_adminList);
        recy_recuirt.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_recuirt.setAdapter(adapterRecruit_admin);

        // chế độ chính sách
        recy_policy = view.findViewById(R.id.recyPolicy);
        adapterPolicy_admin = new AdapterPolicy_Admin(getContext(), policyList);
        recy_policy.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_policy.setAdapter(adapterPolicy_admin);

        // mới
        recy_new = view.findViewById(R.id.recyNew);
        adapterNew_admin = new AdapterNew_Admin(getContext(), new_List);
        recy_new.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_new.setAdapter(adapterNew_admin);


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
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_adminiStrative");
        // get data từ firebase
        mDatabase.child("list_recruit").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recruit_adminList.clear();
                List<String> list = new ArrayList<>();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    list.add(dt.getKey());
                    Recruit_Admin rs = dt.getValue(Recruit_Admin.class);
                    recruit_adminList.add(rs);
                    list_search.add(rs);
                }
                adapterSearch_admininS.notifyDataSetChanged();
                adapterRecruit_admin.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // get data từ firebase
        mDatabase.child("list_policy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                policyList.clear();
                List<String> list = new ArrayList<>();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    list.add(dt.getKey());
                    Policy rs = dt.getValue(Policy.class);
                    policyList.add(rs);
                    Recruit_Admin rss = dt.getValue(Recruit_Admin.class);
                    list_search.add(rss);
                }
                adapterSearch_admininS.notifyDataSetChanged();
                adapterPolicy_admin.notifyDataSetChanged();
                adapterPolicy_admin.notifyDataSetChanged();
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
                    recy_search.setVisibility(View.VISIBLE);
                    if (newText.equals("")) {
                        recy_search.setVisibility(View.GONE);
                    }
                    adapterSearch_admininS.notifyDataSetChanged();
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
        ArrayList<Recruit_Admin> filteredlist = new ArrayList<>();

        // so sánh các phần từ trong adapter
        for (Recruit_Admin item : list_search) {
            // kiểm tra chuỗi vừa nhập có khớp với giá trị cần so sánh hay không
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        // kiểm tra data vừa nhập có chứa nội dung trong adapter hay không
        if (filteredlist.isEmpty()) {
        } else {
            // nếu có sẽ add vào classAdapter
            adapterSearch_admininS.filterList(filteredlist);
            adapterSearch_admininS.notifyDataSetChanged();
        }
    }

    TextView txt_view_more;
    TextView txt_view_more1;
    public static int count = -2;
    public static int count_recruit = -2;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_more:
                txt_view_more = view.findViewById(R.id.view_more);
                if (txt_view_more.getText().toString().equals("Xem thêm")) {
                    if (policyList.size() + count < policyList.size()) {
                        count++;
                        adapterPolicy_admin.notifyDataSetChanged();
                        if (policyList.size() + count == policyList.size()) {
                            txt_view_more.setText("Thu nhỏ");
                        }
                    }
                } else {
                    count = -2;
                    txt_view_more.setText("Xem thêm");
                    adapterPolicy_admin.notifyDataSetChanged();
                }
                return;

            case R.id.view_more_recruit:
                txt_view_more1 = view.findViewById(R.id.view_more_recruit);
                if (txt_view_more1.getText().toString().equals("Xem thêm")) {
                    if (recruit_adminList.size() + count_recruit < recruit_adminList.size()) {
                        count_recruit++;
                        adapterRecruit_admin.notifyDataSetChanged();
                        if (recruit_adminList.size() + count_recruit == recruit_adminList.size()) {
                            txt_view_more1.setText("Thu nhỏ");
                        }
                    }
                } else {
                    count_recruit = -2;
                    txt_view_more1.setText("Xem thêm");
                    adapterRecruit_admin.notifyDataSetChanged();
                }
                return;
        }
    }
}