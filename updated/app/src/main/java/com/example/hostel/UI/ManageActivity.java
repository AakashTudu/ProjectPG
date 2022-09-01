package com.example.hostel.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Adapters.ManageAdapter;
import com.example.hostel.Models.Option;
import com.example.hostel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ManageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Option> optionList;
    String phoneNo, propertyNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        phoneNo = getIntent().getStringExtra("phoneNumber");
        propertyNo = getIntent().getStringExtra("propertyNo");

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
                    case R.id.dashboard:Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        intent.putExtra("phoneNumber", phoneNo);
                        intent.putExtra("propertyNo", propertyNo);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.manage:
                        return true;
                    case R.id.property:
                        intent = new Intent(getApplicationContext(), PropertiesActivity.class);
                        intent.putExtra("phoneNumber", phoneNo);
                        intent.putExtra("propertyNo", propertyNo);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        //Intent intent = new Intent(getApplicationContext(), ManageAdapter.class);

        recyclerView = findViewById(R.id.recyclerView);
        optionList = new ArrayList<Option>() {{
            add(new Option(
                    R.drawable.ic_tenant,
                    "TENANT",
                    getResources().getString(R.string.add_search_your_tenants)
            ));

            add(new Option(
                    R.drawable.ic_vacancy,
                    "ROOM \n" +
                            "VACANCIES",
                    getResources().getString(R.string.check_room_vacancies)
            ));

            add(new Option(
                    R.drawable.ic_enquiries,
                    "ENQUIRIES",
                    getResources().getString(R.string.clear_your_doubts)
            ));

            add(new Option(
                    R.drawable.ic_booking,
                    "BOOKING’S",
                    getResources().getString(R.string.check_your_booking_s_status)
            ));

            add(new Option(
                    R.drawable.ic_report,
                    "REPORT\n" +
                            "COMPLAINTS",
                    getResources().getString(R.string.report_your_complaints)
            ));

            add(new Option(
                    R.drawable.ic_expense,
                    "EXPENSES",
                    getResources().getString(R.string.track_your_expenses)
            ));
        }};


        recyclerView.setAdapter(new ManageAdapter(optionList));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}