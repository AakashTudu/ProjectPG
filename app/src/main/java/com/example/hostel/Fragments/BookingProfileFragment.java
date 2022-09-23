package com.example.hostel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.Adapters.BookingProfileAdapter;
import com.example.hostel.Models.Tenant;
import com.example.hostel.Models.TenantDetail;
import com.example.hostel.R;
import com.example.hostel.Utils.Constants;
import com.example.hostel.databinding.FragmentBookingProfileBinding;
import com.example.hostel.databinding.FragmentBookingsBinding;

import java.util.ArrayList;

public class BookingProfileFragment extends Fragment {

    ArrayList<TenantDetail> tenantDetails;
    BookingProfileAdapter adapter;

   public BookingProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentBookingProfileBinding binding = FragmentBookingProfileBinding.inflate(inflater,container,false);
        tenantDetails = new ArrayList<TenantDetail>(){{
           add(new TenantDetail(R.drawable.ic_non_smoker,"Non-Smoker"));
           add(new TenantDetail(R.drawable.ic_non_drinker,"Non-Drinker"));
           add(new TenantDetail(R.drawable.ic_early_riser,"Early riser"));
           add(new TenantDetail(R.drawable.ic_student_booking,"Student"));
        }};

        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        Tenant tenant = (Tenant) getArguments().getSerializable(Constants.tenant);

        binding.tvName.setText(tenant.getN());
        binding.tvPgName.setText(tenant.getPn());
        binding.tvRoomQuantity.setText(tenant.getR());
        binding.tvOccupancy.setText(tenant.getO());

        adapter = new BookingProfileAdapter(tenantDetails);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }
}