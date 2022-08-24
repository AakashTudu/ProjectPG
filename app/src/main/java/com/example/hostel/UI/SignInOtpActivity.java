package com.example.hostel.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class SignInOtpActivity extends AppCompatActivity {

    ImageView imageButton;
    Button sub_otp;
    EditText inputotp1, inputotp2, inputotp3, inputotp4,inputotp5,inputotp6;
    TextView textView;


    String backEndOtp;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hostel-8a0f7-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_otp);

        //Hooks
        imageButton = findViewById(R.id.imageButton);
        sub_otp = findViewById(R.id.sub_otp);
        final ProgressBar progressBar = findViewById(R.id.progressbar_sending_otp);

        inputotp1 = findViewById(R.id.inputotp1);
        inputotp2 = findViewById(R.id.inputotp2);
        inputotp3 = findViewById(R.id.inputotp3);
        inputotp4 = findViewById(R.id.inputotp4);
        inputotp5 = findViewById(R.id.inputotp5);
        inputotp6 = findViewById(R.id.inputotp6);
        sub_otp = findViewById(R.id.sub_otp);

        //Showing the captured mobile number
        textView = findViewById(R.id.phoneNo);
        String just_no = getIntent().getStringExtra("mobile");
        textView.setText(String.format(
                "+91-%s", getIntent().getStringExtra("mobile")
        ));

        backEndOtp = getIntent().getStringExtra("backendotp");


        //Back Button(Image)
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInOtpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //
        sub_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String phoneNo = just_no;
                if(!inputotp1.getText().toString().trim().isEmpty() && !inputotp2.getText().toString().trim().isEmpty() && !inputotp2.getText().toString().trim().isEmpty() && !inputotp3.getText().toString().trim().isEmpty() && !inputotp4.getText().toString().trim().isEmpty() && !inputotp5.getText().toString().trim().isEmpty() && !inputotp6.getText().toString().trim().isEmpty())
                {
                    progressBar.setVisibility(View.VISIBLE);
                    sub_otp.setVisibility(View.INVISIBLE);
                    String enterotp = inputotp1.getText().toString()+
                            inputotp2.getText().toString()+
                            inputotp3.getText().toString()+
                            inputotp4.getText().toString()+
                            inputotp5.getText().toString()+
                            inputotp6.getText().toString();

                    if(backEndOtp!=null){

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                backEndOtp, enterotp
                        );

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                    if(snapshot.hasChild(phoneNo)){

                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        sub_otp.setVisibility(View.VISIBLE);
                                                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }else{

                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        sub_otp.setVisibility(View.VISIBLE);

                                                        Intent intent = new Intent(SignInOtpActivity.this, SignUpOneActivity.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        Toast.makeText(SignInOtpActivity.this, "OTP Verification Successful", Toast.LENGTH_SHORT).show();
                                                        intent.putExtra("phoneNumber",just_no);
                                                        startActivity(intent);

                                                    }

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    sub_otp.setVisibility(View.VISIBLE);

                                                }
                                            });
                                        }else{

                                            progressBar.setVisibility(View.INVISIBLE);
                                            sub_otp.setVisibility(View.VISIBLE);
                                            Toast.makeText(SignInOtpActivity.this, "Enter the correct OTP", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
                        sub_otp.setVisibility(View.VISIBLE);
                        Toast.makeText(SignInOtpActivity.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    sub_otp.setVisibility(View.VISIBLE);
                    Toast.makeText(SignInOtpActivity.this, "please enter all number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        numberotpmove();
        numberotpmoveback();

        TextView resentOtp = findViewById(R.id.resendOtp);

        resentOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                sub_otp.setVisibility(View.INVISIBLE);
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        SignInOtpActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.INVISIBLE);
                                sub_otp.setVisibility(View.VISIBLE);
                                Toast.makeText(SignInOtpActivity.this, "Verification Completed", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.INVISIBLE);
                                sub_otp.setVisibility(View.VISIBLE);
                                Toast.makeText(SignInOtpActivity.this, "Error..!!! Please Check Internet Connection"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);

                                backEndOtp = s;
                                progressBar.setVisibility(View.INVISIBLE);
                                sub_otp.setVisibility(View.VISIBLE);
                                Toast.makeText(SignInOtpActivity.this, "OTP send successful..", Toast.LENGTH_SHORT).show();

                            }
                        }
                );
            }
        });

    }

    private void numberotpmove() {

        inputotp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputotp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputotp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputotp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputotp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputotp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputotp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputotp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputotp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputotp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private void numberotpmoveback(){

        inputotp6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().isEmpty()) {
                    inputotp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputotp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().isEmpty()) {
                    inputotp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputotp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().isEmpty()) {
                    inputotp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputotp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().isEmpty()) {
                    inputotp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputotp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().isEmpty()) {
                    inputotp1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}


