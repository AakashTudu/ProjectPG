package com.example.hostel.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.hostel.Adapters.TotalRoomsAdapter;
import com.example.hostel.Listeners.OnGroupBtnClickListener;
import com.example.hostel.Models.Floor;
import com.example.hostel.R;

import java.util.ArrayList;
import java.util.Iterator;

public class TotalRoomsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<Floor> hotelList;
    TextView tv_total_room_quantity;
    int totalRoomSum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_rooms);
        recyclerView = findViewById(R.id.recyclerView);
        tv_total_room_quantity = findViewById(R.id.tv_total_room_quantity);

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
    }
}