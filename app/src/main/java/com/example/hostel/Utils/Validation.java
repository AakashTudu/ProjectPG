package com.example.hostel.Utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.hostel.databinding.ActivityProfileBinding;
import com.example.hostel.databinding.FragmentAddTenantBinding;
import com.example.hostel.databinding.FragmentSelectSharingBinding;

public class Validation {
    public static boolean addTenantPage(FragmentAddTenantBinding binding) {

        Context context = binding.getRoot().getContext();

        if (binding.etTenantName.getText().toString().equals("")) {
            Toast.makeText(context, "Please enter name...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (binding.etNumber.getText().toString().equals("")) {
            Toast.makeText(context, "Please enter mobile number...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (binding.etEmailId.getText().toString().equals("")) {
            Toast.makeText(context, "Enter email id...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (binding.tvDOB.getText().toString().equals("")) {
            Toast.makeText(context, "Enter Date of birth...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (binding.tvOccupation.getText().toString().equals("")){
            Toast.makeText(context, "Please Select Occupation...", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public static boolean profilePage(ActivityProfileBinding binding) {
        Context context = binding.getRoot().getContext();

        if (binding.etName.getText().toString().equals("")) {
            Toast.makeText(context, "Please enter name...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (binding.etEmailId.getText().toString().equals("")) {
            Toast.makeText(context, "Please enter valid email...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (binding.etCity.getText().toString().equals("")) {
            Toast.makeText(context, "Please enter city...", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean selectSharingPage(FragmentSelectSharingBinding binding) {

        Context context = binding.getRoot().getContext();

        if (binding.tvSharingType.getText().toString().equals("")) {
            Toast.makeText(context, "Please selected sharing type...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (binding.etRoomNumber.getText().toString().equals("")) {
            Toast.makeText(context, "Please enter room number...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (binding.etBedNumber.getText().toString().equals("")) {
            Toast.makeText(context, "Please enter bed number...", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
