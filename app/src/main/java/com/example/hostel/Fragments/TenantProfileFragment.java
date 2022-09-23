package com.example.hostel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.Models.Tenant;
import com.example.hostel.R;
import com.example.hostel.Utils.Constants;
import com.example.hostel.databinding.FragmentTenantProfileBinding;

public class TenantProfileFragment extends Fragment {

    FragmentTenantProfileBinding binding;

    public TenantProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTenantProfileBinding.inflate(inflater,container,false);
        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        Tenant tenant = (Tenant) getArguments().getSerializable(Constants.tenant);

        binding.tvName.setText(tenant.getN());
        binding.tvRoomQuantity.setText(tenant.getR());
        binding.tvOccupancy.setText(tenant.getO());
        binding.tvNumber.setText(tenant.getP());
        binding.tvEmail.setText(tenant.getE().replace("@gmail.com","") + "\n" + "@gmail.com");
        return binding.getRoot();
    }
}