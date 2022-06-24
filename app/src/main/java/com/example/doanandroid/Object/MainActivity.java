package com.example.doanandroid.Object;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.doanandroid.Fragment.CLB_IT_Fragment;
import com.example.doanandroid.Fragment.DepartmentCNTTFragment;
import com.example.doanandroid.Fragment.DepartmentMechanicalFragment;
import com.example.doanandroid.Fragment.DepmentElectronicFragment;
import com.example.doanandroid.Fragment.GroupYouthFragment;
import com.example.doanandroid.Fragment.HomeFragment;
import com.example.doanandroid.Fragment.RoomAdminiStrativeFragment;
import com.example.doanandroid.Fragment.RoomCTCT_HSSVFragment;
import com.example.doanandroid.Fragment.RoomTrainingFragment;
import com.example.doanandroid.Fragment.ScholarshipFragment;
import com.example.doanandroid.Fragment.TuitionFragment;
import com.example.doanandroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static Dialog dialog;
    public static Toolbar toolbar;
    public static DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    CircleImageView img_account;
    List<String> groupList;
    List<String> childList;
    SearchView searchView;
    RelativeLayout relate_toolbar;
    Map<String, List<String>> ListCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    String[] RoomModels = {"Phòng đào tạo", "Phòng hành chính", "Phòng CTCT - HSSV"};
    String[] DepartmentModels = {"Khoa cơ khí", "Khoa công CNTT", "Khoa điện - điện tử"};
    String[] unionModels = {"Đoàn - Hội"};
    String[] CLBModelsss = {"Câu lạc bộ âm nhạc", "Câu lạc bộ tin học", "Câu lạc bộ điện tử"};
    String[] CLBModels = {"Câu lạc bộ tin học"};
    String[] CLBModelss = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        dialog = new Dialog(MainActivity.this, androidx.appcompat.R.style.Base_Widget_AppCompat_ProgressBar);
        dialog.setContentView(R.layout.progresbar_dialog);
        loadFragment(new HomeFragment());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0)); // hiển thị layout dialog trong this
        dialog.show();
        setActionNavi();
        createGroupList();
        createCollection();
        findViewById(R.id.btn_account).setOnClickListener(this);
        expandableListAdapter = new MyExpandableListAdapter(this, groupList, ListCollection);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;

            @SuppressLint("WrongConstant")
            @Override
            public void onGroupExpand(int i) {
                if (lastExpandedPosition != -1 && i != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
                if (expandableListAdapter.getGroup(i).equals("THOÁT")) {
                    SharedPreferences preferences1 = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = preferences1.edit();
                    editor1.putString("remember", "false");
                    editor1.apply();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                } else if (expandableListAdapter.getGroup(i).equals("ĐĂNG NHẬP")){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                if (expandableListAdapter.getGroup(i).equals("HỌC BỔNG - VAY VỐN")){
                    bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED);
                    loadFragment(new ScholarshipFragment());
                    relate_toolbar.setVisibility(View.GONE);
                    drawerLayout.close();
                }
                if (expandableListAdapter.getGroup(i).equals("HỌC PHÍ")){
                    bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED);
                    loadFragment(new TuitionFragment());
                    relate_toolbar.setVisibility(View.GONE);
                    drawerLayout.close();
                }
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0)); // hiển thị layout dialog trong this
                dialog.show();
                String selected = expandableListAdapter.getChild(i, i1).toString();
                bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED);
                /**
                 * Khoa
                 * */
                if (selected.equals(DepartmentModels[0])) {
                    relate_toolbar.setVisibility(View.GONE);
                    loadFragment(new DepartmentMechanicalFragment());
                    drawerLayout.close();
                }
                if (selected.equals(DepartmentModels[1])) {
                    relate_toolbar.setVisibility(View.GONE);
                    loadFragment(new DepartmentCNTTFragment());
                    drawerLayout.close();
                }
                if (selected.equals(DepartmentModels[2])) {
                    relate_toolbar.setVisibility(View.GONE);
                    loadFragment(new DepmentElectronicFragment());
                    drawerLayout.close();
                }
                /**
                 * Phòng Ban
                 * */
                if (selected.equals(RoomModels[0])) {
                    loadFragment(new RoomTrainingFragment());
                    relate_toolbar.setVisibility(View.GONE);
                    drawerLayout.close();
                }
                if (selected.equals(RoomModels[1])) {
                    loadFragment(new RoomAdminiStrativeFragment());
                    relate_toolbar.setVisibility(View.GONE);
                    drawerLayout.close();
                }
                if (selected.equals(RoomModels[2])) {
                    loadFragment(new RoomCTCT_HSSVFragment());
                    relate_toolbar.setVisibility(View.GONE);
                    drawerLayout.close();
                }
                /**
                 * Đoàn - hội
                 * */
                if (selected.equals(unionModels[0])) {
                    relate_toolbar.setVisibility(View.GONE);
                    loadFragment(new GroupYouthFragment());
                    drawerLayout.close();
                }
                /**
                 * Câu lạc bộ
                 * */
