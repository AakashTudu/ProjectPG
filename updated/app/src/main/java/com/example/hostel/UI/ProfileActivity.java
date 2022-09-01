package com.example.hostel.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hostel.R;
import com.google.android.material.button.MaterialButton;

public class ProfileActivity extends AppCompatActivity {

    MaterialButton btn_continue;
    String phoneNo; //admin_user, admin_email, admin_cityLocation;
    EditText admin_username, admin_emailid, admin_city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btn_continue = findViewById(R.id.btn_continue);
        admin_username = findViewById(R.id.admin_username);
        admin_emailid = findViewById(R.id.admin_emailid);
        admin_city = findViewById(R.id.admin_city);


        //admin_user = admin_username.getText().toString();
        //admin_email = admin_emailid.getText().toString();
        //admin_cityLocation = admin_city.getText().toString();

        //phoneNo = getIntent().getStringExtra("phoneNumber");
        phoneNo = "7001485295";
        btn_continue.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, AddPropActivity.class);
            intent.putExtra("phoneNumber", phoneNo);
            intent.putExtra("admin_username", admin_username.getText().toString());
            intent.putExtra("admin_email", admin_emailid.getText().toString());
            intent.putExtra("admin_city", admin_city.getText().toString());
            startActivity(intent);
        });
    }
}