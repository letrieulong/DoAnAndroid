package com.example.doanandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class dang_ky_tai_khoan extends AppCompatActivity implements View.OnClickListener {
    private EditText _email, _name, _pass, _repass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_tai_khoan);

        _email= findViewById(R.id.ipt_email);
        _name= findViewById(R.id.ipt_name);
        _pass= findViewById(R.id.ipt_pass);
        _repass= findViewById(R.id.ipt_repass);

        findViewById(R.id.btndangky).setOnClickListener(this);
        findViewById(R.id._dangnhap).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btndangky:
                Intent intent =new Intent(dang_ky_tai_khoan.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id._dangnhap:
                Intent intents =new Intent(dang_ky_tai_khoan.this,LoginActivity.class);
                startActivity(intents);
                break;
        }

    }
}