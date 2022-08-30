package com.example.hostel.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.hostel.Adapters.OccupancyInputAdapter;
import com.example.hostel.Models.Occupancy;
import com.example.hostel.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class OccupancyInputActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Occupancy> occupancyList;
    MaterialButton btn_continue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occupancy_input);
        btn_continue = findViewById(R.id.btn_continue);
        recyclerView = findViewById(R.id.recyclerView);
        occupancyList = new ArrayList<Occupancy>(){{
            add(new Occupancy("Single Occupancy"));
            add(new Occupancy("Double Occupancy"));
            add(new Occupancy("Triple Occupancy"));
            add(new Occupancy("Four Occupancy"));
            add(new Occupancy("Five Occupancy"));
            add(new Occupancy("Six Occupancy"));
            add(new Occupancy("Seven Occupancy"));
            add(new Occupancy("Eight Occupancy"));
            add(new Occupancy("Nine Occupancy"));
            add(new Occupancy("Ten Occupancy"));
        }};
        recyclerView.setAdapter(new OccupancyInputAdapter(occupancyList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_continue.setOnClickListener(view -> {
            Intent intent = new Intent(OccupancyInputActivity.this, TotalOccupancyActivity.class);
            intent.putExtra("occupancyList", occupancyList);
            startActivity(intent);
        });
    }
}