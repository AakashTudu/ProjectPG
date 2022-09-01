package com.example.hostel.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hostel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class RecordPaymentActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton, btn_continue;
    ImageView back_btn;
    EditText tenantName, pendingAmt, paymentMode, amtPaid, receivedBy;
    String phoneNo, propertyNo;


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hostel-8a0f7-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_payment);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerBtn);
        dateButton.setText(getTodaysDate());

        phoneNo = getIntent().getStringExtra("phoneNumber");
        propertyNo =getIntent().getStringExtra("propertyNo");
        tenantName = findViewById(R.id.tenantName);
        pendingAmt = findViewById(R.id.pendingAmt);
        paymentMode = findViewById(R.id.paymentMode);
        amtPaid = findViewById(R.id.amtPaid);
        receivedBy = findViewById(R.id.receivedBy);
        btn_continue = findViewById(R.id.btn_continue);
        back_btn = findViewById(R.id.back_btn);

        back_btn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            intent.putExtra("phoneNumber", phoneNo);
            intent.putExtra("propertyNo", propertyNo);
            startActivity(intent);
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String TenantName = tenantName.getText().toString();
                final String PendingAmt = pendingAmt.getText().toString();
                final String Date = dateButton.getText().toString();
                final String PaymentMode = paymentMode.getText().toString();
                final String AmountPaid = amtPaid.getText().toString();
                final String ReceivedBy = receivedBy.getText().toString();

                if(!TenantName.isEmpty() && !PendingAmt.isEmpty() && !PaymentMode.isEmpty() && !AmountPaid.isEmpty() && !ReceivedBy.isEmpty()){

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            databaseReference.child("users").child(phoneNo).child("Property").child(propertyNo).child("Payments").child(TenantName).child(Date).child("PendingAmount").setValue(PendingAmt);
                            databaseReference.child("users").child(phoneNo).child("Property").child(propertyNo).child("Payments").child(TenantName).child(Date).child("PaymentMode").setValue(PaymentMode);
                            databaseReference.child("users").child(phoneNo).child("Property").child(propertyNo).child("Payments").child(TenantName).child(Date).child("AmountPaid").setValue(AmountPaid);
                            databaseReference.child("users").child(phoneNo).child("Property").child(propertyNo).child("Payments").child(TenantName).child(Date).child("ReceivedBy").setValue(ReceivedBy);

                            //Toast for success registration
                            Toast.makeText(getApplicationContext(), "Payment Record Created Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                            intent.putExtra("phoneNumber", phoneNo);
                            intent.putExtra("propertyNo", propertyNo);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }else{
                    Toast.makeText(RecordPaymentActivity.this, "Please Enter All Details...!", Toast.LENGTH_SHORT).show();
                }
            }


        });


    }





    //Setting Spinner Date
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style,dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year){
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEPT";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default return
        return "JAN";
    }


    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}