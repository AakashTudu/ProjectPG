package com.example.hostel.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.hostel.Adapters.ComplaintsAdapter;
import com.example.hostel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ComplaintsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView back_btn;
    String phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        recyclerView = findViewById(R.id.recyclerView);
        back_btn = findViewById(R.id.back_btn);
        phoneNo ="7001485295";

        //Back Image Button
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ManageActivity.class);
                intent.putExtra("phoneNumber", phoneNo);
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
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.property:
                        startActivity(new Intent(getApplicationContext(), PropertiesActivity.class));
                        overridePendingTransition(0,0);
                }
                return false;
            }
        });


        recyclerView.setAdapter(new ComplaintsAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}