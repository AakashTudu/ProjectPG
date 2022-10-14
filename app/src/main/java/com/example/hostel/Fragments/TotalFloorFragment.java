package com.example.hostel.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hostel.Adapters.TotalFloorsAdapter;
import com.example.hostel.Models.Floor;
import com.example.hostel.R;
import com.example.hostel.Utils.Constants;
import com.example.hostel.databinding.FragmentTotalFloorBinding;
import com.example.monthandyearpicker.floor.FloorPickerDialog;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

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
        Log.d("sdfsdfsd", "TotalFloorFragment: " + propertyRef);

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
                case "PropertyFragment":
                    Navigation.findNavController(view).popBackStack();
                default:
                    Navigation.findNavController(view).popBackStack();
            }
        });

        binding.btnContinue.setOnClickListener(view -> {
            if (adapter.getItemCount() == 0) {
                Snackbar.make(view, "Please Add floor...", Snackbar.LENGTH_SHORT).show();
            } else {
                if (fragment.equals("PropertyFragment") || fragment.equals("RoomArrangementFragment")){
                    Navigation.findNavController(view).popBackStack();
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.propertyRef, propertyRef);
                    Navigation.findNavController(view).navigate(R.id.action_totalFloorFragment_to_totalRoomsFragment, bundle);
                }
            }
            removeUnselectedFloor();
        });

        loadRecyclerView();
        if (fragment.equals("AddPropertyFragment"))
            pushFirstThreeFloor();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        removeUnselectedFloor();
    }

    private void removeUnselectedFloor() {

        for (int i = 0; i < adapter.getItemCount(); i++) {
            Floor floor = adapter.getItem(i);
            if (!floor.getSelected())
                adapter.getRef(i).removeValue();
        }
    }

    private void pushFirstThreeFloor() {
        for (int i = -1; i <= 1; i++) {
            Floor floor = new Floor(i);
            floor.setSelected(false);
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userRef = rootRef.child("floor").child(propertyRef);
            userRef.push().setValue(floor);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnAddFloor.setOnClickListener(unused -> {
            showFloorPickerDialog(view);
        });
    }

    private void showFloorPickerDialog(View view) {
        FloorPickerDialog.Builder builder = new FloorPickerDialog.Builder(view.getContext(), com.example.monthandyearpicker.R.style.Style_MonthYearPickerDialog_Black,
                floorNumber -> {
                    Floor floor = new Floor(floorNumber);
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference userRef = rootRef.child("floor").child(propertyRef);
                    userRef.push().setValue(floor);
                }, 2);
        builder.setMinFloor(-1);
        builder.setMaxFloor(100);
        builder.build().show();
    }

    private void loadRecyclerView() {
        Query query = FirebaseDatabase.getInstance().getReference().child("floor").child(propertyRef);
        FirebaseRecyclerOptions<Floor> options = new FirebaseRecyclerOptions.Builder<Floor>()
                .setQuery(query, snapshot -> {
                    return snapshot.getValue(Floor.class);
                }).build();
        adapter = new TotalFloorsAdapter(options);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        adapter.startListening();
    }
}