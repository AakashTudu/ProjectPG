package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;

public class SignUpThreeActivity extends AppCompatActivity {

    ImageView imageButton;
    Button submit_btn;
    TextView username, email, country, state, address, phone;
    EditText pan_no, gstin_no;


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hostel-8a0f7-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_three);

        //Value to be passed to database
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        country = findViewById(R.id.country);
        state = findViewById(R.id.state);
        address = findViewById(R.id.address);

        username.setText(String.format(getIntent().getStringExtra("username")));
        email.setText(String.format(getIntent().getStringExtra("email")));
        phone.setText(String.format(getIntent().getStringExtra("phoneNumber")));
        country.setText(String.format(getIntent().getStringExtra("country")));
        state.setText(String.format(getIntent().getStringExtra("state")));
        address.setText(String.format(getIntent().getStringExtra("address")));

        imageButton = findViewById(R.id.imageButton);
        submit_btn = findViewById(R.id.submit_btn);
        pan_no = findViewById(R.id.pan_no);
        gstin_no = findViewById(R.id.gstin_no);
        final ProgressBar progressBar = findViewById(R.id.progressbar_sending_otp);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpThreeActivity.this, SignUpTwoActivity.class);
                startActivity(intent);
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

                //Get text from activity
                final String phoneNo = phone.getText().toString();
                final String usernameTxt = username.getText().toString();
                final String emailTxt = email.getText().toString();
                final String countryTxt = country.getText().toString();
                final String stateTxt = state.getText().toString();
                final String addressTxt = address.getText().toString();
                final String panTxt = pan_no.getText().toString();
                final String gstTxt = gstin_no.getText().toString();

                if(panTxt.isEmpty()||gstTxt.isEmpty()){
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            progressBar.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);

                            //Sending data to Realtime Database
                            databaseReference.child("users").child(phoneNo).child("username").setValue(usernameTxt);
                            databaseReference.child("users").child(phoneNo).child("email").setValue(emailTxt);
                            databaseReference.child("users").child(phoneNo).child("country").setValue(countryTxt);
                            databaseReference.child("users").child(phoneNo).child("state").setValue(stateTxt);
                            databaseReference.child("users").child(phoneNo).child("address").setValue(addressTxt);

                            //Toast for success registration
                            Toast.makeText(SignUpThreeActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpThreeActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressBar.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(SignUpThreeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //Sending data to Realtime Database
                            databaseReference.child("users").child(phoneNo).child("username").setValue(usernameTxt);
                            databaseReference.child("users").child(phoneNo).child("email").setValue(emailTxt);
                            databaseReference.child("users").child(phoneNo).child("country").setValue(countryTxt);
                            databaseReference.child("users").child(phoneNo).child("state").setValue(stateTxt);
                            databaseReference.child("users").child(phoneNo).child("address").setValue(addressTxt);
                            databaseReference.child("users").child(phoneNo).child("pan_no").setValue(panTxt);
                            databaseReference.child("users").child(phoneNo).child("gstin_no").setValue(gstTxt);

                            //Toast for success registration
                            progressBar.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(SignUpThreeActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpThreeActivity.this, LoginActivity.class);
                            startActivity(intent);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressBar.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(SignUpThreeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        });

    }
}