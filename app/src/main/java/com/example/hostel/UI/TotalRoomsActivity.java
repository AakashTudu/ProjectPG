package com.example.hostel.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hostel.Adapters.TotalRoomsAdapter;
import com.example.hostel.Listeners.OnGroupBtnClickListener;
import com.example.hostel.Models.Floor;
import com.example.hostel.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Iterator;

public class TotalRoomsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<Floor> hotelList;
    TextView tv_total_room_quantity;
    int totalRoomSum = 0;
    MaterialButton btn_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_rooms);
        recyclerView = findViewById(R.id.recyclerView);
        tv_total_room_quantity = findViewById(R.id.tv_total_room_quantity);
        btn_continue = findViewById(R.id.btn_continue);

        hotelList = (ArrayList<Floor>) getIntent().getSerializableExtra("hotelList");

        for (Iterator<Floor> iterator = hotelList.iterator(); iterator.hasNext(); ) {
            Floor floor = iterator.next();
            if (!floor.getSelected()) {
                iterator.remove();
            }
        }
        adapter = new TotalRoomsAdapter(hotelList, new OnGroupBtnClickListener() {
            @Override
            public void addBtnClicked() {
                totalRoomSum++;
                tv_total_room_quantity.setText("" + totalRoomSum);
            }

            @Override
            public void minusBtnCLicked() {
                totalRoomSum--;
                tv_total_room_quantity.setText("" + totalRoomSum);

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();

        btn_continue.setOnClickListener(view -> {
            startActivity(new Intent(TotalRoomsActivity.this,OccupancyInputActivity.class));
        });
    }
}