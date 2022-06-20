package com.example.doanandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class dang_ky_tai_khoan extends AppCompatActivity implements View.OnClickListener {
    private EditText _email, _name, _pass, _repass;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_tai_khoan);

        _email = findViewById(R.id.ipt_email);
        _name = findViewById(R.id.ipt_name);
        _pass = findViewById(R.id.ipt_pass);
        _repass = findViewById(R.id.ipt_repass);

        findViewById(R.id.btndangky).setOnClickListener(this);
        findViewById(R.id._dangnhap).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btndangky:
                Intent intent = new Intent(dang_ky_tai_khoan.this, LoginActivity.class);
                startActivity(intent);
                register();
                break;
            case R.id._dangnhap:
                Intent intents = new Intent(dang_ky_tai_khoan.this, LoginActivity.class);
                startActivity(intents);
                break;
        }
    }

    private void register() {
        mAuth = FirebaseAuth.getInstance();
        String email, pass;
        email = _email.getText().toString();
        pass  = _pass.getText().toString();
        //Kiẻm tra ô nhập bị trống
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Vui lòng nhập email !", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Vui lòng nhập password !", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(dang_ky_tai_khoan.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Tạo tài khoản không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}