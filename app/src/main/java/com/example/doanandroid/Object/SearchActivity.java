package com.example.doanandroid.Object;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doanandroid.Adapter.AdapterRecruit_CNTT;
import com.example.doanandroid.Model.ContentLink;
import com.example.doanandroid.Model.DemoModel;
import com.example.doanandroid.Model.Mechanical;
import com.example.doanandroid.Model.Recruit_CNTT;
import com.example.doanandroid.Model.Tuition;
import com.example.doanandroid.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    List<Recruit_CNTT> recruit_cnttList = new ArrayList<>();
    AdapterRecruit_CNTT adapterRecruit_cntt;
    RecyclerView recyFind;
    EditText edt_search;
    TextView txt_end_date, txt_start_date;
    DatePickerDialog.OnDateSetListener setListener;
    String SearchFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        ActionToolbar();

        txt_end_date.setOnClickListener(this::onClick);
        edt_search.setOnClickListener(this::onClick);
        txt_start_date.setOnClickListener(this::onClick);

    }

    private void ActionToolbar() {
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
    }

    // khởi tạo các control
    private void init() {
        toolbar = findViewById(R.id.toolbar);
        txt_end_date = findViewById(R.id.txt_date_end);
        txt_start_date = findViewById(R.id.txt_date_start);
        edt_search = findViewById(R.id.edt_search_1);

        recyFind = findViewById(R.id.recyFindSearch);

        recyFind.setLayoutManager(new LinearLayoutManager(this));
        recyFind.setAdapter(adapterRecruit_cntt);
    }

    // calendar
    private void calendar(TextView tx) {
        Calendar calendar = Calendar.getInstance();

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int days = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                , setListener, year, month, days);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                month = month + 1;
                String date = days + "-" + month + "-" + year;
                tx.setText(date);
            }
        };
    }

    // search
    private void Search(EditText edt_search) {
        edt_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_ENTER) {
                    SearchFilter = edt_search.getText().toString();
                    filter(SearchFilter);
                }
                return false;
            }
        });
    }

    // Tìm kiếm giá trị
    private void filter(String text) {
        // tạo một danh sách mảng mới để lọc dữ liệu
        ArrayList<DemoModel> filteredlist = new ArrayList<>();

        // so sánh các phần từ trong adapter
//        for (DemoModel item : demoModelList) {
//            // kiểm tra chuỗi vừa nhập có khớp với giá trị cần so sánh hay không
//            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
//                filteredlist.add(item);
////            } else {
////                recyclerView.setVisibility(View.INVISIBLE);
////            }
////            if (Index_Start_Source.equals(text)){
////                recyclerView.setVisibility(View.VISIBLE);
////                filteredlist.add(item);
////            }if (Index_Start_Careby.equals(text)){
////                recyclerView.setVisibility(View.VISIBLE);
////                filteredlist.add(item);
////            }
//            }
//            // kiểm tra data vừa nhập có chứa nội dung trong adapter hay không
//            if (filteredlist.isEmpty()) {
//            } else {
//                // nếu có sẽ add vào classAdapter
//                adapterSearch.filterList(filteredlist);
////            recyclerView.setVisibility(View.VISIBLE);
//            }
//        }
    }
    String userId = "";
    String listValue = "";
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_date_end:
//                calendar(txt_end_date);
                DatabaseReference mDatabsase = FirebaseDatabase.getInstance().getReference("list_ctct_hssv");
                UUID uuid_ctt = UUID.randomUUID();
                String id_ctt = uuid_ctt.toString();
                String userkey = mDatabsase.push().getKey();
                ContentLink ctt = new ContentLink(id_ctt, "", "");
                mDatabsase.child("list_roomschool/" + userId + "/link/" + userkey).setValue(ctt);
//                Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();

                return;
            case R.id.txt_date_start:
//                calendar(txt_start_date);
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_ctct_hssv");

                UUID uuid = UUID.randomUUID();
                String id = uuid.toString();
                String title = "";
                String date = "";
                String content_link = "";
                String image = "";
                String size = "";
                String link = "";
                String views = "";
                UUID uuid_ct = UUID.randomUUID();
                String id_ct = uuid_ct.toString();
                ContentLink ct = new ContentLink(id_ct, link, "");

                Mechanical rs = new Mechanical(id, title,"", date, views, size, content_link);
                Tuition t = new Tuition("","","","");
                userId = mDatabase.push().getKey();
                mDatabase.child("list_roomschool/"+userId).setValue(t);
                return;
            case R.id.edt_search_1:
//                Search(edt_search);
                startActivity(new Intent(SearchActivity.this, LoginActivity.class));

                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                return;
        }
    }
}