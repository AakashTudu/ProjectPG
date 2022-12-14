package com.example.hostel.UI;

import androidx.appcompat.app.AppCompatActivity;
import com.example.hostel.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(getApplicationContext(),
                        EnterNumberActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}