package com.example.hostel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hostel.Adapters.SelectPropertyAdapter;
import com.example.hostel.Models.Property;
import com.example.hostel.R;
import com.example.hostel.Utils.Constants;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.FragmentSelectPropertyBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SelectPropertyFragment extends Fragment {

    FragmentSelectPropertyBinding binding;
    Property property;

    Bundle bundle;

    public SelectPropertyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectPropertyBinding.inflate(inflater, container, false);
        bundle = new Bundle();

        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.btnContinue.setOnClickListener(view -> {
            if (property != null)
                Navigation.findNavController(view).navigate(R.id.action_selectPropertyFragment_to_selectSharingFragment, bundle);
            else
                Snackbar.make(binding.getRoot(), "Please select property..", Snackbar.LENGTH_SHORT).show();
        });

        bundle.putString(Constants.tenantName, getArguments().getString(Constants.tenantName));
        bundle.putString(Constants.mobileNumber, getArguments().getString(Constants.mobileNumber));
        bundle.putString(Constants.emailId, getArguments().getString(Constants.emailId));
        bundle.putString(Constants.date, getArguments().getString(Constants.date));
        bundle.putString(Constants.gender, getArguments().getString(Constants.gender));
        bundle.putString(Constants.martialStatus, getArguments().getString(Constants.martialStatus));
        bundle.putString(Constants.occupation, getArguments().getString(Constants.occupation));


        loadRecyclerView();
        return binding.getRoot();
    }

    private void loadRecyclerView() {
        Query query = FirebaseDatabase.getInstance().getReference().child("properties").child(UserUtils.phoneNumber());
        FirebaseRecyclerOptions<Property> options = new FirebaseRecyclerOptions.Builder<Property>()
                .setQuery(query, snapshot -> {
                    String name = snapshot.child("name").getValue().toString();
                    String type = snapshot.child("type").getValue().toString();
                    String city = snapshot.child("city").getValue().toString();
                    String location = snapshot.child("location").getValue().toString();
                    String isLive = snapshot.child("isLive").getValue().toString();
                    return new Property(
                            name, type, city, location, isLive
                    );
                }).build();
        SelectPropertyAdapter adapter = new SelectPropertyAdapter(options, (property, key) -> {
            this.property = property;
            bundle.putString(Constants.propertyRef, key);
            String message = property.getName() + " PG for " + property.getType().replace("PG", "").trim() + " selected. Press continue..";
            bundle.putSerializable(Constants.property, property);
            Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
        });

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.startListening();
    }
}