package com.example.hostel.UI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hostel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EnterOtpActivity extends AppCompatActivity {

    //Button sub_otp;
    EditText inputotp1, inputotp2, inputotp3, inputotp4, inputotp5, inputotp6;
    TextView textView;
    String just_no, phoneNo, backEndOtp;
    ProgressBar progressBar;

    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        //Hooks
        progressBar = findViewById(R.id.progressbar_sending_otp);
        tvDescription = findViewById(R.id.tvDescription);
        //sub_otp = findViewById(R.id.sub_otp);

        inputotp1 = findViewById(R.id.inputotp1);
        inputotp2 = findViewById(R.id.inputotp2);
        inputotp3 = findViewById(R.id.inputotp3);
        inputotp4 = findViewById(R.id.inputotp4);
        inputotp5 = findViewById(R.id.inputotp5);
        inputotp6 = findViewById(R.id.inputotp6);
        //sub_otp = findViewById(R.id.sub_otp);

        //Showing the captured mobile number
        textView = findViewById(R.id.phoneNo);
        just_no = getIntent().getStringExtra("mobile");
        phoneNo = just_no;
        String html = "We've sent verification code to <b>" + getIntent().getStringExtra("mobile") + "</b>";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvDescription.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvDescription.setText(Html.fromHtml(html));
        }

        backEndOtp = getIntent().getStringExtra("backendotp");


        numberotpmove();
        numberotpmoveback();


    }

    private void numberotpmove() {

        inputotp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
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
                if (!charSequence.toString().trim().isEmpty()) {
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
                if (!charSequence.toString().trim().isEmpty()) {
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
                if (!charSequence.toString().trim().isEmpty()) {
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
                if (!charSequence.toString().trim().isEmpty()) {
                    inputotp6.requestFocus();

                    TextWatcher textWatcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            if (!inputotp1.getText().toString().trim().isEmpty() && !inputotp2.getText().toString().trim().isEmpty() && !inputotp2.getText().toString().trim().isEmpty() && !inputotp3.getText().toString().trim().isEmpty() && !inputotp4.getText().toString().trim().isEmpty() && !inputotp5.getText().toString().trim().isEmpty() && !inputotp6.getText().toString().trim().isEmpty()) {

                                LinearLayout linearLayout = findViewById(R.id.hide_layout);
                                linearLayout.setVisibility(View.GONE);
                                progressBar.setVisibility(View.VISIBLE);

                                String enterotp = inputotp1.getText().toString() +
                                        inputotp2.getText().toString() +
                                        inputotp3.getText().toString() +
                                        inputotp4.getText().toString() +
                                        inputotp5.getText().toString() +
                                        inputotp6.getText().toString();

                                if (backEndOtp != null) {
                                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                            backEndOtp, enterotp
                                    );

                                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                                                        DatabaseReference userNameRef = rootRef.child("users").child(phoneNo);
                                                        ValueEventListener eventListener = new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot snapshot) {

                                                                Intent i;
                                                                if (snapshot.exists())  // if snapshot  exist its mean that user exist
                                                                    i = new Intent(getApplicationContext(), MainActivity.class);
                                                                else
                                                                    i = new Intent(getApplicationContext(), ProfileActivity.class);
                                                                startActivity(i);
                                                                finish();
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {
                                                                Log.d("sdfsdfsdfsd", databaseError.getMessage()); //Don't ignore errors!
                                                            }
                                                        };
                                                        userNameRef.addListenerForSingleValueEvent(eventListener);
                                                    } else {
                                                        Toast.makeText(EnterOtpActivity.this, "Enter the correct OTP", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                            });
                                } else {
                                    Toast.makeText(EnterOtpActivity.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(EnterOtpActivity.this, "please enter all number", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    };

                    inputotp1.addTextChangedListener(textWatcher);
                    inputotp2.addTextChangedListener(textWatcher);
                    inputotp3.addTextChangedListener(textWatcher);
                    inputotp4.addTextChangedListener(textWatcher);
                    inputotp5.addTextChangedListener(textWatcher);
                    inputotp6.addTextChangedListener(textWatcher);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void numberotpmoveback() {

        inputotp6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().isEmpty()) {
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
                if (charSequence.toString().trim().isEmpty()) {
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
                if (charSequence.toString().trim().isEmpty()) {
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
                if (charSequence.toString().trim().isEmpty()) {
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
                if (charSequence.toString().trim().isEmpty()) {
                    inputotp1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}