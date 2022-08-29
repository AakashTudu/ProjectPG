package com.example.hostel.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Adapters.ManageAdapter;
import com.example.hostel.Models.Option;
import com.example.hostel.R;

import java.util.ArrayList;

public class ManageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Option> optionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        recyclerView = findViewById(R.id.recyclerView);
        optionList = new ArrayList<Option>() {{
            add(new Option(
                    R.drawable.ic_tenant,
                    "TENANT",
                    getResources().getString(R.string.add_search_your_tenants)));

            add(new Option(
                    R.drawable.ic_vacancy,
                    "ROOM \n" +
                            "VACANCIES",
                    getResources().getString(R.string.check_room_vacancies)));

            add(new Option(
                    R.drawable.ic_enquiries,
                    "ENQUIRIES",
                    getResources().getString(R.string.clear_your_doubts)));

            add(new Option(
                    R.drawable.ic_booking,
                    "BOOKING’S",
                    getResources().getString(R.string.check_your_booking_s_status)));

            add(new Option(
                    R.drawable.ic_report,
                    "REPORT\n" +
                            "COMPLAINTS",
                    getResources().getString(R.string.report_your_complaints)));

            add(new Option(
                    R.drawable.ic_expense,
                    "EXPENSES",
                    getResources().getString(R.string.track_your_expenses)));
        }};

        recyclerView.setAdapter(new ManageAdapter(optionList));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}