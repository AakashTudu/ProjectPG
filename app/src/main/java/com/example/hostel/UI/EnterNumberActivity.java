package com.example.hostel.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hostel.R;
import com.google.android.material.button.MaterialButton;

public class EnterNumberActivity extends AppCompatActivity {

    MaterialButton btn_get_OTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_number);

        btn_get_OTP = findViewById(R.id.btn_get_OTP);
        btn_get_OTP.setOnClickListener(view -> {
            startActivity(new Intent(EnterNumberActivity.this, EnterOtpActivity.class));
        });
    }
}