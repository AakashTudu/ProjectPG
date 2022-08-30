package com.example.hostel.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hostel.R;
import com.google.android.material.button.MaterialButton;

public class ProfileActivity extends AppCompatActivity {

    MaterialButton btn_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btn_continue = findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(view -> {
            startActivity(new Intent(ProfileActivity.this, EnterNumberActivity.class));
        });
    }
}