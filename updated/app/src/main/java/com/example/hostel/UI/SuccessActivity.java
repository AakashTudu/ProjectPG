package com.example.hostel.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hostel.R;

public class SuccessActivity extends AppCompatActivity {

    Button btn_continue;
    String phoneNo, propertyNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        btn_continue = findViewById(R.id.btn_continue);
        //phoneNo = getIntent().getStringExtra("phoneNumber");
        //propertyNo = getIntent().getStringExtra("propertyNo");
        phoneNo = "7001485295";// for not getting error in dashboard (only for test purpose)
        propertyNo ="one";// for not getting error in dashboard (only for test purpose)


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                Toast.makeText(SuccessActivity.this, "Property Successfully Added :)", Toast.LENGTH_SHORT).show();
                intent.putExtra("phoneNumber", phoneNo);
                intent.putExtra("propertyNo", propertyNo);
                startActivity(intent);
                finish();
            }
        });
    }
}