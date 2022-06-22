package com.example.doanandroid.Object;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanandroid.R;

public class Show_Screen extends AppCompatActivity {
    CountDownTimer countDownTimer;
    TextView txt_welcom;
    ImageView img_view;
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txt_welcom = findViewById(R.id.txt_welcom);
        img_view = findViewById(R.id.img_view);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        Animation animation1= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        txt_welcom.setAnimation(animation);
        img_view.setAnimation(animation1);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Show_Screen.this,MainActivity.class);
                Show_Screen.this.startActivity(mainIntent);
                Show_Screen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}