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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Adapter.AdapterNew_Trainning;
import com.example.doanandroid.Adapter.AdapterSearch_Trainning;
import com.example.doanandroid.Object.DetailPostRoomTraining;
import com.example.doanandroid.Object.MainActivity;
import com.example.doanandroid.Model.ContactMechanical;
import com.example.doanandroid.Model.ContentLink;
import com.example.doanandroid.Model.Mechanical;
import com.example.doanandroid.Model.New_Tranning;
import com.example.doanandroid.Object.TKBActivity;
import com.example.doanandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomTrainingFragment extends Fragment implements AdapterNew_Trainning.ClickItemPost, View.OnClickListener {

    public RoomTrainingFragment() {
        // Required empty public constructor
    }

    Button btn_tkb, btn_cla_Exam, btn_cla_train;
    ViewFlipper viewFlipper;
    RecyclerView recyNew;
    RecyclerView recy_search;
    View view;
    List<ContentLink> contentLinkList = new ArrayList<>();
    List<Mechanical> mechanicalList = new ArrayList<>();
    List<New_Tranning> new_tranningList = new ArrayList<>();
    List<New_Tranning> list_search = new ArrayList<>();
    AdapterNew_Trainning adapterNew_trainning;
    AdapterSearch_Trainning adapterSearch_trainning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_room_training, container, false);
        init();
        Acviewflipper();
        getDataFireBase();
        setHasOptionsMenu(true);
        Actionbar();
        view.findViewById(R.id.btn_tkb).setOnClickListener(this::onClick);
        view.findViewById(R.id.btn_calen).setOnClickListener(this::onClick);
        view.findViewById(R.id.btn_calenTrain).setOnClickListener(this::onClick);
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
        adapterSearch_trainning = new AdapterSearch_Trainning(getContext(), list_search);
        recy_search.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_search.setAdapter(adapterSearch_trainning);

        recyNew = view.findViewById(R.id.recynew);
        adapterNew_trainning = new AdapterNew_Trainning(getContext(), new_tranningList, this);
        recyNew.setLayoutManager(new LinearLayoutManager(getContext()));
        recyNew.setAdapter(adapterNew_trainning);
    }

    //     get data
    private void getDataFireBase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_training");

        mDatabase.child("list_new").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> list = new ArrayList<>();
                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                //HashMap này sẽ có kích  thước bằng số Node con bên trong node truy vấn
                //mỗi phần tử có key là name được định nghĩa trong cấu trúc Json của Firebase
//                String id = hashMap.get("id").toString();
                new_tranningList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    New_Tranning n = snap.getValue(New_Tranning.class);
                    new_tranningList.add(n);
                    list_search.add(n);
                }
                adapterSearch_trainning.notifyDataSetChanged();
                adapterNew_trainning.notifyDataSetChanged();
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
                    adapterSearch_trainning.notifyDataSetChanged();
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
        for (New_Tranning item : list_search) {
            // kiểm tra chuỗi vừa nhập có khớp với giá trị cần so sánh hay không
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        // kiểm tra data vừa nhập có chứa nội dung trong adapter hay không
        if (filteredlist.isEmpty()) {
        } else {
            // nếu có sẽ add vào classAdapter
            adapterSearch_trainning.filterList(filteredlist);
            adapterSearch_trainning.notifyDataSetChanged();
        }
    }


    @Override
    public void onClickItem(New_Tranning new_tranning) {
        Intent i = new Intent(getContext(), DetailPostRoomTraining.class);
        new_tranning.setRecruit(true);
        i.putExtra("recruit", new_tranning);
        startActivity(i);
    }

    public static String str;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tkb:
                str = "THỜI KHÓA BIỂU";
                startActivity(new Intent(getActivity(), TKBActivity.class));
                return;
            case R.id.btn_calen:
                str = "LỊCH THI";
                startActivity(new Intent(getActivity(), TKBActivity.class));
                return;
            case R.id.btn_calenTrain:
                str = "LỊCH ĐÀO TẠO";
                startActivity(new Intent(getActivity(), TKBActivity.class));
                return;

        }
    }
}