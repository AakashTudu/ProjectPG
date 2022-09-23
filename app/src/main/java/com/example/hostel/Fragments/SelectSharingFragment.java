package com.example.hostel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.hostel.Listeners.OnBtnClickListener;
import com.example.hostel.Models.Property;
import com.example.hostel.Models.Tenant;
import com.example.hostel.R;
import com.example.hostel.Utils.BottomSheet;
import com.example.hostel.Utils.Constants;
import com.example.hostel.Utils.Validation;
import com.example.hostel.databinding.FragmentSelectSharingBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SelectSharingFragment extends Fragment {

    FragmentSelectSharingBinding binding;
    Bundle bundle;

    public SelectSharingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectSharingBinding.inflate(inflater, container, false);
        bundle = new Bundle();

        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.tvSharingType.setOnClickListener(view -> {
            BottomSheet.showOccupancyOptionDialog(getContext(), new OnBtnClickListener() {
                @Override
                public void btnClicked(String data) {
                    binding.tvSharingType.setText(data);
                }
            });
        });

        binding.btnDone.setOnClickListener(view -> {

            String propertyRef = getArguments().getString(Constants.propertyRef);

            String tenantName = getArguments().getString(Constants.tenantName);
            String mobileNumber = getArguments().getString(Constants.mobileNumber);
            String emailId = getArguments().getString(Constants.emailId);
            String date = getArguments().getString(Constants.date);
            String gender = getArguments().getString(Constants.gender);
            String martialStatus = getArguments().getString(Constants.martialStatus);
            String occupation = getArguments().getString(Constants.occupation);
            Property property = (Property) getArguments().getSerializable(Constants.property);

            if (Validation.selectSharingPage(binding)) {
                binding.linearProgressIndicator.setVisibility(View.VISIBLE);
                String occupancy = binding.tvSharingType.getText().toString();
                String roomNumber = binding.etRoomNumber.getText().toString();
                String bedNumber = binding.etBedNumber.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("tenants");
                String propertyName = property.getName() + " PG for " + property.getType().replace("PG","").trim();
                Tenant tenant = new Tenant(tenantName, roomNumber, mobileNumber, occupancy, emailId,propertyName,  propertyRef);
                myRef.push().setValue(tenant).addOnSuccessListener(unused -> {
                    binding.linearProgressIndicator.setVisibility(View.GONE);
                    Navigation.findNavController(view).navigate(R.id.action_selectSharingFragment_to_congratulationTenantFragment, bundle);
                });

            }
        });

        return binding.getRoot();
    }
}