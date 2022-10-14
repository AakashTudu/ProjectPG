package com.example.hostel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.hostel.DTO.AddTenantDTO;
import com.example.hostel.Utils.BottomSheet;
import com.example.hostel.Utils.Validation;
import com.example.hostel.databinding.FragmentAddTenantFirstBinding;

public class AddTenantFirstFragment extends Fragment {

    FragmentAddTenantFirstBinding binding;
    AddTenantFirstFragmentArgs args;

    public AddTenantFirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTenantFirstBinding.inflate(inflater, container, false);
        args = AddTenantFirstFragmentArgs.fromBundle(getArguments());

        AddTenantDTO addTenantDTO = args.getAddTenantDTO();

        binding.tvOccupation.setOnClickListener(view1 -> {
            BottomSheet.showOccupationOptionDialog(getContext(), data -> {
                binding.tvOccupation.setText(data);
            });
        });


        binding.btnContinue.setOnClickListener(view -> {
            if (Validation.addTenantFirstPage(binding)) {

                addTenantDTO.setName(binding.etTenantName.getText().toString());
                addTenantDTO.setPhoneNumber(binding.etNumber.getText().toString());
                addTenantDTO.setEmailId(binding.etEmailId.getText().toString());
                addTenantDTO.setOccupation(binding.tvOccupation.getText().toString());

                Navigation.findNavController(view).navigate(
                    AddTenantFirstFragmentDirections.actionAddTenantFirstFragmentToAddTenantSecondFragment(addTenantDTO)
                );

            }
        });

        return binding.getRoot();
    }
}