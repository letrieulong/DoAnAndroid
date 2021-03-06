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

import android.text.TextPaint;
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
import com.example.doanandroid.Adapter.AdapterNew_Admin;
import com.example.doanandroid.Adapter.AdapterPolicy_Admin;
import com.example.doanandroid.Adapter.AdapterRecruit_Admin;
import com.example.doanandroid.Adapter.AdapterSearch_AdmininS;
import com.example.doanandroid.Model.Mechanical;
import com.example.doanandroid.Model.Tuition;
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
import java.util.Calendar;
import java.util.List;


public class RoomAdminiStrativeFragment extends Fragment implements View.OnClickListener {

    public RoomAdminiStrativeFragment() {
        // Required empty public constructor
    }
    RelativeLayout rela_filter;
    Button btn_filter, btn_filter_off, btn_search;
    RecyclerView recy_filter;
    List<Mechanical> list_filter = new ArrayList<>();
    List<Mechanical> mechanicalList = new ArrayList<>();

    View view;
    ViewFlipper viewFlipper;
    RecyclerView recy_search;
    AdapterRecruit_Admin adapterRecruit_admin;
    AdapterPolicy_Admin adapterPolicy_admin;
    AdapterSearch_AdmininS adapterSearch_admininS;
    AdapterNew_Admin adapterNew_admin;
    AdapterFilter adapterFilter;

    List<Recruit_Admin> new_List = new ArrayList<>();
    List<Recruit_Admin> new_List1 = new ArrayList<>();
    List<Recruit_Admin> recruit_adminList = new ArrayList<>();
    List<Policy> policyList = new ArrayList<>();
    List<Recruit_Admin> list_search = new ArrayList<>();
    RecyclerView recy_recuirt;
    RecyclerView recy_policy;
    RecyclerView recy_new;
    int arr[] = {64, 25, 12, 22, 11};

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
        view.findViewById(R.id.view_more_New).setOnClickListener(this);
        view.findViewById(R.id.txt_end_date).setOnClickListener(this::onClick);
        view.findViewById(R.id.txt_start_date).setOnClickListener(this::onClick);
        view.findViewById(R.id.btn_filter_search).setOnClickListener(this);
        view.findViewById(R.id.btn_filter).setOnClickListener(this::onClick);
        view.findViewById(R.id.btn_filter_off).setOnClickListener(this::onClick);
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
        rela_filter = view.findViewById(R.id.linear_filter);
        btn_filter_off = view.findViewById(R.id.btn_filter_off);
        btn_filter = view.findViewById(R.id.btn_filter);
        viewFlipper = view.findViewById(R.id.viewflipper);
        // t??m ki???m
        recy_search = view.findViewById(R.id.list_item);
        adapterSearch_admininS = new AdapterSearch_AdmininS(getContext(), list_search);
        recy_search.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_search.setAdapter(adapterSearch_admininS);

        // tuy???n d???ng
        recy_recuirt = view.findViewById(R.id.recyRecruit);
        adapterRecruit_admin = new AdapterRecruit_Admin(getContext(), recruit_adminList);
        recy_recuirt.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_recuirt.setAdapter(adapterRecruit_admin);

        // ch??? ????? ch??nh s??ch
        recy_policy = view.findViewById(R.id.recyPolicy);
        adapterPolicy_admin = new AdapterPolicy_Admin(getContext(), policyList);
        recy_policy.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_policy.setAdapter(adapterPolicy_admin);

        // m???i
        recy_new = view.findViewById(R.id.recyNew);
        adapterNew_admin = new AdapterNew_Admin(getContext(), new_List);
        recy_new.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_new.setAdapter(adapterNew_admin);

