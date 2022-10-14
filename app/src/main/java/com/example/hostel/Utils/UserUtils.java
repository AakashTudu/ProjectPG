package com.example.hostel.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ibm.icu.text.RuleBasedNumberFormat;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserUtils {
    public static String phoneNumber() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) return user.getPhoneNumber().replace("+91", "");
        return null;
    }

    public static void call(String phoneNumber, Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    public static String getMonth(String date) throws ParseException {
        DateFormat originalFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("MMM yyyy");
        Date dateObj = originalFormat.parse(date);
        return targetFormat.format(dateObj);
    }

    public static String getBankImageUrl(String bankName, Context context) throws JSONException {
        JSONObject bankJsonObject = new JSONObject(getJson(context));
        return bankJsonObject.getString(bankName);
    }

    private static String getJson(Context context) {
        String json = null;
        try {
            // Opening cities.json file
            InputStream is = context.getAssets().open("banks.json");
            // is there any content in the file
            int size = is.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            is.read(buffer);
            // close the stream --- very important
            is.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return json;
        }
        return json;
    }

    public static String getOccupancy(int number) {
        String occupancy = null;

        switch (number) {
            case 1:
                occupancy = "Single Occupancy";
                break;
            case 2:
                occupancy = "Double Occupancy";
                break;
            case 3:
                occupancy = "Triple Occupancy";
                break;
            case 4:
                occupancy = "Four Occupancy";
                break;
            case 5:
                occupancy = "Five Occupancy";
                break;
            case 6:
                occupancy = "Six Occupancy";
                break;
            case 7:
                occupancy = "Seven Occupancy";
                break;
            case 8:
                occupancy = "Eight Occupancy";
                break;
            case 9:
                occupancy = "Nine Occupancy";
                break;
            case 10:
                occupancy = "Ten Occupancy";
                break;
        }
        return occupancy;
    }

    public static String numberToOrdinalWord(int number) {
        RuleBasedNumberFormat nf = new RuleBasedNumberFormat(Locale.UK, RuleBasedNumberFormat.SPELLOUT);
        String floor = nf.format(number, "%spellout-ordinal").replace("-", " ");
        floor = capitalize(floor);
        if (number == -1) {
            floor = "Basement";
        } else if (number == 0) {
            floor = "Ground";
        }
        return floor + " Floor";
    }

    public static String numberToOrdinal(int number) {
        RuleBasedNumberFormat nf = new RuleBasedNumberFormat(Locale.UK, RuleBasedNumberFormat.ORDINAL);
        String floor = nf.format(number);
        if (number == -1) {
            floor = "Basement";
        } else if (number == 0) {
            floor = "Ground";
        }
        return floor + " Floor";
    }

    public static String capitalize(String str) {
        return StringUtils.capitalize(str);
    }
}
