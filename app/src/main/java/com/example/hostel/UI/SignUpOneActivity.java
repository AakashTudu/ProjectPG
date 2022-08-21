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

public class SignUpOneActivity extends AppCompatActivity {

    ImageView imageButton;
    Button submit_btn;
    EditText user_text, email_id;
    TextView phno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_one);

        imageButton = findViewById(R.id.imageButton);
        submit_btn = findViewById(R.id.submit_btn);
        user_text = findViewById(R.id.user_text);
        email_id = findViewById(R.id.email_id);
        phno = findViewById(R.id.phno);


        phno.setText(String.format(getIntent().getStringExtra("phoneNumber")));
        final ProgressBar progressBar = findViewById(R.id.progressbar_sending_otp);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpOneActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                submit_btn.setVisibility(View.INVISIBLE);

                final String usernameTxt = user_text.getText().toString();
                final String emailTxt = email_id.getText().toString();
                //final String phnoTxt = phno.getText().toString();
                //Check if the necessary fields are not empty
                if(usernameTxt.isEmpty() || emailTxt.isEmpty()){

                    progressBar.setVisibility(View.INVISIBLE);
                    submit_btn.setVisibility(View.VISIBLE);
                    Toast.makeText(SignUpOneActivity.this, "Please fill all the details..!", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressBar.setVisibility(View.INVISIBLE);
                    submit_btn.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(SignUpOneActivity.this, SignUpTwoActivity.class);
                    intent.putExtra("username", user_text.getText().toString());
                    intent.putExtra("email", email_id.getText().toString());
                    intent.putExtra("phoneNumber", phno.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}