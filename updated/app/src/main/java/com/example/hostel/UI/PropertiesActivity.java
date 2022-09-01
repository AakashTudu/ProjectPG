package com.example.hostel.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Adapters.ManageAdapter;
import com.example.hostel.Adapters.PropertiesAdapter;
import com.example.hostel.CustomViews.CustomEditText;
import com.example.hostel.Models.PropertiesOption;
import com.example.hostel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Properties;

public class PropertiesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<PropertiesOption> optionList;

    CustomEditText et_search;
    Button btn_add_property;
    ImageView back_btn;
    String phoneNo, propertyNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);

        et_search = findViewById(R.id.et_search);
        btn_add_property = findViewById(R.id.btn_add_property);
        back_btn = findViewById(R.id.back_btn);

        phoneNo = getIntent().getStringExtra("phoneNumber");
        propertyNo = getIntent().getStringExtra("propertyNo");

        recyclerView = findViewById(R.id.recyclerView);
        optionList = new ArrayList<PropertiesOption>() {
            {
                add(new PropertiesOption(
                        "Prasant PG for Men's",
                        "PG",
                        "Location"
                ));
                add(new PropertiesOption(
                        "Prashant 2",
                        "Flat",
                        "Bangalore"
                ));
            }
        };

        recyclerView.setAdapter(new PropertiesAdapter(optionList));
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


        //Bottom Navigation
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.property);

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
                        intent = new Intent(getApplicationContext(), ManageActivity.class);
                        intent.putExtra("phoneNumber", phoneNo);
                        intent.putExtra("propertyNo", propertyNo);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.property:
                        return true;
                }
                return false;
            }
        });

        btn_add_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPropActivity.class);
                startActivity(intent);
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ManageActivity.class);
                startActivity(intent);
            }
        });

    }
}