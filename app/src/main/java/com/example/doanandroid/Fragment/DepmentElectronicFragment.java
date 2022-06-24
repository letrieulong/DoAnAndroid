package com.example.doanandroid.Fragment;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Adapter.AdapterFilter;
import com.example.doanandroid.Adapter.AdapterNew_Electronic;
import com.example.doanandroid.Adapter.AdapterNews_Electronic;
import com.example.doanandroid.Adapter.AdapterRecruit_CNTT;
import com.example.doanandroid.Adapter.AdapterSearch_Electronic;
import com.example.doanandroid.Model.Mechanical;
import com.example.doanandroid.Object.MainActivity;
import com.example.doanandroid.Model.Contact;
import com.example.doanandroid.Model.News_Electronic;
import com.example.doanandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class DepmentElectronicFragment extends Fragment implements View.OnClickListener {
    public DepmentElectronicFragment() {
        // Required empty public constructor
    }
    RelativeLayout rela_filter;
    Button btn_filter, btn_filter_off, btn_search;
    RecyclerView recy_filter;
    List<Mechanical> list_filter = new ArrayList<>();
    List<Mechanical> mechanicalList = new ArrayList<>();
    AdapterFilter adapterFilter;

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
        view.findViewById(R.id.view_more_news).setOnClickListener(this);
        view.findViewById(R.id.view_more_new).setOnClickListener(this);
        view.findViewById(R.id.txt_end_date).setOnClickListener(this::onClick);
        view.findViewById(R.id.txt_start_date).setOnClickListener(this::onClick);
        view.findViewById(R.id.btn_filter_search).setOnClickListener(this);
        view.findViewById(R.id.btn_filter).setOnClickListener(this::onClick);
        view.findViewById(R.id.btn_filter_off).setOnClickListener(this::onClick);
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
        rela_filter = view.findViewById(R.id.linear_filter);
        btn_filter_off = view.findViewById(R.id.btn_filter_off);
        btn_filter = view.findViewById(R.id.btn_filter);
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

        // filter
        recy_filter = view.findViewById(R.id.list_filter);
        adapterFilter = new AdapterFilter(getContext(), list_filter);
        recy_filter.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_filter.setAdapter(adapterFilter);

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
                    Mechanical ns = dt.getValue(Mechanical.class);
                    mechanicalList.add(ns);
                    MainActivity.dialog.dismiss();
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

    TextView txt_start_date, txt_end_date;
    String strStartDate = "";
    String strEndDate = "";
    boolean b = true;
    TextView txt_view_more;
    TextView txt_view_more1;
    public static int count = -2;
    public static int count_recruit = -2;
    @Override
    public void onClick(View view) {
        txt_start_date = view.findViewById(R.id.txt_start_date);
        txt_end_date = view.findViewById(R.id.txt_end_date);
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

            case R.id.view_more_news:
                txt_view_more = view.findViewById(R.id.view_more_news);
                if (txt_view_more.getText().toString().equals("Xem thêm")) {
                    if (news_electronics.size() + count < news_electronics.size()) {
                        count++;
                        adapterNews_electronic.notifyDataSetChanged();
                        if (news_electronics.size() + count == news_electronics.size()) {
                            txt_view_more.setText("Thu nhỏ");
                        }
                    }
                } else {
                    count = -2;
                    txt_view_more.setText("Xem thêm");
                    adapterNews_electronic.notifyDataSetChanged();
                }
                return;
            case R.id.view_more_new:
                txt_view_more1 = view.findViewById(R.id.view_more_new);
                if (txt_view_more1.getText().toString().equals("Xem thêm")) {
                    if (news_electronics.size() + count < news_electronics.size()) {
                        count++;
                        adapterNew_electronic.notifyDataSetChanged();
                        if (news_electronics.size() + count == news_electronics.size()) {
                            txt_view_more1.setText("Thu nhỏ");
                        }
                    }
                } else {
                    count = -2;
                    txt_view_more1.setText("Xem thêm");
                    adapterNew_electronic.notifyDataSetChanged();
                }
                return;
            case R.id.txt_start_date:
                calendar(txt_start_date);
                strStartDate = txt_start_date.getText().toString().trim();
                return;
            case R.id.txt_end_date:
                calendar(txt_end_date);
                strEndDate = txt_end_date.getText().toString().trim();
                return;
            case R.id.btn_filter_search:
                Toast.makeText(getContext(), strStartDate, Toast.LENGTH_SHORT).show();
                if (!strEndDate.isEmpty() && !strStartDate.isEmpty()) {
                    filterdate(strStartDate, strEndDate);
                }
                return;
            case R.id.btn_filter:
                rela_filter.setVisibility(View.VISIBLE);
                btn_filter_off.setVisibility(View.VISIBLE);
                btn_filter.setVisibility(View.GONE);
                return;
            case R.id.btn_filter_off:
                rela_filter.setVisibility(View.GONE);
                btn_filter_off.setVisibility(View.GONE);
                btn_filter.setVisibility(View.VISIBLE);
                return;
        }
    }
    // Tìm kiếm giá trị theo mssv
    private void filterdate(String start, String end) {
        String StrStart[] = start.split("-");
        int yearStart = Integer.parseInt(StrStart[2]);
        int monthStart = Integer.parseInt(StrStart[1]);
        String StrEnd[] = end.split("-");
        int yearEnd = Integer.parseInt(StrEnd[2]);
        int monthEnd = Integer.parseInt(StrEnd[1]);
        // tạo một danh sách mảng mới để lọc dữ liệu
        ArrayList<Mechanical> filteredlist = new ArrayList<>();

        // so sánh các phần từ trong adapter
        for (Mechanical item : mechanicalList) {
            String strItem[] = item.getDate().replace("/",".").split("\\.");
//            int yearItem = Integer.parseInt(strItem[2]);
            int monthItem = Integer.parseInt(strItem[1]);
            if (2022 <= 2022 && 2022 >= 2022) {
                // kiểm tra chuỗi vừa nhập có khớp với giá trị cần so sánh hay không
                if (monthStart <= monthItem && monthItem <= monthEnd) {
                    filteredlist.add(item);
                    recy_filter.setVisibility(View.VISIBLE);
                }
            } else {

            }

        }
        // kiểm tra data vừa nhập có chứa nội dung trong adapter hay không
        if (filteredlist.isEmpty()) {
        } else {
            // nếu có sẽ add vào classAdapter
            adapterFilter.filterList(filteredlist);
            adapterFilter.notifyDataSetChanged();
        }
    }

    /**
     * tìm kiếm theo thời gian
     **/
    DatePickerDialog.OnDateSetListener setListener;

    // calendar
    private void calendar(TextView txt) {
        Calendar calendar = Calendar.getInstance();

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int days = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth
                , setListener, year, month, days);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                month = month + 1;
                String date = days + "-" + month + "-" + year;
                txt.setText(date);
            }
        };
    }
}