package com.example.hostel.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Adapters.TotalFloorsAdapter;
import com.example.hostel.Models.Floor;
import com.example.hostel.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

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
/*
        hotelList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        btn_add = findViewById(R.id.btn_add);
        btn_continue = findViewById(R.id.btn_save);

        adapter = new TotalFloorsAdapter(hotelList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_add.setOnClickListener(view -> {
            showAddBottomSheetDialog();
        });

        btn_continue.setOnClickListener(view -> {
            Intent intent = new Intent(TotalFloorsActivity.this, TotalRoomsActivity.class);
            intent.putExtra("hotelList", hotelList);
            startActivity(intent);
        });*/

    }

/*    private void showAddBottomSheetDialog() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_add_floor);
        AppCompatButton tvAdd = bottomSheetDialog.findViewById(R.id.btn_add_bottom_sheet);
        TextInputEditText etFloorName = bottomSheetDialog.findViewById(R.id.et_floor_name);
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etFloorName.getText().toString();
                String s1 = String.valueOf(name.charAt(0));
                hotelList.add(new Floor(s1.toUpperCase() + name.substring(1)));
                adapter.notifyItemInserted(hotelList.size());
                recyclerView.scrollToPosition(hotelList.size() - 1);
                bottomSheetDialog.cancel();
            }
        });
        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
    }*/
}