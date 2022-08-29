package com.example.hostel.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Adapters.RoomsArrangementAdapter;
import com.example.hostel.R;

public class RoomArrangementActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_arrangement);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setAdapter(new RoomsArrangementAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}