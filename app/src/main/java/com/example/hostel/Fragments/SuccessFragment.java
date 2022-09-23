package com.example.hostel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.R;
import com.example.hostel.databinding.FragmentSuccessBinding;


public class SuccessFragment extends Fragment {

    FragmentSuccessBinding binding;

    public SuccessFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSuccessBinding.inflate(inflater,container,false);

        binding.btnContinue.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack(R.id.action_successFragment_to_propertyFragment,true);
        });
        return binding.getRoot();
    }
}