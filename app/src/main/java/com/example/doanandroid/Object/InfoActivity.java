package com.example.doanandroid.Object;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.doanandroid.R;
import com.example.doanandroid.Util.SharedPreferencessss;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;
public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    CircleImageView img_infor;
    TextView txt_name, txt_position, txt_phone, txt_email,txt_face_book, txt_zalo, txt_address;
    Button btn_logout;
    Toolbar toolbar;
    LinearLayout linear_logout;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        init();
        Actiontoolbar();
        getData();
        dialog = new Dialog(InfoActivity.this, androidx.appcompat.R.style.Base_Widget_AppCompat_ProgressBar);
        dialog.setContentView(R.layout.progresbar_dialog);
        btn_logout.setOnClickListener(this::onClick);
//        btn_update.setOnClickListener(this::onClick);
        linear_logout.setOnClickListener(this::onClick);
        showUserInformation();
    }

    // khởi tạo các control
    private void init(){
        btn_logout    = findViewById(R.id.btn_logout);
        toolbar       = findViewById(R.id.toolbar);

        linear_logout       = findViewById(R.id.linear_logout);
        img_infor       = findViewById(R.id.img_acount_infor);

        txt_name      = findViewById(R.id.txt_name_account_infor);
        txt_position  = findViewById(R.id.txt_work_infor);
        txt_phone     = findViewById(R.id.txt_phone_infor);
        txt_email     = findViewById(R.id.txt_email_infor);
        txt_face_book = findViewById(R.id.txt_fb_infor);
        txt_zalo      = findViewById(R.id.txt_zalo_infor);
        txt_address   = findViewById(R.id.txt_address_infor);

    }

    // tạo form
    private void getData() {

        String Email = SharedPreferencessss.read(this,"Email","");
        String Pass = SharedPreferencessss.read(this,"Pass","");

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_logout:
                SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                startActivity(new Intent(InfoActivity.this, LoginActivity.class));
                return;
            case R.id.linear_logout:
//                SharedPreferences preferences1 = getSharedPreferences("checkbox",MODE_PRIVATE);
//                SharedPreferences.Editor editor1 = preferences1.edit();
//                editor1.putString("remember", "false");
//                editor1.apply();
                startActivity(new Intent(InfoActivity.this, LoginActivity.class));
                return;
        }
    }
    // Bắt sự kiện cho toolbar
    private void Actiontoolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void showUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        if(name==null){
            txt_name.setVisibility(View.GONE);
        }else{
            txt_name.setVisibility(View.VISIBLE);
            txt_name.setText(name);
        }

        txt_name.setText(name);
        txt_email.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.empty).into(img_infor);
    }
}