//                if (selected.equals(CLBModels[0])){
//                    txt_name_toolbar.setText("CÂU LẠC BỘ ÂM NHẠC");
//                    relate_toolbar.setVisibility(View.GONE);
//                    loadFragment(new CLB_MusicFragment());
//                    drawerLayout.close();
//                }
                if (selected.equals(CLBModels[0])) {
                    relate_toolbar.setVisibility(View.GONE);
                    loadFragment(new CLB_IT_Fragment());
                    drawerLayout.close();
                }
//                if (selected.equals(CLBModels[2])) {
//                    txt_name_toolbar.setText("CÂU LẠC BỘ ĐIỆN TỬ");
//                    relate_toolbar.setVisibility(View.GONE);
//                    loadFragment(new CLB_ElectronicFragment());
//                    drawerLayout.close();
//                }
                return true;
            }
        });
        Actionbar();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerlayout);
        expandableListView = findViewById(R.id.elvMobiles);
        bottomNavigationView = findViewById(R.id.bottomnavi);
        img_account = findViewById(R.id.img_acount);
        searchView = findViewById(R.id.search_view);
        relate_toolbar = findViewById(R.id.relate_toolbar);
    }

    private void createCollection() {
        ListCollection = new HashMap<String, List<String>>();
        for (String group : groupList) {
            if (group.equals("PHÒNG")) {
                loadChild(RoomModels);
            } else if (group.equals("KHOA")) {
                loadChild(DepartmentModels);
            } else if (group.equals("ĐOÀN - HỘI")) {
                loadChild(unionModels);
            } else if (group.equals("CÂU LẠC BỘ")) {
                loadChild(CLBModels);
            }
            if (group.equals("HỌC BỔNG - VAY VỐN")) {
                loadChild(CLBModelss);
            }
            ListCollection.put(group, childList);
        }
    }

    private void loadChild(String[] mobileModels) {
        childList = new ArrayList<>();
        for (String model : mobileModels) {
            childList.add(model);
        }
    }

    private void createGroupList() {
        groupList = new ArrayList<>();
        groupList.add("PHÒNG");
        groupList.add("KHOA");
        groupList.add("ĐOÀN - HỘI");
        groupList.add("CÂU LẠC BỘ");
        groupList.add("HỌC BỔNG - VAY VỐN");
        groupList.add("HỌC PHÍ");
        groupList.add("CÀI ĐẶT");
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if (checkbox.equals("false")) {
            groupList.add("ĐĂNG NHẬP");
            bottomNavigationView.getMenu()
                    .findItem(R.id.item_person)
                    .setVisible(false);

            bottomNavigationView.invalidate();

        } else {
            groupList.add("THOÁT");
        }
    }

    // set hành động của bottomnavigation
    private void setActionNavi() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("WrongConstant")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.item_home:
                        relate_toolbar.setVisibility(View.VISIBLE);
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0)); // hiển thị layout dialog trong this
                        dialog.show();
                        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
                        return true;
                    case R.id.item_person:
//                        txt_name_toolbar.setText("THÔNG TIN CÁ NHÂN");
//                        fragment = new InforUserFragment();
//                        loadFragment(fragment);
                        startActivity(new Intent(MainActivity.this, InfoActivity.class));
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        dialog.show();
                        return true;
                }
                return false;
            }
        });
    }


    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    // hiển thị fragment
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_account:
                startActivity(new Intent(MainActivity.this, InfoActivity.class));
        }
    }
}