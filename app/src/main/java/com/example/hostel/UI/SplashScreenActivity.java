package com.example.hostel.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hostel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 2000;
    Boolean isNewUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

/*        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("users").orderByChild("userName").equalTo("Nick123");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                isNewUser = !snapshot.exists();
                Intent i;
                if (user != null) {
                    if (isNewUser)
                        i = new Intent(getApplicationContext(), ProfileActivity.class);
                    else
                        i = new Intent(getApplicationContext(), MainActivity.class);
                } else {
                    i = new Intent(getApplicationContext(), EnterNumberActivity.class);
                }
                startActivity(i);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user!=null){
            String number = user.getPhoneNumber();
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userNameRef = rootRef.child("users").child(number.replace("+91",""));
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i;
                            if (snapshot.exists())  // if snapshot  exist its mean that user exist
                                i = new Intent(getApplicationContext(), MainActivity.class);
                            else
                                i = new Intent(getApplicationContext(), ProfileActivity.class);
                            startActivity(i);
                            finish();
                        }
                    },1000);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("sdfsdfsdfsd", databaseError.getMessage()); //Don't ignore errors!
                }
            };
            userNameRef.addListenerForSingleValueEvent(eventListener);
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), EnterNumberActivity.class);
                    startActivity(i);
                    finish();
                }
            },2000);
        }



/*        FirebaseUser user = firebaseAuth.getCurrentUser();
        new Handler().postDelayed(() -> {
            Intent i;
            if (user != null) {
                if (isNewUser)
                    i = new Intent(getApplicationContext(), ProfileActivity.class);
                else
                    i = new Intent(getApplicationContext(), MainActivity.class);
            } else {
                i = new Intent(getApplicationContext(), EnterNumberActivity.class);
            }
            startActivity(i);
            finish();

        }, SPLASH_SCREEN_TIME_OUT);*/
    }
}