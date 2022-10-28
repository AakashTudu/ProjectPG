package com.example.hostel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.R;
import com.example.hostel.databinding.FragmentComplaintStatusBinding;


public class ComplaintStatusFragment extends Fragment {

    FragmentComplaintStatusBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentComplaintStatusBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}