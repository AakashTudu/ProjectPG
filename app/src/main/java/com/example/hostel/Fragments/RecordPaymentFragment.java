package com.example.hostel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.R;
import com.example.hostel.databinding.FragmentRecordPaymentBinding;

public class RecordPaymentFragment extends Fragment {

    FragmentRecordPaymentBinding binding;

    public RecordPaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRecordPaymentBinding.inflate(inflater, container, false);
        binding.btnBack.setOnClickListener(view -> {
            //Navigation.findNavController(view).popBackStack(R.id.action_recordPaymentFragment_to_dashboardFragment);
            Navigation.findNavController(view).popBackStack();
        });
        return binding.getRoot();
    }
}