package com.example.hostel.Utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.hostel.databinding.ActivityProfileBinding;
import com.example.hostel.databinding.FragmentAddBankBinding;
import com.example.hostel.databinding.FragmentAddTenantBinding;
import com.example.hostel.databinding.FragmentAddTenantFirstBinding;
import com.example.hostel.databinding.FragmentAddTenantSecondBinding;
import com.example.hostel.databinding.FragmentSelectSharingBinding;
import com.google.android.material.snackbar.Snackbar;

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

        if (binding.tvOccupation.getText().toString().equals("")) {
            Toast.makeText(context, "Please Select Occupation...", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public static boolean addTenantFirstPage(FragmentAddTenantFirstBinding binding) {

        View view = binding.getRoot().getRootView();

        if (binding.etTenantName.getText().toString().equals("")) {
            Snackbar.make(view, "Please enter name...", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (binding.etNumber.getText().toString().equals("")) {
            Snackbar.make(view, "Please enter mobile number...", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (binding.etEmailId.getText().toString().equals("")) {
            Snackbar.make(view, "Enter email id...", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (binding.tvOccupation.getText().toString().equals("")) {
            Snackbar.make(view, "Please Select Occupation...", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public static boolean addTenantSecondPage(FragmentAddTenantSecondBinding binding) {
        View view = binding.getRoot().getRootView();

        if (binding.tvDOB.getText().toString().equals("")) {
            Snackbar.make(view, "Enter Date of birth...", Snackbar.LENGTH_SHORT).show();
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

        View view = binding.getRoot().getRootView();
        if (binding.etRoomRent.getText().toString().equals("")) {
            Snackbar.make(view, "Please enter room rent...", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public static boolean addBankPage(FragmentAddBankBinding binding) {
        View view = binding.getRoot().getRootView();
        if (binding.etSelectBank.getText().toString().equals("")) {
            Snackbar.make(view, "Please Select Bank...", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (binding.etHolderName.getText().toString().equals("")) {
            Snackbar.make(view, "Please enter holder name...", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (binding.etAccountNumber.getText().toString().equals("")) {
            Snackbar.make(view, "Please enter account number...", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (binding.etIfscCode.getText().toString().equals("")) {
            Snackbar.make(view, "Please enter ifsc code...", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
