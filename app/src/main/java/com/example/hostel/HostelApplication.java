package com.example.hostel;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class HostelApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
