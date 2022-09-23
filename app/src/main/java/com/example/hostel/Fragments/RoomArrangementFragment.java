package com.example.hostel.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Adapters.FloorHorizontalAdapter;
import com.example.hostel.Adapters.RoomsArrangementAdapter;
import com.example.hostel.Listeners.OnBtnClickListener;
import com.example.hostel.Listeners.OnFloorItemClickListener;
import com.example.hostel.Models.Floor;
import com.example.hostel.Models.Room;
import com.example.hostel.R;
import com.example.hostel.Utils.BottomSheet;
import com.example.hostel.Utils.Constants;
import com.example.hostel.databinding.FragmentRoomArrangementBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

public class RoomArrangementFragment extends Fragment {

    FragmentRoomArrangementBinding binding;
    RoomsArrangementAdapter roomAdapter;
    FloorHorizontalAdapter floorAdapter;
    String roomRef;
    String timeStampRef;


    public RoomArrangementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRoomArrangementBinding.inflate(inflater, container, false);

        binding.btnBack.setOnClickListener(view -> {
            if (getArguments().getString(Constants.fragment).equals(Constants.managementFragment)) {
                Navigation.findNavController(view).popBackStack();
            } else if (getArguments().getString(Constants.fragment).equals(Constants.userPropertyFragment))
                Navigation.findNavController(view).popBackStack();
            else
                Navigation.findNavController(view).navigate(RoomArrangementFragmentDirections.actionRoomArrangementFragmentToPropertyFragment());
        });

        binding.btnAdd.setOnClickListener(view -> {

            Navigation.findNavController(view).navigate(R.id.action_roomArrangementFragment_to_addRoomFragment);
        });
        timeStampRef = getArguments().getString(Constants.propertyRef);
        loadFloorRecyclerview();

        binding.btnAdd.setOnClickListener(view -> {
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

        binding.ivMore.setOnClickListener(view -> {
            BottomSheet.showEditOptionDialog(getContext(), () -> {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.propertyRef, timeStampRef);
                bundle.putString("fragment", "RoomArrangementFragment");
                Navigation.findNavController(view).navigate(R.id.action_roomArrangementFragment_to_totalFloorFragment, bundle);
            });

        });

        return binding.getRoot();
    }

    private void loadFloorRecyclerview() {
        Query query = FirebaseDatabase.getInstance().getReference().child("floor").child(timeStampRef);
        FirebaseRecyclerOptions<Floor> options = new FirebaseRecyclerOptions.Builder<Floor>()
                .setQuery(query, snapshot -> {
                    String name = snapshot.child("n").getValue().toString();
                    String ch = String.valueOf(name.charAt(0));
                    name = ch.toUpperCase() + name.substring(1) + " Floor";
                    return new Floor(
                            name
                    );
                }).build();
        floorAdapter = new FloorHorizontalAdapter(options, new OnFloorItemClickListener() {
            @Override
            public void clicked(String floorName, String roomRef) {
                loadRoomRecyclerView(floorName, roomRef);
            }

            @Override
            public void firstItem(String floorName, String roomRef) {
                loadRoomRecyclerView(floorName, roomRef);
            }
        });
        binding.rvFloor.setAdapter(floorAdapter);

        binding.rvFloor.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        floorAdapter.startListening();
    }

    private void loadRoomRecyclerView(String floorName, String roomRef) {
        this.roomRef = roomRef;
        binding.tvFloorName.setText(floorName);
        Query query = FirebaseDatabase.getInstance().getReference().child("room").child(roomRef);
        FirebaseRecyclerOptions<Room> options = new FirebaseRecyclerOptions.Builder<Room>()
                .setQuery(query, dataSnapshot -> {
                    Log.d("sfsdfsdf", "snapshot: " + dataSnapshot);
                    String roomNumber = dataSnapshot.child("n").getValue().toString();
                    return new Room(
                            roomNumber
                    );
                }).build();
        roomAdapter = new RoomsArrangementAdapter(options);

        binding.rvRoom.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvRoom.setAdapter(roomAdapter);

        roomAdapter.startListening();

    }

    /*
    private void loadRecyclerView() {
        Log.d("sfsdfsdf", "timeStampRef: " + timeStampRef);
        Query hasRoomQuery = FirebaseDatabase.getInstance().getReference().child("floor").child(timeStampRef).limitToFirst(1);

        hasRoomQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    Log.d("sfsdfsdf", "snapshot: " + snapshot);
                    Log.d("sfsdfsdf", "roomRef: " + snapshot.getValue());
                    HashMap<String,Object> hashMap = null;

                    hashMap = new Gson().fromJson(snapshot.getValue().toString(), HashMap.class);
                    Map.Entry<String,Object> entry = hashMap.entrySet().iterator().next();
                    String key = entry.getKey();


                    Log.d("sfsdfsdf", "key: " + key);


                    Query query = FirebaseDatabase.getInstance().getReference().child("room").child(key);
                    FirebaseRecyclerOptions<Room> options = new FirebaseRecyclerOptions.Builder<Room>()
                            .setQuery(query, dataSnapshot -> {
                                Log.d("sfsdfsdf", "snapshot: " + dataSnapshot);
                                String roomNumber = dataSnapshot.child("n").getValue().toString();
                                return new Room(
                                        roomNumber
                                );
                            }).build();
                    adapter = new RoomsArrangementAdapter(options);

                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.recyclerView.setAdapter(adapter);

                    adapter.startListening();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }*/

}