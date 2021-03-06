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
import com.example.doanandroid.Adapter.AdapterNotification_Mechanical;
import com.example.doanandroid.Adapter.AdapterRecruit_Mechanical;
import com.example.doanandroid.Adapter.AdapterSearch_Mechanical;
import com.example.doanandroid.Object.MainActivity;
import com.example.doanandroid.Model.ContactMechanical;
import com.example.doanandroid.Model.Mechanical;
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

public class DepartmentMechanicalFragment extends Fragment implements View.OnClickListener {
    TextView txt_contetn;
    RelativeLayout rela_filter;
    Button btn_filter, btn_filter_off, btn_search;

    RecyclerView recyRecruit;
    RecyclerView recyNotification;
    RecyclerView recy_search;
    RecyclerView recy_filter;
    ViewFlipper viewFlipper;
    AdapterRecruit_Mechanical adapterRecruit_mechanical;
    AdapterSearch_Mechanical adapterSearch_mechanical;
    AdapterNotification_Mechanical adapterNotification_mechanical;
    AdapterFilter adapterFilter;
    List<Mechanical> mechanicalList = new ArrayList<>();
    List<Mechanical> mechanicalList_noti = new ArrayList<>();
    List<Mechanical> list_search = new ArrayList<>();
    List<Mechanical> list_filter = new ArrayList<>();
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
        txt_contetn.setOnClickListener(this::onClick);
        view.findViewById(R.id.txt_end_date).setOnClickListener(this::onClick);
        view.findViewById(R.id.txt_start_date).setOnClickListener(this::onClick);
        view.findViewById(R.id.view_more_recruit).setOnClickListener(this);
        view.findViewById(R.id.view_more_noti).setOnClickListener(this);
        view.findViewById(R.id.btn_filter_search).setOnClickListener(this);
        btn_filter.setOnClickListener(this::onClick);
        btn_filter_off.setOnClickListener(this::onClick);
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

    // get data
    private void getDataFireBase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_Mechanical");
        // get data t??? firebase
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
                    MainActivity.dialog.dismiss();
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

