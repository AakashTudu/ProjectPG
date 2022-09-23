package com.example.hostel.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hostel.Utils.Validation;
import com.example.hostel.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {


    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        String number = user.getPhoneNumber();


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child("users").child(number.replace("+91", ""));

        binding.btnContinue.setOnClickListener(view -> {

            if (Validation.profilePage(binding)) {

                binding.linearProgressIndicator.setVisibility(View.VISIBLE);
                Map<String, Object> map = new HashMap<>();
                map.put("name", binding.etName.getText().toString());
                map.put("email", binding.etEmailId.getText().toString());
                map.put("city", binding.etCity.getText().toString());

                userRef.setValue(map).addOnSuccessListener(unused -> {
                    binding.linearProgressIndicator.setVisibility(View.GONE);
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    finish();
                });

            }
        });
    }
}