package com.example.hostel.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedPref {

    public static final String emptyPropertyCheck = "emptyPropertyCheck";

    public static SharedPreferences initializeSharedPreferenceForEmptyPropertyCheck(Context context){
        return context.getSharedPreferences(emptyPropertyCheck, Context.MODE_PRIVATE);
    }
}
