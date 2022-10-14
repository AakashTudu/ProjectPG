package com.example.hostel.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Adapters.FloorHorizontalAdapter;
import com.example.hostel.Adapters.RoomsArrangementAdapter;
import com.example.hostel.Listeners.OnBtnClickListener;
import com.example.hostel.Listeners.OnFloorItemClickListener;
import com.example.hostel.Models.BedCard;
import com.example.hostel.Models.Floor;
import com.example.hostel.Models.Room;
import com.example.hostel.R;
import com.example.hostel.Utils.BottomSheet;
import com.example.hostel.Utils.Constants;
import com.example.hostel.databinding.FragmentRoomArrangementBinding;
import com.example.monthandyearpicker.Utils;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RoomArrangementFragment extends Fragment {

    FragmentRoomArrangementBinding binding;
    RoomsArrangementAdapter roomAdapter;
    FloorHorizontalAdapter floorAdapter;
    String roomRef;
    String propertyRefKey;


    public RoomArrangementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRoomArrangementBinding.inflate(inflater, container, false);

        binding.btnBack.setOnClickListener(view -> {

            if (getArguments() != null) {
                String fragment = getArguments().getString(Constants.fragment);
                if (fragment != null && fragment.equals(Constants.managementFragment)) {
                    Navigation.findNavController(view).popBackStack();
                } else if (fragment != null && fragment.equals(Constants.userPropertyFragment))
                    Navigation.findNavController(view).popBackStack();
                else
                    Navigation.findNavController(view).navigate(RoomArrangementFragmentDirections.actionRoomArrangementFragmentToPropertyFragment());
            }
        });

        propertyRefKey = getArguments().getString(Constants.propertyRef);
        loadFloorRecyclerview();

        binding.btnAddRoom.setOnClickListener(view -> {
            if (roomRef == null) {
                Toast.makeText(getContext(), "Please Add Floor", Toast.LENGTH_SHORT).show();
            } else {
                BottomSheet.addRoomBottomDialog(getContext(), new OnBtnClickListener() {
                    @Override
                    public void btnClicked(String roomNumber) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("n", roomNumber);
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference userRef = rootRef.child("room").child(roomRef).child(String.valueOf(System.currentTimeMillis()));
                        userRef.setValue(map);
                    }
                });
            }
        });


        binding.btnSave.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_roomArrangementFragment_to_propertyFragment);
        });

        binding.ivMore.setOnClickListener(view -> {
            BottomSheet.showEditOptionDialog(getContext(), () -> {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.propertyRef, propertyRefKey);
                bundle.putString("fragment", "RoomArrangementFragment");
                Navigation.findNavController(view).navigate(R.id.action_roomArrangementFragment_to_totalFloorFragment, bundle);
            });

        });

        fetchBedCountingCards(propertyRefKey);

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

    private void loadFloorRecyclerview() {
        Query query = FirebaseDatabase.getInstance().getReference().child("floor").child(propertyRefKey);
        FirebaseRecyclerOptions<Floor> options = new FirebaseRecyclerOptions.Builder<Floor>()
                .setQuery(query, snapshot -> {
                    return snapshot.getValue(Floor.class);
                }).build();
        floorAdapter = new FloorHorizontalAdapter(options, new OnFloorItemClickListener() {
            @Override
            public void clicked(int floorNumber, String roomRef) {
                loadRoomRecyclerView(floorNumber, roomRef);
            }

            @Override
            public void firstItem(int floorNumber, String roomRef) {
                loadRoomRecyclerView(floorNumber, roomRef);
            }
        });
        binding.rvFloor.setAdapter(floorAdapter);

        binding.rvFloor.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        floorAdapter.startListening();
    }

    private void loadRoomRecyclerView(int floorNumber, String roomRef) {
        this.roomRef = roomRef;
        binding.tvFloorName.setText(Utils.numberToOrdinalWord(floorNumber));
        Query query = FirebaseDatabase.getInstance().getReference().child("room").child(roomRef);
        FirebaseRecyclerOptions<Room> options = new FirebaseRecyclerOptions.Builder<Room>()
                .setQuery(query, dataSnapshot -> {
                    String roomNumber = dataSnapshot.child("n").getValue().toString();
                    return new Room(
                            roomNumber
                    );
                }).build();
        roomAdapter = new RoomsArrangementAdapter(options, propertyRefKey);

        binding.rvRoom.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvRoom.setAdapter(roomAdapter);

        roomAdapter.startListening();

    }

}