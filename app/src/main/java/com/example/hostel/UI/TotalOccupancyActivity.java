package com.example.hostel.UI;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Adapters.TotalOccupancyAdapter;
import com.example.hostel.Models.Occupancy;
import com.example.hostel.R;

import java.util.ArrayList;
import java.util.Iterator;

public class TotalOccupancyActivity extends AppCompatActivity {

    ArrayList<Occupancy> occupancyList;
    RecyclerView recyclerView;
    TextView tv_total_room_quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_occupancy);
        recyclerView = findViewById(R.id.recyclerView);
        tv_total_room_quantity = findViewById(R.id.tv_total_room_quantity);

        occupancyList = (ArrayList<Occupancy>) getIntent().getSerializableExtra("occupancyList");

        for (Iterator<Occupancy> iterator = occupancyList.iterator(); iterator.hasNext(); ) {
            Occupancy occupancy = iterator.next();
            if (!occupancy.getSelected()) {
                iterator.remove();
            }
        }

        recyclerView.setAdapter(new TotalOccupancyAdapter(occupancyList, () -> {
            int sum = 0;
            for (Occupancy occupancy : occupancyList) {
                sum = sum + occupancy.getRoomsQuantity();
            }

            tv_total_room_quantity.setText("" + sum);
        }));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}