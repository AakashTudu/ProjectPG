package com.example.hostel.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.hostel.Adapters.TotalFloorsAdapter;
import com.example.hostel.Models.Floor;
import com.example.hostel.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class TotalFloorsActivity extends AppCompatActivity {

    ArrayList<Floor> hotelList;
    RecyclerView recyclerView;
    MaterialButton btn_add, btn_continue;
    TotalFloorsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_floors);

        hotelList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        btn_add = findViewById(R.id.btn_add);
        btn_continue = findViewById(R.id.btn_continue);

        adapter = new TotalFloorsAdapter(hotelList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_add.setOnClickListener(view -> {
            hotelList.add(new Floor());
            adapter.notifyItemInserted(hotelList.size());
            recyclerView.scrollToPosition(hotelList.size()-1);
        });

        btn_continue.setOnClickListener(view -> {
            Intent intent = new Intent(TotalFloorsActivity.this,TotalRoomsActivity.class);
            intent.putExtra("hotelList", hotelList);
            startActivity(intent);
        });

    }
}