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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Adapter.AdapterGroup_Search;
import com.example.doanandroid.Adapter.AdapterNew_Electronic;
import com.example.doanandroid.Adapter.AdapterNews_Electronic;
import com.example.doanandroid.Adapter.AdapterSearch_CNTT;
import com.example.doanandroid.Adapter.AdapterSearch_Electronic;
import com.example.doanandroid.Adapter.AdapterView_CNTT;
import com.example.doanandroid.MainActivity;
import com.example.doanandroid.Model.CNTT_infor;
import com.example.doanandroid.Model.Contact;
import com.example.doanandroid.Model.Infor_All_CNTT;
import com.example.doanandroid.Model.New_Tranning;
import com.example.doanandroid.Model.News_Electronic;
import com.example.doanandroid.Model.Recruit_CNTT;
import com.example.doanandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DepmentElectronicFragment extends Fragment implements View.OnClickListener {
    public DepmentElectronicFragment() {
        // Required empty public constructor
    }
    TextView txt_contetn;

    RecyclerView recyNews;
    RecyclerView recyNew;
    RecyclerView recy_search;
    ViewFlipper viewFlipper;
    AdapterSearch_Electronic adapterSearch_electronic;
    AdapterNews_Electronic adapterNews_electronic;
    AdapterNew_Electronic adapterNew_electronic;
    List<News_Electronic> news_electronics = new ArrayList<>();
    List<News_Electronic> list_Search = new ArrayList<>();
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_depment_electronic, container, false);
        init();
        Acviewflipper();
        getDataFireBase();
        setHasOptionsMenu(true);
        Actionbar();
        txt_contetn.setOnClickListener(this::onClick);
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

    private void init() {
        viewFlipper = view.findViewById(R.id.viewflipper);
        txt_contetn = view.findViewById(R.id.txt_content);
        txt_contetn.setMaxLines(5);
        txt_contetn.setEllipsize(TextUtils.TruncateAt.END);

        // tìm kiếm
        recy_search = view.findViewById(R.id.list_item);
        adapterSearch_electronic = new AdapterSearch_Electronic(getContext(), list_Search);
        recy_search.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_search.setAdapter(adapterSearch_electronic);

        // list new
        recyNew = view.findViewById(R.id.recynew);
        adapterNew_electronic = new AdapterNew_Electronic(getContext(), news_electronics);
        recyNew.setLayoutManager(new LinearLayoutManager(getContext()));
        recyNew.setAdapter(adapterNew_electronic);

        // list news
        recyNews = view.findViewById(R.id.recyNews);
        adapterNews_electronic = new AdapterNews_Electronic(getContext(), news_electronics);
        recyNews.setLayoutManager(new LinearLayoutManager(getContext()));
        recyNews.setAdapter(adapterNews_electronic);
    }

    // banner
    private void Acviewflipper() {
        ArrayList<String> arrayViewFlipper = new ArrayList<>();
        arrayViewFlipper.add("https://ddt.caothang.edu.vn/images/banner/1_10dc068cf61b0c45550a.jpg");
        arrayViewFlipper.add("https://ddt.caothang.edu.vn/images/banner/1641200410_Untitled-1.jpg");
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

    // get data
    private void getDataFireBase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_electronic");
        // get data từ firebase
        mDatabase.child("list_news").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("asd",snapshot.getValue().toString());
                news_electronics.clear();
                List<String> list = new ArrayList<>();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    list.add(dt.getKey());
                    News_Electronic rs = dt.getValue(News_Electronic.class);
                    news_electronics.add(rs);
                    list_Search.add(rs);
                }
                adapterSearch_electronic.notifyDataSetChanged();
                adapterNew_electronic.notifyDataSetChanged();
                adapterNews_electronic.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // get data từ firebase
        mDatabase.child("contact").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Contact rs = snapshot.getValue(Contact.class);

                setText(view.findViewById(R.id.txt_phone),rs.getPhone());
                setText(view.findViewById(R.id.txt_email),rs.getEmail());
                setText(view.findViewById(R.id.txt_address),rs.getAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("contents").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                setText(txt_contetn,Html.fromHtml(hashMap.get("content").toString()).toString());
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
                    adapterSearch_electronic.notifyDataSetChanged();
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
        ArrayList<News_Electronic> filteredlist = new ArrayList<>();

        // so sánh các phần từ trong adapter
        for (News_Electronic item : list_Search) {
            // kiểm tra chuỗi vừa nhập có khớp với giá trị cần so sánh hay không
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        // kiểm tra data vừa nhập có chứa nội dung trong adapter hay không
        if (filteredlist.isEmpty()) {
        } else {
            // nếu có sẽ add vào classAdapter
            adapterSearch_electronic.filterList(filteredlist);
            adapterSearch_electronic.notifyDataSetChanged();
        }
    }

    boolean b = true;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_content:
                if (b){
                    txt_contetn.setEllipsize(null);
                    txt_contetn.setMaxLines(txt_contetn.getText().toString().length());
                    b = false;
                }else {
                    txt_contetn.setMaxLines(5);
                    txt_contetn.setEllipsize(TextUtils.TruncateAt.END);
                    b = true;
                }
                return;
        }
    }
}