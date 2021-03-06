package com.example.doanandroid.Fragment;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import com.example.doanandroid.Adapter.AdapterRecruit_CNTT;
import com.example.doanandroid.Adapter.AdapterView_CNTT;
import com.example.doanandroid.Model.Mechanical;
import com.example.doanandroid.Object.DetailPostActivity;
import com.example.doanandroid.Object.MainActivity;
import com.example.doanandroid.Model.CNTT_infor;
import com.example.doanandroid.Model.Infor_All_CNTT;
import com.example.doanandroid.Model.Recruit_CNTT;
import com.example.doanandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DepartmentCNTTFragment extends Fragment implements AdapterRecruit_CNTT.ClickItemPost, AdapterView_CNTT.ClickItemPost, View.OnClickListener {
    public DepartmentCNTTFragment() {
        // Required empty public constructor
    }
    RelativeLayout rela_filter;
    Button btn_filter, btn_filter_off, btn_search;
    RecyclerView recy_filter;
    List<Mechanical> list_filter = new ArrayList<>();
    List<Mechanical> mechanicalList = new ArrayList<>();
    AdapterFilter adapterFilter;

    RecyclerView recyRecruit;
    RecyclerView recyInfor;
    RecyclerView recy_search;
    List<Recruit_CNTT> recruit_cnttList = new ArrayList<>();
    List<Infor_All_CNTT> infor_all_cntts = new ArrayList<>();
    List<Infor_All_CNTT> list_search = new ArrayList<>();
    AdapterRecruit_CNTT adapterRecruit_cntt;
    AdapterView_CNTT adapterView_cntt;
    TextView txt_title_name;
    ImageView img_view;
    ViewFlipper viewFlipper;
    Toolbar toolbar;
    View view;
    TextView txt_content;

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
        view.findViewById(R.id.view_more).setOnClickListener(this);
        view.findViewById(R.id.view_more_recruit).setOnClickListener(this);
        view.findViewById(R.id.txt_content).setOnClickListener(this);
        view.findViewById(R.id.txt_end_date).setOnClickListener(this::onClick);
        view.findViewById(R.id.txt_start_date).setOnClickListener(this::onClick);
        view.findViewById(R.id.btn_filter_search).setOnClickListener(this);
        view.findViewById(R.id.btn_filter).setOnClickListener(this::onClick);
        view.findViewById(R.id.btn_filter_off).setOnClickListener(this::onClick);
        return view;
    }

    private void Actionbar() {
        toolbar = view.findViewById(R.id.toolbar);
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

    // get data
    private void getDataFireBase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_cntt");
        // get data t??? firebase
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
                    Mechanical ns = dt.getValue(Mechanical.class);
                    mechanicalList.add(ns);
                    MainActivity.dialog.dismiss();
                }
                adapterRecruit_cntt.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // get data t??? firebase
        mDatabase.child("contents").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CNTT_infor rs = snapshot.getValue(CNTT_infor.class);
                txt_title_name.setText(Html.fromHtml(rs.getTitle()));
                Glide.with(getContext())
                        .load(rs.getImage())
                        .into(img_view);
                setText(view.findViewById(R.id.txt_phone), rs.getPhone());
                setText(view.findViewById(R.id.txt_email), rs.getEmail());
                setText(view.findViewById(R.id.txt_address), rs.getAddress());
                txt_content.setMaxLines(3);
                txt_content.setEllipsize(TextUtils.TruncateAt.END);
                setText(view.findViewById(R.id.txt_content), rs.getContent());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // get data t??? firebase
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
                    Mechanical ns = dt.getValue(Mechanical.class);
                    mechanicalList.add(ns);
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
        for (int i = 0; i < arrayViewFlipper.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            Glide.with(this).load(arrayViewFlipper.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY); // s??t h??nh ???nh hi???n th??? full;
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_right_in);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_right_out);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    //H??? tr??? ?????i TEXT
    private void setText(final TextView text, final String value) {
        if (text != null) {
            text.setText(value);
        }
    }


    // kh???i t???o c??c control
    private void init() {
        txt_title_name = view.findViewById(R.id.txt_title_name);
        img_view = view.findViewById(R.id.img_view);
        viewFlipper = view.findViewById(R.id.viewflipper);
        txt_content = view.findViewById(R.id.txt_content);

        rela_filter = view.findViewById(R.id.linear_filter);
        btn_filter_off = view.findViewById(R.id.btn_filter_off);
        btn_filter = view.findViewById(R.id.btn_filter);

        // filter
        recy_filter = view.findViewById(R.id.list_filter);
        adapterFilter = new AdapterFilter(getContext(), list_filter);
        recy_filter.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_filter.setAdapter(adapterFilter);

        // list all
        recyInfor = view.findViewById(R.id.recyInfor);
        adapterView_cntt = new AdapterView_CNTT(getContext(), infor_all_cntts, this);
        recyInfor.setLayoutManager(new LinearLayoutManager(getContext()));
        recyInfor.setAdapter(adapterView_cntt);

        // list tuy???n d???ng
        recyRecruit = view.findViewById(R.id.recyRecruit);
        adapterRecruit_cntt = new AdapterRecruit_CNTT(getContext(), recruit_cnttList, this);
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
                    if (newText.equals("")) {
                        recy_search.setVisibility(View.GONE);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    for (Recruit_CNTT rc : recruit_cnttList) {
                        if (rc.getTitle().toLowerCase().contains(query.toLowerCase())) {
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

    // T??m ki???m gi?? tr??? theo mssv
    private void filter(String text) {
        // t???o m???t danh s??ch m???ng m???i ????? l???c d??? li???u
        ArrayList<Infor_All_CNTT> filteredlist = new ArrayList<>();

        // so s??nh c??c ph???n t??? trong adapter
        for (Infor_All_CNTT item : list_search) {
            // ki???m tra chu???i v???a nh???p c?? kh???p v???i gi?? tr??? c???n so s??nh hay kh??ng
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        // ki???m tra data v???a nh???p c?? ch???a n???i dung trong adapter hay kh??ng
        if (filteredlist.isEmpty()) {
        } else {
            // n???u c?? s??? add v??o classAdapter
        }
    }

    @Override
    public void onClickItem(Recruit_CNTT recruit_cntt) {
        Intent i = new Intent(getContext(), DetailPostActivity.class);
        recruit_cntt.setRecruit(true);
        i.putExtra("recruit", recruit_cntt);
        startActivity(i);
    }


    @Override
    public void onClickItemInfor(Infor_All_CNTT infor_all_cntt) {
        Intent i = new Intent(getContext(), DetailPostActivity.class);
        Recruit_CNTT recruit_cntt = new Recruit_CNTT();
        recruit_cntt.setRecruit(false);
        recruit_cntt.setId(infor_all_cntt.getId());
        recruit_cntt.setContent(infor_all_cntt.getContent());
        recruit_cntt.setTitle(infor_all_cntt.getTitle());
//        recruit_cntt.setLike(infor_all_cntt.getLike());
//        recruit_cntt.setView(infor_all_cntt.getView());
        i.putExtra("recruit", recruit_cntt);
        startActivity(i);
    }

    TextView txt_view_more;
    TextView txt_view_more1;
    public static int count = -2;
    public static int count_recruit = -2;
    boolean b = true;

    TextView txt_start_date, txt_end_date;
    String strStartDate = "";
    String strEndDate = "";
    @Override
    public void onClick(View view) {
        txt_start_date = view.findViewById(R.id.txt_start_date);
        txt_end_date = view.findViewById(R.id.txt_end_date);
        switch (view.getId()) {
            case R.id.view_more:
                txt_view_more = view.findViewById(R.id.view_more);
                if (txt_view_more.getText().toString().equals("Xem th??m")) {
                    if (infor_all_cntts.size() + count < infor_all_cntts.size()) {
                        count++;
                        adapterView_cntt.notifyDataSetChanged();
                        if (infor_all_cntts.size() + count == infor_all_cntts.size()) {
                            txt_view_more.setText("Thu nh???");
                        }
                    }
                } else {
                    count = -2;
                    txt_view_more.setText("Xem th??m");
                    adapterView_cntt.notifyDataSetChanged();
                }
                return;

            case R.id.view_more_recruit:
                txt_view_more1 = view.findViewById(R.id.view_more_recruit);
                if (txt_view_more1.getText().toString().equals("Xem th??m")) {
                    if (recruit_cnttList.size() + count_recruit < recruit_cnttList.size()) {
                        count_recruit++;
                        adapterRecruit_cntt.notifyDataSetChanged();
                        if (recruit_cnttList.size() + count_recruit == recruit_cnttList.size()) {
                            txt_view_more1.setText("Thu nh???");
                        }
                    }
                } else {
                    count_recruit = -2;
                    txt_view_more1.setText("Xem th??m");
                    adapterRecruit_cntt.notifyDataSetChanged();
                }
                return;
            case R.id.txt_content:
                if (b) {
                    txt_content.setEllipsize(null);
                    txt_content.setMaxLines(txt_content.getText().toString().length());
                    b = false;
                } else {
                    txt_content.setMaxLines(5);
                    txt_content.setEllipsize(TextUtils.TruncateAt.END);
                    b = true;
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
    // T??m ki???m gi?? tr??? theo mssv
    private void filterdate(String start, String end) {
        String StrStart[] = start.split("-");
        int yearStart = Integer.parseInt(StrStart[2]);
        int monthStart = Integer.parseInt(StrStart[1]);
        String StrEnd[] = end.split("-");
        int yearEnd = Integer.parseInt(StrEnd[2]);
        int monthEnd = Integer.parseInt(StrEnd[1]);
        // t???o m???t danh s??ch m???ng m???i ????? l???c d??? li???u
        ArrayList<Mechanical> filteredlist = new ArrayList<>();

        // so s??nh c??c ph???n t??? trong adapter
        for (Mechanical item : mechanicalList) {
            String strItem[] = item.getDate().replace("/",".").split("\\.");
//            int yearItem = Integer.parseInt(strItem[2]);
            int monthItem = Integer.parseInt(strItem[1]);
            if (2022 <= 2022 && 2022 >= 2022) {
                // ki???m tra chu???i v???a nh???p c?? kh???p v???i gi?? tr??? c???n so s??nh hay kh??ng
                if (monthStart <= monthItem && monthItem <= monthEnd) {
                    filteredlist.add(item);
                    recy_filter.setVisibility(View.VISIBLE);
                }
            } else {

            }

        }
        // ki???m tra data v???a nh???p c?? ch???a n???i dung trong adapter hay kh??ng
        if (filteredlist.isEmpty()) {
        } else {
            // n???u c?? s??? add v??o classAdapter
            adapterFilter.filterList(filteredlist);
            adapterFilter.notifyDataSetChanged();
        }
    }

    /**
     * t??m ki???m theo th???i gian
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