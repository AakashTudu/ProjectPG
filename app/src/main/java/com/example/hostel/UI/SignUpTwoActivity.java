package com.example.hostel.UI;

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

import com.example.hostel.R;

public class SignUpTwoActivity extends AppCompatActivity {

    ImageView imageButton;
    Button submit_btn;
    TextView username, email, phno;
    EditText country,state,address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_two);

        //setting vale to the id
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phno = findViewById(R.id.phno);

        //Value to be passed to database
        username.setText(String.format(getIntent().getStringExtra("username")));
        email.setText(String.format(getIntent().getStringExtra("email")));
        phno.setText(String.format(getIntent().getStringExtra("phoneNumber")));



        imageButton = findViewById(R.id.imageButton);
        submit_btn = findViewById(R.id.submit_btn);
        country = findViewById(R.id.country);
        state = findViewById(R.id.state);
        address = findViewById(R.id.address);
        final ProgressBar progressBar = findViewById(R.id.progressbar_sending_otp);



        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpTwoActivity.this, SignUpThreeActivity.class);
                startActivity(intent);
            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                submit_btn.setVisibility(View.INVISIBLE);

                final String countryTxt = country.getText().toString();
                final String stateTxt = state.getText().toString();
                final String addressTxt = address.getText().toString();

                if(countryTxt.isEmpty() || stateTxt.isEmpty() || addressTxt.isEmpty()){
                    progressBar.setVisibility(View.INVISIBLE);
                    submit_btn.setVisibility(View.VISIBLE);
                    Toast.makeText(SignUpTwoActivity.this, "Please fill all the details..!", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressBar.setVisibility(View.INVISIBLE);
                    submit_btn.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(SignUpTwoActivity.this, SignUpThreeActivity.class);
                    intent.putExtra("username", username.getText().toString());
                    intent.putExtra("email", email.getText().toString());
                    intent.putExtra("phoneNumber", phno.getText().toString());
                    intent.putExtra("country", country.getText().toString());
                    intent.putExtra("state", state.getText().toString());
                    intent.putExtra("address", address.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}