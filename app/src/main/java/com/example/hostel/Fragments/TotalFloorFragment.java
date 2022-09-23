package com.example.hostel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hostel.Adapters.TotalFloorsAdapter;
import com.example.hostel.Models.Floor;
import com.example.hostel.R;
import com.example.hostel.Utils.BottomSheet;
import com.example.hostel.Utils.Constants;
import com.example.hostel.databinding.FragmentTotalFloorBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

public class TotalFloorFragment extends Fragment {

    FragmentTotalFloorBinding binding;
    TotalFloorsAdapter adapter;
    String propertyRef;
    String fragment;

    public TotalFloorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTotalFloorBinding.inflate(inflater, container, false);
        propertyRef = getArguments().getString(Constants.propertyRef);
        fragment = getArguments().getString("fragment");

        binding.btnBack.setOnClickListener(view -> {
            switch (fragment) {
                case "RoomArrangementFragment":
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.propertyRef, propertyRef);
                    Navigation.findNavController(view).navigate(R.id.action_totalFloorFragment_to_roomArrangementFragment, bundle);
                    break;
                case "AddPropertyFragment":
                    Navigation.findNavController(view).navigate(R.id.action_totalFloorFragment_to_propertyFragment);
                    break;
            }
        });

        binding.btnContinue.setOnClickListener(view -> {
            if (adapter.getItemCount() == 0) {
                Toast.makeText(getContext(), "Please Add floor...", Toast.LENGTH_SHORT).show();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.propertyRef, propertyRef);
                Navigation.findNavController(view).navigate(R.id.action_totalFloorFragment_to_totalRoomsFragment, bundle);
            }
        });


        binding.btnAdd.setOnClickListener(view -> {
            BottomSheet.addFloorBottomDialog(getContext(), floorName -> {
                Map<String, Object> map = new HashMap<>();
                map.put("n", floorName);
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference userRef = rootRef.child("floor").child(propertyRef).child(String.valueOf(System.currentTimeMillis()));
                userRef.setValue(map);
            });

        });

        loadRecyclerView();
        return binding.getRoot();
    }

    private void loadRecyclerView() {
        propertyRef = getArguments().getString("timeStampRef");
        Query query = FirebaseDatabase.getInstance().getReference().child("floor").child(propertyRef);
        FirebaseRecyclerOptions<Floor> options = new FirebaseRecyclerOptions.Builder<Floor>()
                .setQuery(query, snapshot -> {
                    String name = snapshot.child("n").getValue().toString();
                    String ch = String.valueOf(name.charAt(0));
                    name = ch.toUpperCase() + name.substring(1) + " Floor";
                    return new Floor(name);
                }).build();
        adapter = new TotalFloorsAdapter(options);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        adapter.startListening();
    }
}