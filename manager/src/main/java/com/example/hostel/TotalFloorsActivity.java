package com.example.hostel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TotalFloorsActivity extends AppCompatActivity {
    TextView add_floor;
    LinearLayout linearLayout8, layout;
    int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_floors);

        add_floor = findViewById(R.id.add_floor_btn);
        linearLayout8 = findViewById(R.id.linearLayout8);
        layout = findViewById(R.id.layout);

        add_floor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.add_floor, null);
                linearLayout8.addView(v);
                Toast.makeText(TotalFloorsActivity.this, "Hey..!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}