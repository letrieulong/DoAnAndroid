package com.example.doanandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanandroid.Adapter.AdapterSearch;
import com.example.doanandroid.Model.DemoModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    List<DemoModel> demoModelList = new ArrayList<>();
    AdapterSearch adapterSearch;
    RecyclerView recyFind;
    EditText edt_search;
    TextView txt_end_date, txt_start_date;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        ActionToolbar();
        demoModelList.add(new DemoModel("Đăng ký học phần", "15/02/2022", "151", R.drawable.ic_launcher_background));
        demoModelList.add(new DemoModel("Đăng ký học kỳ phụ", "15/02/2022", "151", R.drawable.ic_launcher_background));
        demoModelList.add(new DemoModel("Lịch học kỳ phụ", "15/02/2022", "151", R.drawable.ic_launcher_background));
        demoModelList.add(new DemoModel("Lịch tiếp sinh viên", "15/02/2022", "151", R.drawable.ic_launcher_background));
        demoModelList.add(new DemoModel("Thông báo", "15/02/2022", "151", R.drawable.ic_launcher_background));
        demoModelList.add(new DemoModel("Lịch thi", "15/02/2022", "151", R.drawable.ic_launcher_background));
        demoModelList.add(new DemoModel("Thời gian báo cáo đồ án", "15/02/2022", "151", R.drawable.ic_launcher_background));
        demoModelList.add(new DemoModel("Thời gian nghĩ hè", "15/02/2022", "151", R.drawable.ic_launcher_background));
        demoModelList.add(new DemoModel("Thông báo đóng học phí", "15/02/2022", "151", R.drawable.ic_launcher_background));

        txt_end_date.setOnClickListener(this::onClick);
        txt_start_date.setOnClickListener(this::onClick);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // khởi tạo các control
    private void init() {
        toolbar = findViewById(R.id.toolbar);
        txt_end_date = findViewById(R.id.txt_date_end);
        txt_start_date = findViewById(R.id.txt_date_start);
        edt_search = findViewById(R.id.edt_search_1);

        recyFind = findViewById(R.id.recyFindSearch);

        adapterSearch = new AdapterSearch(this, demoModelList);
        recyFind.setLayoutManager(new LinearLayoutManager(this));
        recyFind.setAdapter(adapterSearch);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_date_end:
                calendar(txt_end_date);
                return;
            case R.id.txt_date_start:
                calendar(txt_start_date);
                return;
        }
    }
}