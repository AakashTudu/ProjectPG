package com.example.hostel.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddPropActivity extends AppCompatActivity {

    Button button;
    EditText propertyName, cityName, propertyLocation;
    LinearLayout men_layout, fem_layout, co_layout;
    //String men,fem,co;
    //Spinner spinner;
    String value, phoneNo, admin_name, admin_email, admin_cityLocation, propertyNo = "one";
    //String[] months = {"Select Property Type", "Apartment", "Flat", "Ladies PG", "Mens PG", "Coed", "Studio Room"};


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hostel-8a0f7-default-rtdb.firebaseio.com/");
    boolean iscolor = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prop);

        //spinner = findViewById(R.id.spinner);
        button = findViewById(R.id.button);
        phoneNo = getIntent().getStringExtra("phoneNumber");
        admin_name = getIntent().getStringExtra("admin_username");
        admin_email = getIntent().getStringExtra("admin_email");
        admin_cityLocation = getIntent().getStringExtra("admin_city");

        System.out.println(admin_name);
        System.out.println(admin_email);
        System.out.println(admin_cityLocation);
        //phoneNo = "7001485295";
        propertyName = findViewById(R.id.PropertyName);
        cityName = findViewById(R.id.CityName);
        propertyLocation = findViewById(R.id.PropertyLocation);
        men_layout = findViewById(R.id.men_layout);
        fem_layout = findViewById(R.id.fem_layout);
        co_layout = findViewById(R.id.co_layout);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddPropActivity.this, android.R.layout.simple_spinner_item, months);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);

        //spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        //  @Override
        //public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //  value = adapterView.getItemAtPosition(i).toString();
        //}

        //@Override
        //public void onNothingSelected(AdapterView<?> adapterView) {

        //}
        //});

        men_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (iscolor) {
                    men_layout.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.layout_sele));
                    fem_layout.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.text_input_layout));
                    co_layout.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.text_input_layout));
                    iscolor = true;
                    value = "Men's Pg";

                } else {
                    men_layout.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.text_input_layout));
                    iscolor = false;
                }
                System.out.println(value);
            }
        });
        fem_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (iscolor) {
                    men_layout.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.text_input_layout));
                    fem_layout.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.layout_sele));
                    co_layout.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.text_input_layout));
                    iscolor = true;
                    value = "Ladies PG";
                } else {
                    fem_layout.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.text_input_layout));
                    iscolor = false;
                }
                System.out.println(value);
            }
        });
        co_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (iscolor) {
                    men_layout.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.text_input_layout));
                    fem_layout.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.text_input_layout));
                    co_layout.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.layout_sele));
                    iscolor = true;
                    value = "Coed PG";
                } else {
                    co_layout.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.text_input_layout));
                    iscolor = false;
                }
                System.out.println(value);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String PropName = propertyName.getText().toString();
                final String PropertyType = value;
                final String CityName = cityName.getText().toString();
                final String PropLocation = propertyLocation.getText().toString();

                if (!PropName.isEmpty() && !PropertyType.isEmpty() && !CityName.isEmpty() && !PropLocation.isEmpty()) {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            databaseReference.child("users").child(phoneNo).child("AdminUserName").setValue(admin_name);
                            databaseReference.child("users").child(phoneNo).child("AdminEmailId").setValue(admin_email);
                            databaseReference.child("users").child(phoneNo).child("AdminCityLocation").setValue(admin_cityLocation);
                            databaseReference.child("users").child(phoneNo).child("Property").child(propertyNo).child("PropertyName").setValue(PropName);
                            databaseReference.child("users").child(phoneNo).child("Property").child(propertyNo).child("PropertyType").setValue(PropertyType);
                            databaseReference.child("users").child(phoneNo).child("Property").child(propertyNo).child("PropertyLocation").setValue(PropLocation);
                            databaseReference.child("users").child(phoneNo).child("Property").child(propertyNo).child("CityName").setValue(CityName);

                            Intent intent = new Intent(getApplicationContext(), TotalFloorsActivity.class);
                            intent.putExtra("phoneNumber", phoneNo);
                            intent.putExtra("propertyNo", propertyNo);
                            startActivity(intent);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    Toast.makeText(AddPropActivity.this, "Please Enter all details", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}