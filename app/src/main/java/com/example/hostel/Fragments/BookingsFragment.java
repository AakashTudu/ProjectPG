package com.example.hostel.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hostel.Adapters.BookingsAdapter;
import com.example.hostel.Adapters.BookingsFirebaseAdapter;
import com.example.hostel.Models.Tenant;
import com.example.hostel.R;
import com.example.hostel.Utils.Constants;
import com.example.hostel.databinding.FragmentBookingsBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingsFragment extends Fragment {

    FragmentBookingsBinding binding;
    BookingsAdapter adapter;
    String ref;
    ArrayList<Tenant> tenantList;

    HashMap<String, Tenant> hashMap;

    public BookingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBookingsBinding.inflate(inflater, container, false);
        if (getArguments() != null)
            ref = getArguments().getString(Constants.propertyRef);
        tenantList = new ArrayList<>();
        hashMap = new HashMap<>();

        if (ref != null)
            loadRoomRecyclerView("b", Constants.layoutTenantsBinding);
        else
            loadFirebaseRecyclerView("b", Constants.layoutTenantsBinding);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        binding.btnBookings.setOnClickListener(view -> {

            if (ref != null)
                loadRoomRecyclerView("b", Constants.layoutTenantsBinding);
            else
                loadFirebaseRecyclerView("b", Constants.layoutTenantsBinding);

            loadRoomRecyclerView("b", Constants.layoutTenantsBinding);
            binding.btnBookings.setBackgroundResource(R.drawable.solid_border_20_dp);
            binding.btnOnBoarded.setBackgroundResource(R.drawable.outlined_border_20_dp);
            binding.btnHistory.setBackgroundResource(R.drawable.outlined_border_20_dp);


            binding.btnBookings.setTextColor(Color.WHITE);
            binding.btnOnBoarded.setTextColor(Color.BLACK);
            binding.btnHistory.setTextColor(Color.BLACK);

        });

        binding.btnOnBoarded.setOnClickListener(view -> {

            if (ref != null)
                loadRoomRecyclerView("o", Constants.layoutOnboardedBinding);
            else
                loadFirebaseRecyclerView("o", Constants.layoutOnboardedBinding);

            binding.btnOnBoarded.setBackgroundResource(R.drawable.solid_border_20_dp);
            binding.btnBookings.setBackgroundResource(R.drawable.outlined_border_20_dp);
            binding.btnHistory.setBackgroundResource(R.drawable.outlined_border_20_dp);


            binding.btnOnBoarded.setTextColor(Color.WHITE);
            binding.btnBookings.setTextColor(Color.BLACK);
            binding.btnHistory.setTextColor(Color.BLACK);

        });

        binding.btnHistory.setOnClickListener(view -> {

            if (ref != null) {
                tenantList.clear();
                adapter.notifyDataSetChanged();
            }

            binding.btnHistory.setBackgroundResource(R.drawable.solid_border_20_dp);
            binding.btnOnBoarded.setBackgroundResource(R.drawable.outlined_border_20_dp);
            binding.btnBookings.setBackgroundResource(R.drawable.outlined_border_20_dp);

            binding.btnHistory.setTextColor(Color.WHITE);
            binding.btnOnBoarded.setTextColor(Color.BLACK);
            binding.btnBookings.setTextColor(Color.BLACK);
        });

        return binding.getRoot();
    }


    private void loadFirebaseRecyclerView(String status, String layoutTenantsBinding) {
        Query query = FirebaseDatabase.getInstance().getReference().child("tenants").orderByChild("s").equalTo(status);
        FirebaseRecyclerOptions<Tenant> options = new FirebaseRecyclerOptions.Builder<Tenant>()
                .setQuery(query, snapshot -> {
                    return snapshot.getValue(Tenant.class);
                }).build();
        BookingsFirebaseAdapter bedAdapter = new BookingsFirebaseAdapter(options, layoutTenantsBinding);
        binding.recyclerView.setAdapter(bedAdapter);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        bedAdapter.startListening();
    }

    private void loadRoomRecyclerView(String status, String layoutTenantsBinding) {

        tenantList.clear();
        hashMap.clear();

        Query query = FirebaseDatabase.getInstance().getReference().child("tenants").orderByChild("rf").equalTo(ref);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Tenant tenant = snapshot.getValue(Tenant.class);
                if (tenant != null) hashMap.put(snapshot.getKey(), tenant);
                loadData();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Tenant tenant = snapshot.getValue(Tenant.class);
                hashMap.put(snapshot.getKey(), tenant);
                loadData();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                hashMap.remove(snapshot.getKey());
                loadData();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new BookingsAdapter(tenantList, layoutTenantsBinding);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

    }

    private void loadData() {
        tenantList.clear();
        for (Map.Entry<String, Tenant> map : hashMap.entrySet()) {
            tenantList.add(map.getValue());
        }
        adapter.notifyDataSetChanged();
    }
}