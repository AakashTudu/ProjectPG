package com.example.hostel.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hostel.R;

public class AfterSignupActivity extends AppCompatActivity {

    LinearLayout click_add_prop;
    String pass_ph_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_signup);

        click_add_prop = findViewById(R.id.click_add_prop);

        pass_ph_no = getIntent().getStringExtra("phoneNumber");

        click_add_prop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPropActivity.class);
                intent.putExtra("phoneNumber", pass_ph_no);
                startActivity(intent);
            }
        });
    }
}