package com.example.doanandroid.Object;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doanandroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText edt_email, edt_pass;
    Button btn_login;
    FirebaseAuth mAuth;
    TextView Quenmatkhau,Dangky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        Login();
        dangky();
        quenmatkhau();

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");

        if (checkbox.equals("true")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    private void Login() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edt_email.getText().toString();
                String pass = edt_pass.getText().toString();
//                getData();
                //Kiẻm tra ô nhập bị trống
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this,"Vui lòng nhập email !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(LoginActivity.this,"Vui lòng nhập password !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!email.equals("") && !pass.equals("")){
                    Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(email);
                    if (m.find()) {
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                                    SaveLogin();
//                                   SharedPreferencessss.save(LoginActivity.this,"Email",task.getResult().getUser().getEmail());
//                                   SharedPreferencessss.save(LoginActivity.this,"Uid",task.getResult().getUser().getUid());
                                }else{
                                    Toast.makeText(getApplicationContext(),"Đăng nhập không thành công !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        edt_email.setError("Email phải đúng định dạng !");
                    }

                }
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }
    //  doi mat khau
    private void dangky(){
        Dangky.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this,dang_ky_tai_khoan.class);
                startActivity(intent);
            }
        });
    }
    // quen mat khau
    private void quenmatkhau(){
        Quenmatkhau.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this,Doi_mat_khau.class);
                startActivity(intent);
            }
        });
    }
    // Khởi tạo các control
    private void init() {
        edt_email = findViewById(R.id.edt_email_login);
        edt_pass = findViewById(R.id.edt_pass_login);
        btn_login = findViewById(R.id.btn_login);
        Dangky = findViewById(R.id.Dangky);
        Quenmatkhau= findViewById(R.id.Quenmatkhau);

    }

    // lưu lần đăng nhập tiếp theo
    private void SaveLogin() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        LoginActivity.this.finish(); // ghi nhớ email,mật khẩu để login nhanh hơn
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember", "true");
        editor.apply();
    }
}