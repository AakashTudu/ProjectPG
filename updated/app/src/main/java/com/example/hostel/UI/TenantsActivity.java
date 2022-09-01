package com.example.hostel.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hostel.Adapters.TenantsAdapter;
import com.example.hostel.CustomViews.CustomEditText;
import com.example.hostel.Models.TenantsOption;
import com.example.hostel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class TenantsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<TenantsOption> optionList;

    CustomEditText et_search;
    Button btn_add_tenant;
    ImageView back_btn;
    //For sampling setting up Static phoneno and propertyno
    String phoneNo="7001485295", propertyNo="one";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenants);
        recyclerView = findViewById(R.id.recyclerView);
        et_search = findViewById(R.id.et_search);
        btn_add_tenant = findViewById(R.id.btn_add_tenant);
        back_btn = findViewById(R.id.back_btn);

        optionList = new ArrayList<TenantsOption>() {{
            add(new TenantsOption(
                    "Varun Kumar",
                    "Prashant PG for Men's"
            ));

            add(new TenantsOption(
                    "Suraj Sharma",
                    "Prashant PG Apartment"
            ));

        }};

        recyclerView.setAdapter(new TenantsAdapter(optionList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        et_search.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;

            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (et_search.getRight() - et_search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (et_search.hasFocus()) {
                        et_search.clearFocus();
                        et_search.setText("");
                    }
                    et_search.hideKeyboard();
                    return true;
                }
            }
            return false;
        });

        //Add Tenant Button
        btn_add_tenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddTenantActivity.class);
                intent.putExtra("phoneNumber", phoneNo);
                intent.putExtra("propertyNo", propertyNo);
                startActivity(intent);
            }
        });

        //Back Image Button
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ManageActivity.class);
                intent.putExtra("phoneNumber", phoneNo);
                intent.putExtra("propertyNo", propertyNo);
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
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        intent.putExtra("phoneNumber", phoneNo);
                        intent.putExtra("propertyNo", propertyNo);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.manage:
                        intent = new Intent(getApplicationContext(), ManageActivity.class);
                        intent.putExtra("phoneNumber", phoneNo);
                        intent.putExtra("propertyNo", propertyNo);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.property:
                        intent = new Intent(getApplicationContext(), PropertiesActivity.class);
                        intent.putExtra("phoneNumber", phoneNo);
                        intent.putExtra("propertyNo", propertyNo);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });



    }
}