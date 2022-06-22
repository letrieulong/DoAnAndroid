package com.example.doanandroid.Object;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        Login();

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

    // Khởi tạo các control
    private void init() {
        edt_email = findViewById(R.id.edt_email_login);
        edt_pass = findViewById(R.id.edt_pass_login);
        btn_login = findViewById(R.id.btn_login);
    }

    // tạo form
//    private void getData() {
//        String Email = edt_email.getText().toString().trim();
//        String Pass = edt_pass.getText().toString().trim();
//
//        SharedPreferencessss.save(LoginActivity.this, "Email", Email);
//        SharedPreferencessss.save(LoginActivity.this, "Pass", Pass);
//
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("email", Email)
//                .addFormDataPart("password", Pass)
//                .build();
//
//        ConnectApi.apiService.getUserLogin(requestBody).enqueue(new Callback<StatusCode>() {
//            @Override
//            public void onResponse(Call<StatusCode> call, Response<StatusCode> response) {
//                if (response.body().getStatus().equals("OK")) {
//                    StatusCode a = response.body();
//
//                    // lưu data
//                    SharedPreferencessss.save(LoginActivity.this, "token", a.getToken());
//                    SharedPreferencessss.save(LoginActivity.this, "apiHost", a.getApihost());
//
//                    SharedPreferencessss.save(LoginActivity.this, "gui_url", a.getPersonal().getGui_url());
//                    SharedPreferencessss.save(LoginActivity.this, "name", a.getPersonal().getName());
//                    SharedPreferencessss.save(LoginActivity.this, "position", a.getPersonal().getPosition());
//                    SharedPreferencessss.save(LoginActivity.this, "phone", a.getPersonal().getPhone());
//                    SharedPreferencessss.save(LoginActivity.this, "email", a.getPersonal().getEmail());
//                    SharedPreferencessss.save(LoginActivity.this, "facebook_url", a.getPersonal().getFacebook_url());
//                    SharedPreferencessss.save(LoginActivity.this, "zalo", a.getPersonal().getZalo());
//                    SharedPreferencessss.save(LoginActivity.this, "address", a.getPersonal().getAddress());
//
//                    SaveLogin();
//                } else if (response.body().getStatus().equals("ER")) {
//                    Toast.makeText(LoginActivity.this, "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<StatusCode> call, Throwable t) {
//                Toast.makeText(LoginActivity.this, "Lỗi Kết Nối api", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

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