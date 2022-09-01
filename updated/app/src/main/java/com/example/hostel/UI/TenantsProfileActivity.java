package com.example.hostel.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TenantsProfileActivity extends AppCompatActivity {

    TextView tv_name, change_room, off_board;
    ImageView back_btn;
    LinearLayout property_details, send_wp_msg;
    String phoneNo = "7001485295";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenants_profile);

        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(getIntent().getStringExtra("student_name"));

        send_wp_msg = findViewById(R.id.send_wp_msg);
        send_wp_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("smsto:" + phoneNo);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                startActivity(Intent.createChooser(i, ""));
            }
        });

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TenantsActivity.class));
            }
        });

        property_details = findViewById(R.id.property_details);
        property_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TenantsProfileActivity.this, "Work in Progress", Toast.LENGTH_SHORT).show();
            }
        });

        change_room = findViewById(R.id.change_room);
        change_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TenantsProfileActivity.this, "Work in Progress", Toast.LENGTH_SHORT).show();
            }
        });

        off_board = findViewById(R.id.off_board);
        off_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TenantsProfileActivity.this, "Work in Progress", Toast.LENGTH_SHORT).show();
            }
        });

        //Bottom Navigation
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.manage);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.manage:
                        startActivity(new Intent(getApplicationContext(), ManageActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.property:
                        startActivity(new Intent(getApplicationContext(), PropertiesActivity.class));
                        overridePendingTransition(0,0);
                }
                return false;
            }
        });
    }

    public void openDatePicker_addTenant(View view) {
    }
}