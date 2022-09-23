package com.example.hostel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.R;
import com.example.hostel.databinding.FragmentCongratulationTenantBinding;

public class CongratulationTenantFragment extends Fragment {

    FragmentCongratulationTenantBinding binding;

    public CongratulationTenantFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCongratulationTenantBinding.inflate(inflater,container,false);

        binding.btnContinue.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_congratulationTenantFragment_to_tenantsFragment);
        });
        return binding.getRoot();
    }
}