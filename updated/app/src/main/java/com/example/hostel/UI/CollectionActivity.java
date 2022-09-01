package com.example.hostel.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hostel.R;

public class CollectionActivity extends AppCompatActivity {

    ImageView back_btn;
    String phoneNo, propertyNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        phoneNo = getIntent().getStringExtra("phoneNumber");
        propertyNo = getIntent().getStringExtra("propertyNo");

        back_btn = findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                intent.putExtra("phoneNumber", phoneNo);
                intent.putExtra("propertyNo", propertyNo);
                startActivity(intent);
            }
        });
    }
}