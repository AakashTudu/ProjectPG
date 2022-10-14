package com.example.hostel.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.BottomSheetDialog.PropertyDialog;
import com.example.hostel.Models.Tenant;
import com.example.hostel.R;
import com.example.hostel.Utils.Constants;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.FragmentTenantProfileBinding;

public class TenantProfileFragment extends Fragment {

    FragmentTenantProfileBinding binding;
    TenantProfileFragmentArgs args;
    Tenant tenant;

    public TenantProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTenantProfileBinding.inflate(inflater,container,false);
        args = TenantProfileFragmentArgs.fromBundle(getArguments());
        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        tenant = args.getTenant();
        binding.tvCode.setText(args.getCode());
        binding.tvName.setText(tenant.getN());
        binding.tvRoomQuantity.setText(tenant.getR());

        binding.tvOccupancy.setText(tenant.getOccupancy());

        binding.ivProfile.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(
                    TenantProfileFragmentDirections.actionTenantProfileFragmentToTenantProfileDetialFragment(tenant)
            );
        });

        binding.ivProperty.setOnClickListener(view -> {
            new PropertyDialog(view.getContext(),tenant);
        });

        binding.ivDocument.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(
                    TenantProfileFragmentDirections.actionTenantProfileFragmentToDocumentFragment()
            );
        });

        binding.ivPassbook.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(
                    TenantProfileFragmentDirections.actionTenantProfileFragmentToPassbookFragment()
            );
        });

        binding.tvCode.setOnClickListener(view -> {
            UserUtils.call(tenant.getP(),view.getContext());
        });

        binding.ivCall.setOnClickListener(view -> {
            UserUtils.call(tenant.getP(),view.getContext());
        });

        binding.tvNumber.setText(tenant.getP());
        binding.tvEmail.setText(tenant.getE());
        return binding.getRoot();
    }

}