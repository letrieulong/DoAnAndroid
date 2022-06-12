package com.example.app_thong_tin_cao_thang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email, matkhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=findViewById(R.id.inputEmail);
        matkhau= findViewById(R.id.inputMatkhau);

        findViewById(R.id.btnDangnhap).setOnClickListener(this);
        findViewById(R.id.Dangky).setOnClickListener(this);
        findViewById(R.id.Quenmatkhau).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        String _email = email.getText().toString();
        String _matkhau= matkhau.getText().toString();
        String mail = "0306181235@gmail.com";
        String mk = "123456";
        String _ok= "";
        switch (v.getId()) {
            case R.id.btnDangnhap:
                // Nếu đúng chuyển trang chử | sai sẽ thông báo
                    if(_email.equals(mail) && _matkhau.equals(mk)){
                        Intent intent = new Intent(this, doi_avatar.class);
//                        Bundle bundle= new Bundle();
//                        bundle.putString("Email",_ok);
//                        intent.putExtra("Email_intent",bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(this, "Vui lòng kiểm ra lại email hoặc mật khẩu",Toast.LENGTH_SHORT).show();
                    }
                break;
            case R.id.Quenmatkhau:
                    Intent intent = new Intent(this, Doi_mat_khau.class);
                    startActivity(intent);
                break;
            case R.id.Dangky:
                Intent intents = new Intent(this, dang_ky_tai_khoan.class);
                startActivity(intents);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}