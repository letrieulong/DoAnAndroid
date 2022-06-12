package com.example.doanandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanandroid.Fragment.CLB_ElectronicFragment;
import com.example.doanandroid.Fragment.CLB_IT_Fragment;
import com.example.doanandroid.Fragment.CLB_MusicFragment;
import com.example.doanandroid.Fragment.DepartmentCNTTFragment;
import com.example.doanandroid.Fragment.DepartmentEconomyFragment;
import com.example.doanandroid.Fragment.DepartmentPoliticsFragment;
import com.example.doanandroid.Fragment.DepmentElectronicFragment;
import com.example.doanandroid.Fragment.GroupStudentFragment;
import com.example.doanandroid.Fragment.GroupYouthFragment;
import com.example.doanandroid.Fragment.HomeFragment;
import com.example.doanandroid.Fragment.InforUserFragment;
import com.example.doanandroid.Fragment.RoomAdminiStrativeFragment;
import com.example.doanandroid.Fragment.RoomCTCT_HSSVFragment;
import com.example.doanandroid.Fragment.RoomTrainingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static Toolbar toolbar;
    public static DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    TextView txt_name_toolbar;
    CircleImageView img_account;
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> ListCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    String[] RoomModels = {"Phòng đào tạo", "Phòng hành chính", "Phòng CTCT - HSSV"};
    String[] DepartmentModels = {"Khoa kinh tế", "Khoa chính trị", "Khoa công CNTT", "Khoa điện tử"};
    String[] unionModels = {"Đoàn thanh niên", "Hội sinh viên"};
    String[] CLBModels = {"Câu lạc bộ âm nhạc", "Câu lạc bộ tin học", "Câu lạc bộ điện tử"};
    String[] CLBModelss = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        loadFragment(new HomeFragment());
        setActionNavi();
        createGroupList();
        createCollection();

        expandableListAdapter = new MyExpandableListAdapter(this, groupList, ListCollection);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if (lastExpandedPosition != -1 && i != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
                if (lastExpandedPosition == 4){
                    startActivity(new Intent(MainActivity.this, cai_dat.class));
                }
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i, i1).toString();
                /**
                 * Khoa
                 * */
                if (selected.equals(DepartmentModels[0])){
                    txt_name_toolbar.setText("KHOA KINH TẾ");
                    loadFragment(new DepartmentEconomyFragment());
                    drawerLayout.close();
                }
                if (selected.equals(DepartmentModels[1])){
                    txt_name_toolbar.setText("KHOA CHÍNH TRỊ");
                    loadFragment(new DepartmentPoliticsFragment());
                    drawerLayout.close();
                }
                if (selected.equals(DepartmentModels[2])){
                    txt_name_toolbar.setText("KHOA CÔNG NGHỆ THÔNG TIN");
                    loadFragment(new DepartmentCNTTFragment());
                    drawerLayout.close();
                }
                if (selected.equals(DepartmentModels[3])){
                    txt_name_toolbar.setText("KHOA ĐIỆN TỬ");
                    loadFragment(new DepmentElectronicFragment());
                    drawerLayout.close();
                }
                /**
                * Phòng Ban
                * */
                if (selected.equals(RoomModels[0])){
                    txt_name_toolbar.setText("PHÒNG ĐÀO TẠO");
                    loadFragment(new RoomTrainingFragment());
                    drawerLayout.close();
                }
                if (selected.equals(RoomModels[1])){
                    txt_name_toolbar.setText("PHÒNG HÀNH CHÍNH");
                    loadFragment(new RoomAdminiStrativeFragment());
                    drawerLayout.close();
                }
                if (selected.equals(RoomModels[2])){
                    txt_name_toolbar.setText("PHÒNG CTCT-HSSV");
                    loadFragment(new RoomCTCT_HSSVFragment());
                    drawerLayout.close();
                }
                /**
                 * Đoàn - hội
                 * */
                if (selected.equals(unionModels[0])){
                    txt_name_toolbar.setText("ĐOÀN THANH NIÊN");
                    loadFragment(new GroupYouthFragment());
                    drawerLayout.close();
                }
                if (selected.equals(unionModels[1])){
                    txt_name_toolbar.setText("HỘI SINH VIÊN");
                    loadFragment(new GroupStudentFragment());
                    drawerLayout.close();
                }
                /**
                 * Câu lạc bộ
                 * */
                if (selected.equals(CLBModels[0])){
                    txt_name_toolbar.setText("CÂU LẠC BỘ ÂM NHẠC");
                    loadFragment(new CLB_MusicFragment());
                    drawerLayout.close();
                }
                if (selected.equals(CLBModels[1])){
                    txt_name_toolbar.setText("CÂU LẠC BỘ TIN HỌC");
                    loadFragment(new CLB_IT_Fragment());
                    drawerLayout.close();
                }
                if (selected.equals(CLBModels[2])) {
                    txt_name_toolbar.setText("CÂU LẠC BỘ ĐIỆN TỬ");
                    loadFragment(new CLB_ElectronicFragment());
                    drawerLayout.close();
                }
                return true;
            }
        });
        Actionbar();
    }

    private void init(){
        toolbar = findViewById(R.id.toolbar);
        txt_name_toolbar = findViewById(R.id.txt_name_toolbar);
        drawerLayout = findViewById(R.id.drawerlayout);
        expandableListView = findViewById(R.id.elvMobiles);
        bottomNavigationView = findViewById(R.id.bottomnavi);
        img_account = findViewById(R.id.img_acount);
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
            if (group.equals("CÀI ĐẶT")) {
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
        groupList.add("CÀI ĐẶT");
        groupList.add("THOÁT");
    }

    // set hành động của bottomnavigation
    private void setActionNavi() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.item_home:
                        txt_name_toolbar.setText("TRANG CHỦ");
                        fragment = new HomeFragment();
                        loadFragment(fragment);
//                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0)); // hiển thị layout dialog trong this
//                        dialog.show();
                        return true;
                    case R.id.item_person:
                        txt_name_toolbar.setText("THÔNG TIN CÁ NHÂN");
                        fragment = new InforUserFragment();
                        loadFragment(fragment);
//                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//                        dialog.show();
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
        switch (view.getId()){
            case R.id.btn_logout:
                SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            case R.id.linear_logout:
                SharedPreferences preferences1 = getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences1.edit();
                editor1.putString("remember", "false");
                editor1.apply();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}