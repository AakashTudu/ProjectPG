package com.example.hostel.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hostel.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterNumberActivity extends AppCompatActivity{



    Button genOtp;
    EditText input_mobile_number;
    RelativeLayout hs_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_number);

        //Hooks
        genOtp = findViewById(R.id.genOtp);
        input_mobile_number = findViewById(R.id.input_mobile_number);
        hs_layout = findViewById(R.id.hs_layout);


        //Generate OTP function
        final ProgressBar progressBar = findViewById(R.id.progressbar_sending_otp);

        genOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!input_mobile_number.getText().toString().trim().isEmpty()){
                    if((input_mobile_number.getText().toString().trim()).length()==10){

                        progressBar.setVisibility(View.VISIBLE);
                        genOtp.setVisibility(View.INVISIBLE);
                        hs_layout.setVisibility(View.INVISIBLE);


                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + input_mobile_number.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                EnterNumberActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                        progressBar.setVisibility(View.GONE);
                                        genOtp.setVisibility(View.VISIBLE);
                                        hs_layout.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {

                                        progressBar.setVisibility(View.GONE);
                                        genOtp.setVisibility(View.VISIBLE);
                                        hs_layout.setVisibility(View.VISIBLE);
                                        Toast.makeText(EnterNumberActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        super.onCodeSent(s, forceResendingToken);

                                        progressBar.setVisibility(View.GONE);
                                        genOtp.setVisibility(View.VISIBLE);
                                        hs_layout.setVisibility(View.VISIBLE);

                                        Intent intent = new Intent(getApplicationContext(),EnterOtpActivity.class);
                                        intent.putExtra("mobile", input_mobile_number.getText().toString());
                                        intent.putExtra("backendotp",s);
                                        startActivity(intent);

                                    }
                                }
                        );


                    }else{
                        Toast.makeText(EnterNumberActivity.this, "Please enter correct mobile number!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(EnterNumberActivity.this, "Enter Mobile Number!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}



