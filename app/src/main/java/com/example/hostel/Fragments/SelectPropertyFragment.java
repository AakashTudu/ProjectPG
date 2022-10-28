package com.example.hostel.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hostel.Adapters.SelectPropertyAdapter;
import com.example.hostel.DTO.AddTenantDTO;
import com.example.hostel.Models.Property;
import com.example.hostel.Utils.Constants;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.FragmentSelectPropertyBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SelectPropertyFragment extends Fragment {

    FragmentSelectPropertyBinding binding;
    SelectPropertyFragmentArgs args;
    AddTenantDTO addTenantDTO;
    public SelectPropertyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectPropertyBinding.inflate(inflater, container, false);
        args = SelectPropertyFragmentArgs.fromBundle(getArguments());

        addTenantDTO = args.getAddTenantDTO();

        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });
        loadRecyclerView();
        return binding.getRoot();
    }

    private void loadRecyclerView() {
        Query query = FirebaseDatabase.getInstance().getReference().child("properties").child(UserUtils.phoneNumber());
        FirebaseRecyclerOptions<Property> options = new FirebaseRecyclerOptions.Builder<Property>()
                .setQuery(query, snapshot -> {
                    return snapshot.getValue(Property.class);
                }).build();
        SelectPropertyAdapter adapter = new SelectPropertyAdapter(options, addTenantDTO);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.startListening();
    }
}