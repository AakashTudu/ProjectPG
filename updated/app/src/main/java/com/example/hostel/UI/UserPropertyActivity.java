package com.example.hostel.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.hostel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserPropertyActivity extends AppCompatActivity {

    CardView cv_report, cv_tenant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_property);

        cv_report = findViewById(R.id.cv_report);
        cv_report.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ComplaintsActivity.class));
            }
        });

        cv_tenant = findViewById(R.id.cv_tenant);
        cv_tenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TenantsActivity.class);
                startActivity(intent);
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
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.property:
                        startActivity(new Intent(getApplicationContext(), PropertiesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }
}