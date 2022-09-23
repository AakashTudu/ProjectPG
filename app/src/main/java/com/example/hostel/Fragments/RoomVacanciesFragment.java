package com.example.hostel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hostel.Adapters.RoomVacanciesAdapter;
import com.example.hostel.Models.Property;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.FragmentRoomVacanciesBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class RoomVacanciesFragment extends Fragment {

    RoomVacanciesAdapter adapter;
    FragmentRoomVacanciesBinding binding;

    public RoomVacanciesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRoomVacanciesBinding.inflate(inflater, container, false);

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
                    String name = snapshot.child("name").getValue().toString();
                    String type = snapshot.child("type").getValue().toString();
                    String city = snapshot.child("city").getValue().toString();
                    String location = snapshot.child("location").getValue().toString();
                    String isLive = snapshot.child("isLive").getValue().toString();
                    return new Property(
                            name, type, city, location, isLive
                    );
                }).build();
        adapter = new RoomVacanciesAdapter(options);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.startListening();
    }

}