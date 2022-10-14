package com.example.hostel.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.hostel.CustomViews.DatePickerFragment;
import com.example.hostel.Models.Property;
import com.example.hostel.R;
import com.example.hostel.Utils.BottomSheet;
import com.example.hostel.Utils.Constants;
import com.example.hostel.Utils.Validation;
import com.example.hostel.databinding.FragmentAddTenantBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTenantFragment extends Fragment {

    FragmentAddTenantBinding binding;
    String ref;
    Bundle bundle;

    public AddTenantFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTenantBinding.inflate(inflater, container, false);
        bundle = new Bundle();
        ref = getArguments().getString(Constants.propertyRef);
        Property property = (Property) getArguments().getSerializable(Constants.property);

        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });


        binding.tvDOB.setOnClickListener(view -> {
            showDatePickerDialog();
        });

        binding.tvOccupation.setOnClickListener(view1 -> {
            BottomSheet.showOccupationOptionDialog(getContext(), data -> {
                bundle.putString(Constants.occupation, data);
                binding.tvOccupation.setText(data);
            });
        });


        binding.btnContinue.setOnClickListener(view -> {
            bundle.putString(Constants.propertyRef, ref);

            if (Validation.addTenantPage(binding)) {
                bundle.putString(Constants.tenantName, binding.etTenantName.getText().toString());
                bundle.putString(Constants.mobileNumber, binding.etNumber.getText().toString());
                bundle.putString(Constants.emailId, binding.etEmailId.getText().toString());
                bundle.putString(Constants.date, binding.tvDOB.getText().toString());

                if (binding.radioGroup.getCheckedRadioButtonId() == binding.rbMale.getId())
                    bundle.putString(Constants.gender, "Male");
                else
                    bundle.putString(Constants.gender, "Female");

                if (binding.switchMartialStatus.isChecked())
                    bundle.putString(Constants.martialStatus, "Single");
                else
                    bundle.putString(Constants.martialStatus, "Married");


                if (ref == null) {
                    Navigation.findNavController(view).navigate(R.id.action_addTenantFragment_to_selectPropertyFragment, bundle);
                } else {
                    bundle.putSerializable(Constants.property, property);
                    Navigation.findNavController(view).navigate(R.id.action_addTenantFragment_to_selectSharingFragment, bundle);
                }

            }
        });

        return binding.getRoot();
    }

    public void showDatePickerDialog() {
        DatePickerFragment dateDialog = new DatePickerFragment();
        dateDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-");
                Date d = new Date(year, month, day);
                String date = dateFormatter.format(d);
                binding.tvDOB.setText(date + year);
            }
        });
        dateDialog.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
}