                // truy c???p v??o t???ng ph???n t???
//                HashMap<String,Object> hashMap= (HashMap<String, Object>) snapshot.getValue();
                //HashMap n??y s??? c?? k??ch  th?????c b???ng s??? Node con b??n trong node truy v???n
                //m???i ph???n t??? c?? key l?? name ???????c ?????nh ngh??a trong c???u tr??c Json c???a Firebase
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
                setText(view.findViewById(R.id.txt_access_today), "H??m nay : " + access.get(1));
                setText(view.findViewById(R.id.access_month), "Th??ng n??y : " + access.get(0));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // content
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // truy c???p t???ng ph???n t???
                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                setText(txt_contetn, Html.fromHtml(hashMap.get("contents").toString()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // l???ch ti???p sinh vi??n
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
        arrayViewFlipper.add("https://ck.caothang.edu.vn/images/banner/1558893001_gio??i thieu 2 - copy (2).jpg");
        arrayViewFlipper.add("https://ck.caothang.edu.vn/images/banner/1558888296_gi??i thieu 1-4.jpg");
        arrayViewFlipper.add("https://ck.caothang.edu.vn/images/banner/1558893044_gio??i thieu 4 (1).jpg");
        arrayViewFlipper.add("https://ck.caothang.edu.vn/images/banner/1558888827_gi??i thieu 5.jpg");
        arrayViewFlipper.add("https://ck.caothang.edu.vn/images/banner/1558891625_gio??i thieu 3 - copy.jpg");
        arrayViewFlipper.add("https://ck.caothang.edu.vn/images/banner/1544591313_banner-TS - copy(2)-3.jpg");
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
        viewFlipper = view.findViewById(R.id.viewflipper);
        rela_filter = view.findViewById(R.id.linear_filter);
        btn_filter_off = view.findViewById(R.id.btn_filter_off);
        btn_filter = view.findViewById(R.id.btn_filter);
        txt_contetn = view.findViewById(R.id.txt_content);
        txt_contetn.setMaxLines(5);
        txt_contetn.setEllipsize(TextUtils.TruncateAt.END);

        // t??m ki???m
        recy_search = view.findViewById(R.id.list_item);
        adapterSearch_mechanical = new AdapterSearch_Mechanical(getContext(), list_search);
        recy_search.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_search.setAdapter(adapterSearch_mechanical);

        // list tuy???n d???ng
        recyRecruit = view.findViewById(R.id.recyRecruit);
        adapterRecruit_mechanical = new AdapterRecruit_Mechanical(getContext(), mechanicalList);
        recyRecruit.setLayoutManager(new LinearLayoutManager(getContext()));
        recyRecruit.setAdapter(adapterRecruit_mechanical);

        // list ti???p sinh vi??n
        recyNotification = view.findViewById(R.id.recyNotification);
        adapterNotification_mechanical = new AdapterNotification_Mechanical(getContext(), mechanicalList_noti);
        recyNotification.setLayoutManager(new LinearLayoutManager(getContext()));
        recyNotification.setAdapter(adapterNotification_mechanical);

        // filter
        recy_filter = view.findViewById(R.id.list_filter);
        adapterFilter = new AdapterFilter(getContext(), list_filter);
        recy_filter.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_filter.setAdapter(adapterFilter);
    }

    /**
     * T??m ki???m
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

    // T??m ki???m gi?? tr??? theo mssv
    private void filter(String text) {
        // t???o m???t danh s??ch m???ng m???i ????? l???c d??? li???u
        ArrayList<Mechanical> filteredlist = new ArrayList<>();

        // so s??nh c??c ph???n t??? trong adapter
        for (Mechanical item : list_search) {
            // ki???m tra chu???i v???a nh???p c?? kh???p v???i gi?? tr??? c???n so s??nh hay kh??ng
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        // ki???m tra data v???a nh???p c?? ch???a n???i dung trong adapter hay kh??ng
        if (filteredlist.isEmpty()) {
        } else {
            // n???u c?? s??? add v??o classAdapter
            adapterSearch_mechanical.filterList(filteredlist);
            adapterSearch_mechanical.notifyDataSetChanged();
        }
    }

    boolean b = true;
    TextView txt_view_more;
    TextView txt_view_more1;
    TextView txt_start_date, txt_end_date;
    public static int count = -2;
    public static int count_recruit = -2;
    String strStartDate = "";
    String strEndDate = "";

    @Override
    public void onClick(View view) {
        txt_start_date = view.findViewById(R.id.txt_start_date);
        txt_end_date = view.findViewById(R.id.txt_end_date);
        switch (view.getId()) {
            case R.id.txt_content:
                if (b) {
                    txt_contetn.setEllipsize(null);
                    txt_contetn.setMaxLines(txt_contetn.getText().toString().length());
                    b = false;
                } else {
                    txt_contetn.setMaxLines(5);
                    txt_contetn.setEllipsize(TextUtils.TruncateAt.END);
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
            case R.id.view_more_recruit:
                txt_view_more1 = view.findViewById(R.id.view_more_recruit);
                if (txt_view_more1.getText().toString().equals("Xem th??m")) {
                    if (mechanicalList.size() + count_recruit < mechanicalList.size()) {
                        count_recruit++;
                        adapterRecruit_mechanical.notifyDataSetChanged();
                        if (mechanicalList.size() + count_recruit == mechanicalList.size()) {
                            txt_view_more1.setText("Thu nh???");
                        }
                    }
                } else {
                    count_recruit = -2;
                    txt_view_more1.setText("Xem th??m");
                    adapterRecruit_mechanical.notifyDataSetChanged();
                }
                return;
            case R.id.view_more_noti:
                txt_view_more = view.findViewById(R.id.view_more_noti);
                if (txt_view_more.getText().toString().equals("Xem th??m")) {
                    if (mechanicalList_noti.size() + count < mechanicalList_noti.size()) {
                        count++;
                        adapterNotification_mechanical.notifyDataSetChanged();
                        if (mechanicalList_noti.size() + count == mechanicalList_noti.size()) {
                            txt_view_more.setText("Thu nh???");
                        }
                    }
                } else {
                    count = -2;
                    txt_view_more.setText("Xem th??m");
                    adapterNotification_mechanical.notifyDataSetChanged();
                }
                return;
            case R.id.btn_filter_search:
                Toast.makeText(getContext(), strStartDate, Toast.LENGTH_SHORT).show();
                if (!strEndDate.isEmpty() && !strStartDate.isEmpty()) {
                    filterdate(strStartDate, strEndDate);
                }

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
        for (Mechanical item : list_search) {
            String strItem[] = item.getDate().split("\\.");
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
            adapterSearch_mechanical.notifyDataSetChanged();
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