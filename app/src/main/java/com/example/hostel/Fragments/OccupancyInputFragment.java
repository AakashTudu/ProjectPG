package com.example.hostel.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.Adapters.OccupancyInputAdapter;
import com.example.hostel.Models.Occupancy;
import com.example.hostel.R;
import com.example.hostel.UI.OccupancyInputActivity;
import com.example.hostel.UI.TotalOccupancyActivity;
import com.example.hostel.Utils.Constants;
import com.example.hostel.databinding.FragmentOccupancyInputBinding;

import java.util.ArrayList;

public class OccupancyInputFragment extends Fragment {

    FragmentOccupancyInputBinding binding;
    String ref;
    public OccupancyInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOccupancyInputBinding.inflate(inflater,container,false);
        ref = getArguments().getString(Constants.propertyRef);
        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.btnContinue.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.propertyRef, ref);
            Navigation.findNavController(view).navigate(R.id.action_occupencyInputFragment_to_roomArrangementFragment,bundle);
        });

        loadRecyclerView();

        return binding.getRoot();
    }

    private void loadRecyclerView() {
        ArrayList<Occupancy> occupancyList = new ArrayList<Occupancy>(){{
            add(new Occupancy("Single Occupancy"));
            add(new Occupancy("Double Occupancy"));
            add(new Occupancy("Triple Occupancy"));
            add(new Occupancy("Four Occupancy"));
            add(new Occupancy("Five Occupancy"));
            add(new Occupancy("Six Occupancy"));
            add(new Occupancy("Seven Occupancy"));
            add(new Occupancy("Eight Occupancy"));
            add(new Occupancy("Nine Occupancy"));
            add(new Occupancy("Ten Occupancy"));
        }};
        binding.recyclerView.setAdapter(new OccupancyInputAdapter(occupancyList));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }


}