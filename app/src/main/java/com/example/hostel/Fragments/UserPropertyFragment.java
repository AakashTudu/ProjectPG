package com.example.hostel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.DTO.AddTenantDTO;
import com.example.hostel.Models.Property;
import com.example.hostel.R;
import com.example.hostel.Utils.Constants;
import com.example.hostel.databinding.FragmentUserPropertyBinding;

public class UserPropertyFragment extends Fragment {

    FragmentUserPropertyBinding binding;
    UserPropertyFragmentArgs args;
    //String ref;

    public UserPropertyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUserPropertyBinding.inflate(inflater,container,false);
        args = UserPropertyFragmentArgs.fromBundle(getArguments());

        AddTenantDTO addTenantDTO = args.getAddTenantDTO();

        Property property = addTenantDTO.getProperty();
        binding.tvPropertyDetail.setText(property.getName() + " PG for " + property.getType().replace("PG","").trim());

        binding.cvTenant.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(
                UserPropertyFragmentDirections.actionUserPropertyFragmentToTenantsFragment(addTenantDTO)
            );
        });

/*        ref = getArguments().getString(Constants.propertyRef);

        Property property = (Property) getArguments().getSerializable(Constants.property);

        String position = getArguments().getString("position");

        if (position!=null){
            binding.tvProperty.setText("Property " + position);
        }

        binding.tvPropertyDetail.setText(property.getName() + " PG for " + property.getType().replace("PG","").trim());

        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.cvTenant.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.propertyRef, ref);
            bundle.putSerializable(Constants.property, property);
            Navigation.findNavController(view).navigate(R.id.action_userPropertyFragment_to_tenantsFragment,bundle);
        });

        binding.cvBooking.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.propertyRef, ref);
            bundle.putSerializable(Constants.property, property);
            Navigation.findNavController(view).navigate(R.id.action_userPropertyFragment_to_bookingsFragment,bundle);
        });

        binding.cvComplaints.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.propertyRef, ref);
            bundle.putSerializable(Constants.property, property);
            Navigation.findNavController(view).navigate(R.id.action_userPropertyFragment_to_complaintsFragment, bundle);

        });

        binding.cvRoom.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.propertyRef, ref);
            bundle.putString(Constants.fragment, Constants.userPropertyFragment);
            Navigation.findNavController(view).navigate(R.id.action_userPropertyFragment_to_roomArrangementFragment, bundle);
        });*/

        return binding.getRoot();
    }
}