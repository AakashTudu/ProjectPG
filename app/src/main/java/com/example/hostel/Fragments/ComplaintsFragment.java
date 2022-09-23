package com.example.hostel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.Adapters.ComplaintsAdapter;
import com.example.hostel.Adapters.TenantsAdapter;
import com.example.hostel.Models.Complaint;
import com.example.hostel.Models.Property;
import com.example.hostel.Models.Tenant;
import com.example.hostel.R;
import com.example.hostel.Utils.Constants;
import com.example.hostel.databinding.FragmentComplaintsBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ComplaintsFragment extends Fragment {

    FragmentComplaintsBinding binding;
    ComplaintsAdapter adapter;
    String ref;

    public ComplaintsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentComplaintsBinding.inflate(inflater,container,false);

        if (getArguments() != null) {
            ref = getArguments().getString(Constants.propertyRef);
            binding.btnAdd.setOnClickListener(view -> {
                addComplaints();
            });
        }

        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        loadRoomRecyclerView();
        return binding.getRoot();
    }

    private void addComplaints() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("complaints");
        Property property = (Property) getArguments().getSerializable(Constants.property);
        String propertyName = property.getName() + " PG for " + property.getType().replace("PG","").trim();
        Complaint complaint = new Complaint("Varun kumar","101","9846274571",propertyName,ref,"Cleaning not done");
        dbRef.push().setValue(complaint);
    }

    private void loadRoomRecyclerView() {
        Query query;

        if (ref == null) {
            query = FirebaseDatabase.getInstance().getReference().child("complaints");
        } else {
            query = FirebaseDatabase.getInstance().getReference().child("complaints").orderByChild("rf").equalTo(ref);
        }

        FirebaseRecyclerOptions<Complaint> options = new FirebaseRecyclerOptions.Builder<Complaint>()
                .setQuery(query, snapshot -> {
                    return snapshot.getValue(Complaint.class);
                }).build();
        adapter = new ComplaintsAdapter(options);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        adapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.stopListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }
}