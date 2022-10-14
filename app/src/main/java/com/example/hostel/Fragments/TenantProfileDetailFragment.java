package com.example.hostel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.Models.Tenant;
import com.example.hostel.R;
import com.example.hostel.databinding.FragmentTenantProfileDetialBinding;

public class TenantProfileDetailFragment extends Fragment {

    FragmentTenantProfileDetialBinding binding;
    TenantProfileDetailFragmentArgs args;
    public TenantProfileDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTenantProfileDetialBinding.inflate(inflater,container,false);
        args = TenantProfileDetailFragmentArgs.fromBundle(getArguments());
        Tenant tenant = args.getTenant();

        binding.tvTenantName.setText(tenant.getN());
        binding.tvTenantDOB.setText(tenant.getD());
        binding.tvTenantNumber.setText(tenant.getP());
        binding.tvTenantEmailId.setText(tenant.getE());

        if (tenant.getG()){
            binding.tvTenantGender.setText("Male");
        }else
            binding.tvTenantGender.setText("Female");

        if (tenant.getM()){
            binding.tvTenantMartialStatus.setText("Single");
        }else
            binding.tvTenantMartialStatus.setText("Married");

        binding.tvTenantMartialStatus.setText(tenant.getN());
       // binding.tvGender.setText(tenant.getN());
        return binding.getRoot();
    }
}