package com.example.hostel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.Adapters.PassbookAdapter;
import com.example.hostel.R;
import com.example.hostel.databinding.FragmentPassbookBinding;


public class PassbookFragment extends Fragment {

    FragmentPassbookBinding binding;
    public PassbookFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPassbookBinding.inflate(inflater,container, false);

        PassbookAdapter passbookAdapter = new PassbookAdapter();

        binding.recyclerView.setAdapter(passbookAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        return binding.getRoot();
    }
}