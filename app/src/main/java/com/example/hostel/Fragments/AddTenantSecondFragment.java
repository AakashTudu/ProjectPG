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
import com.example.hostel.DTO.AddTenantDTO;
import com.example.hostel.Enums.FragmentEnum;
import com.example.hostel.Utils.Validation;
import com.example.hostel.databinding.FragmentAddTenantSecondBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTenantSecondFragment extends Fragment {

    FragmentAddTenantSecondBinding binding;

    public AddTenantSecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTenantSecondBinding.inflate(inflater, container, false);
        AddTenantSecondFragmentArgs args = AddTenantSecondFragmentArgs.fromBundle(getArguments());
        AddTenantDTO addTenantDTO = args.getAddTenantDTO();

        binding.tvDOB.setOnClickListener(view -> {
            showDatePickerDialog();
        });

        binding.btnContinue.setOnClickListener(view -> {
            if (Validation.addTenantSecondPage(binding)) {
                addTenantDTO.setDOB(binding.tvDOB.getText().toString());
                addTenantDTO.setGender(binding.radioGroup.getCheckedRadioButtonId() == binding.rbMale.getId());
                addTenantDTO.setMartialStatus(binding.switchMartialStatus.isChecked());

                if (addTenantDTO.getFragmentEnum() == FragmentEnum.USER_PROPERTY){
                    Navigation.findNavController(view).navigate(
                            AddTenantSecondFragmentDirections.actionAddTenantSecondFragmentToSelectRoomFragment(addTenantDTO)
                    );
                }else{
                    Navigation.findNavController(view).navigate(
                            AddTenantSecondFragmentDirections.actionAddTenantSecondFragmentToSelectPropertyFragment(addTenantDTO)
                    );
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