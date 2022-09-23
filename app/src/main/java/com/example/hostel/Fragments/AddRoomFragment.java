package com.example.hostel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.hostel.R;
import com.example.hostel.databinding.FragmentAddRoomBinding;

public class AddRoomFragment extends Fragment {

    FragmentAddRoomBinding binding;

    public AddRoomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddRoomBinding.inflate(inflater, container, false);
        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.btnSave.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_addRoomFragment_to_successFragment);
        });


        return binding.getRoot();
    }
}