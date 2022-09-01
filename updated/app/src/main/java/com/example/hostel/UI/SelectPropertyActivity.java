package com.example.hostel.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.hostel.Adapters.PropertiesAdapter;
import com.example.hostel.R;

public class SelectPropertyActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_property);

        recyclerView = findViewById(R.id.recyclerView);


        //recyclerView.setAdapter(new PropertiesAdapter());
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}