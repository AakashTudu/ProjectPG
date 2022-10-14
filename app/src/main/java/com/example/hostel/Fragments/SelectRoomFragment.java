package com.example.hostel.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hostel.Adapters.RoomsArrangementAdapter;
import com.example.hostel.Adapters.SelectRoomAdapter;
import com.example.hostel.BottomSheetDialog.FloorDialog;
import com.example.hostel.DTO.AddTenantDTO;
import com.example.hostel.Models.BedCard;
import com.example.hostel.Models.Floor;
import com.example.hostel.Models.Room;
import com.example.hostel.databinding.FragmentSelectRoomBinding;
import com.example.monthandyearpicker.Utils;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SelectRoomFragment extends Fragment {

    FragmentSelectRoomBinding binding;
    SelectRoomFragmentArgs args;
    SelectRoomAdapter roomAdapter;
    AddTenantDTO addTenantDTO;

    public SelectRoomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectRoomBinding.inflate(inflater, container, false);
        args = SelectRoomFragmentArgs.fromBundle(getArguments());
        addTenantDTO = args.getAddTenantDTO();
        binding.tvSelectFloor.setOnClickListener(view -> {
            showFloorDialog();
        });

        initRoomRecyclerView();
        fetchBedCountingCards(addTenantDTO.getPropertyRefKey());
        return binding.getRoot();
    }

    private void fetchBedCountingCards(String propertyRefKey) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query query = database.getReference("bedCard").child(propertyRefKey);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    BedCard bedCard = snapshot.getValue(BedCard.class);
                    if (bedCard != null) {
                        setBedCards(bedCard);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setBedCards(BedCard bedCard) {
        binding.tvTotalBed.setText(String.valueOf(bedCard.getTotalBed()));
        binding.tvVacantBed.setText(String.valueOf(bedCard.getVacantBed()));
        binding.tvOnboardBed.setText((String.valueOf(bedCard.getOnboardBed())));
    }

    private void initRoomRecyclerView() {
        Query query = FirebaseDatabase.getInstance().getReference().child("floor").child(addTenantDTO.getPropertyRefKey()).limitToFirst(1);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()) {
                    Floor floor = snapshot.getValue(Floor.class);
                    String ref = snapshot.getKey();
                    if (floor != null)
                        loadRoomRecyclerView(floor, ref);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadRoomRecyclerView(Floor floor, String ref) {
        binding.tvSelectFloor.setText(Utils.numberToOrdinalWord(floor.getN()));
        Query query = FirebaseDatabase.getInstance().getReference().child("room").child(ref);
        FirebaseRecyclerOptions<Room> options = new FirebaseRecyclerOptions.Builder<Room>()
                .setQuery(query, dataSnapshot -> {
                    Log.d("sfsdfsdf", "snapshot: " + dataSnapshot);
                    String roomNumber = dataSnapshot.child("n").getValue().toString();
                    return new Room(
                            roomNumber
                    );
                }).build();
        roomAdapter = new SelectRoomAdapter(options, new SelectRoomAdapter.OnEmptyCheckListener() {
            @Override
            public void isEmpty() {
                Snackbar.make(getView(), "No room found on " + Utils.numberToOrdinalWord(floor.getN()),Snackbar.LENGTH_SHORT).show();
            }
        }, addTenantDTO);

        binding.rvRoom.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvRoom.setAdapter(roomAdapter);

        roomAdapter.startListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (roomAdapter != null)
            roomAdapter.stopListening();
    }

    private void showFloorDialog() {

        FloorDialog floorDialog = new FloorDialog(getContext(), addTenantDTO.getPropertyRefKey(), new FloorDialog.OnFloorClickListener() {
            @Override
            public void floorClicked(Floor floor, String ref) {
                loadRoomRecyclerView(floor, ref);
            }
        });
    }
}