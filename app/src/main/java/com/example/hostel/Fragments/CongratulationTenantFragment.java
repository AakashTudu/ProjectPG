package com.example.hostel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.DTO.AddTenantDTO;
import com.example.hostel.R;
import com.example.hostel.databinding.FragmentCongratulationTenantBinding;

public class CongratulationTenantFragment extends Fragment {

    FragmentCongratulationTenantBinding binding;
    CongratulationTenantFragmentArgs args;

    public CongratulationTenantFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCongratulationTenantBinding.inflate(inflater,container,false);
        args = CongratulationTenantFragmentArgs.fromBundle(getArguments());

        AddTenantDTO addTenantDTO = args.getAddTenantDTO();


        binding.btnContinue.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(
                    CongratulationTenantFragmentDirections.actionCongratulationTenantFragmentToTenantsFragment(addTenantDTO)
            );
        });
        return binding.getRoot();
    }
}