        // filter
        recy_filter = view.findViewById(R.id.list_filter);
        adapterFilter = new AdapterFilter(getContext(), list_filter);
        recy_filter.setLayoutManager(new LinearLayoutManager(getContext()));
        recy_filter.setAdapter(adapterFilter);

    }

    //H??? tr??? ?????i TEXT
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

    //     get data
    private void getDataFireBase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_adminiStrative");
        // get data t??? firebase
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

                    Mechanical ns = dt.getValue(Mechanical.class);
                    mechanicalList.add(ns);
                    MainActivity.dialog.dismiss();
                }
                adapterSearch_admininS.notifyDataSetChanged();
                adapterRecruit_admin.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // get data t??? firebase
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
                    Mechanical ns = dt.getValue(Mechanical.class);
                    mechanicalList.add(ns);
                    MainActivity.dialog.dismiss();
                }
                adapterSearch_admininS.notifyDataSetChanged();
                adapterPolicy_admin.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // get data t??? firebase
        mDatabase.child("list_news").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                new_List.clear();
                List<String> list = new ArrayList<>();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    list.add(dt.getKey());
                    Recruit_Admin rs = dt.getValue(Recruit_Admin.class);
                    new_List.add(rs);
                    MainActivity.dialog.dismiss();
                }
                adapterSearch_admininS.notifyDataSetChanged();
                adapterPolicy_admin.notifyDataSetChanged();
                adapterNew_admin.notifyDataSetChanged();
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
        mDatabase.child("list_recruit").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                new_List1.clear();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    Recruit_Admin rs = dt.getValue(Recruit_Admin.class);
                    new_List1.add(rs);
                }
                List<Recruit_Admin> recruit_adminList = new ArrayList<>();
                mDatabase.child("list_policy").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String str[] = new String[0];
                        Recruit_Admin rs = null;
                        for (DataSnapshot data : snapshot.getChildren()) {
                            rs = data.getValue(Recruit_Admin.class);
                            recruit_adminList.add(rs);
                            str = rs.getDate().split("\\.");
                        }
                        mDatabase.child("list_news").removeValue();
                        int year = Integer.parseInt(str[2]);
                        for (int j = 0; j < new_List1.size(); j++) {
                            String strrr[] = new_List1.get(j).getDate().split("\\.");
                            int yearrr = Integer.parseInt(strrr[2]);
                            if (year > yearrr){
                                String userId = mDatabase.push().getKey();
                                mDatabase.child("list_news/" + userId).setValue(recruit_adminList.get(j));
                            }else {
                                String userId = mDatabase.push().getKey();
                                mDatabase.child("list_news/" + userId).setValue(new_List1.get(j));
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addnew (){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_adminiStrative");
        for (int i = 0; i < list_search.size() - 1; i++) {
            String str[] = list_search.get(i).getDate().split("\\.");
            int year = Integer.parseInt(str[2]);
            for (int j = i + 1; j < list_search.size(); j++) {
                String strrr[] = list_search.get(j).getDate().split("\\.");
                int yearrr = Integer.parseInt(strrr[2]);
                if (year > yearrr){
                    String userId = mDatabase.push().getKey();
                    mDatabase.child("list_news/" + userId).setValue(list_search.get(i));
                }else {
                    String userId = mDatabase.push().getKey();
                    mDatabase.child("list_news/" + userId).setValue(list_search.get(i));
                }
            }
        }
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

    // T??m ki???m gi?? tr??? theo mssv
    private void filter(String text) {
        // t???o m???t danh s??ch m???ng m???i ????? l???c d??? li???u
        ArrayList<Recruit_Admin> filteredlist = new ArrayList<>();

        // so s??nh c??c ph???n t??? trong adapter
        for (Recruit_Admin item : list_search) {
            // ki???m tra chu???i v???a nh???p c?? kh???p v???i gi?? tr??? c???n so s??nh hay kh??ng
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        // ki???m tra data v???a nh???p c?? ch???a n???i dung trong adapter hay kh??ng
        if (filteredlist.isEmpty()) {
        } else {
            // n???u c?? s??? add v??o classAdapter
            adapterSearch_admininS.filterList(filteredlist);
            adapterSearch_admininS.notifyDataSetChanged();
        }
    }

    TextView txt_view_more;
    TextView txt_view_more1;
    TextView txt_view_more2;
    public static int count = -2;
    public static int count_recruit = -2;
    public static int count_new = -2;
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
                    if (policyList.size() + count < policyList.size()) {
                        count++;
                        adapterPolicy_admin.notifyDataSetChanged();
                        if (policyList.size() + count == policyList.size()) {
                            txt_view_more.setText("Thu nh???");
                        }
                    }
                } else {
                    count = -2;
                    txt_view_more.setText("Xem th??m");
                    adapterPolicy_admin.notifyDataSetChanged();
                }
                return;

            case R.id.view_more_recruit:
                txt_view_more1 = view.findViewById(R.id.view_more_recruit);
                if (txt_view_more1.getText().toString().equals("Xem th??m")) {
                    if (recruit_adminList.size() + count_recruit < recruit_adminList.size()) {
                        count_recruit++;
                        adapterRecruit_admin.notifyDataSetChanged();
                        if (recruit_adminList.size() + count_recruit == recruit_adminList.size()) {
                            txt_view_more1.setText("Thu nh???");
                        }
                    }
                } else {
                    count_recruit = -2;
                    txt_view_more1.setText("Xem th??m");
                    adapterRecruit_admin.notifyDataSetChanged();
                }
                return;
            case R.id.view_more_New:
                txt_view_more2 = view.findViewById(R.id.view_more_New);
                if (txt_view_more2.getText().toString().equals("Xem th??m")) {
                    if (new_List.size() + count_new < new_List.size()) {
                        count_new++;
                        adapterNew_admin.notifyDataSetChanged();
                        if (recruit_adminList.size() + count_new == new_List.size()) {
                            txt_view_more2.setText("Thu nh???");
                        }
                    }
                } else {
                    count_new = -2;
                    txt_view_more2.setText("Xem th??m");
                    adapterNew_admin.notifyDataSetChanged();
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