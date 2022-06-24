package com.example.doanandroid.Object;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanandroid.Fragment.HomeFragment;
import com.example.doanandroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class dang_ky_tai_khoan extends AppCompatActivity {
    private EditText _email, _name, _pass, _repass;
    private TextView _dangnhap;
    private Button btndangky;
    private ProgressDialog progressDialog;
    //private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_tai_khoan);

        iunit();
        initListener();

//        findViewById(R.id.btndangky).setOnClickListener(this);
//        findViewById(R.id._dangnhap).setOnClickListener(this);
    }
    private void iunit(){
        _email = findViewById(R.id.ipt_email);
//        _name = findViewById(R.id.ipt_name);
        _pass = findViewById(R.id.ipt_pass);
//        _repass = findViewById(R.id.ipt_repass);
        btndangky = findViewById(R.id.btndangky);
        _dangnhap=findViewById(R.id._dangnhap);
        progressDialog = new ProgressDialog(this);
    }

    private void initListener(){
        btndangky.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onClickDangKy();
            }

            private void onClickDangKy() {
                FirebaseAuth _auth = FirebaseAuth.getInstance();
                String email = _email.getText().toString().trim();
                String password= _pass.getText().toString().trim();
                _auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(dang_ky_tai_khoan.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent intent = new Intent(dang_ky_tai_khoan.this, LoginActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getApplicationContext(), "Tạo tài khoản không thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        _dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dang_ky_tai_khoan.this,LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}