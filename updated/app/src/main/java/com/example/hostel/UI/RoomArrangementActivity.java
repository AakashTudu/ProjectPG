package com.example.hostel.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Adapters.RoomsArrangementAdapter;
import com.example.hostel.R;

public class RoomArrangementActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_arrangement);
        btn_add = findViewById(R.id.btn_add);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setAdapter(new RoomsArrangementAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_add.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SuccessActivity.class);
        });

    }
}