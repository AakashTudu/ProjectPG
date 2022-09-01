package com.example.hostel.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hostel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DashboardActivity extends AppCompatActivity {

    Button recordPay;
    ImageView iv_money_custom;
    TextView username;
    String phoneNo, propertyNo = "one";
    int i=0;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hostel-8a0f7-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        phoneNo = getIntent().getStringExtra("phoneNumber");
        //phoneNo = "7001485295";
        username = findViewById(R.id.username);
        recordPay = findViewById(R.id.recordPay);
        iv_money_custom = findViewById(R.id.iv_money_custom);

        //Bottom Navigation
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.dashboard:
                        return true;
                    case R.id.manage:
                        Intent intent = new Intent(getApplicationContext(), ManageActivity.class);
                        intent.putExtra("phoneNumber", phoneNo);
                        intent.putExtra("propertyNo", propertyNo);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.property:
                        intent = new Intent(getApplicationContext(),PropertiesActivity.class);
                        intent.putExtra("phoneNumber", phoneNo);
                        intent.putExtra("propertyNo", propertyNo);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        databaseReference
                .child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(phoneNo)) {

                            String username_value = Objects.requireNonNull(snapshot.child(phoneNo).child("AdminUserName").getValue()).toString();
                            System.out.println(username_value);
                            username.setText(username_value);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        recordPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecordPaymentActivity.class);
                intent.putExtra("phoneNumber", phoneNo);
                intent.putExtra("propertyNo", propertyNo);
                startActivity(intent);
            }
        });

        iv_money_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CollectionActivity.class);
                intent.putExtra("phoneNumber", phoneNo);
                intent.putExtra("propertyNo", propertyNo);
                startActivity(intent);
            }
        });


    }
}