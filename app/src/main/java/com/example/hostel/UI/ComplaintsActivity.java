package com.example.hostel.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.hostel.Adapters.ComplaintsAdapter;
import com.example.hostel.R;

public class ComplaintsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setAdapter(new ComplaintsAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}