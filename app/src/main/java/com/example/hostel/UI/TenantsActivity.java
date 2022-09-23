package com.example.hostel.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;

import com.example.hostel.Adapters.TenantsAdapter;
import com.example.hostel.CustomViews.CustomEditText;
import com.example.hostel.R;

public class TenantsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CustomEditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenants);
        recyclerView = findViewById(R.id.recyclerView);
        et_search = findViewById(R.id.et_search);

        //recyclerView.setAdapter(new TenantsAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        et_search.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;

            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (et_search.getRight() - et_search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (et_search.hasFocus()) {
                        et_search.clearFocus();
                        et_search.setText("");
                    }
                    et_search.hideKeyboard();
                    return true;
                }
            }
            return false;
        });


    }
}