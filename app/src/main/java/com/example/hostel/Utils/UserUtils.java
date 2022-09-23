package com.example.hostel.Utils;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserUtils {
    public static String phoneNumber() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null)
            return user.getPhoneNumber().replace("+91", "");
        return null;
    }
}
