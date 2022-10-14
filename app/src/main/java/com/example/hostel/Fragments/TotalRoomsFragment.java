package com.example.hostel.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hostel.Adapters.TotalRoomsAdapter;
import com.example.hostel.Listeners.OnGroupBtnClickListener;
import com.example.hostel.Models.Floor;
import com.example.hostel.Models.Room;
import com.example.hostel.R;
import com.example.hostel.Utils.Constants;
import com.example.hostel.databinding.FragmentTotalRoomsBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class TotalRoomsFragment extends Fragment {

    FragmentTotalRoomsBinding binding;
    String timeStampRef;
    TotalRoomsAdapter adapter;
    Bundle bundle;

    int totalRoomSum = 0;

    public TotalRoomsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTotalRoomsBinding.inflate(inflater, container, false);
        timeStampRef = getArguments().getString(Constants.propertyRef);
        bundle = new Bundle();
        bundle.putString(Constants.propertyRef,timeStampRef);

        binding.btnContinue.setOnClickListener(view -> {
            uploadRooms();
        });

        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });


        loadRecyclerView();
        return binding.getRoot();
    }

    private void loadRecyclerView() {
        Query query = FirebaseDatabase.getInstance().getReference().child("floor").child(timeStampRef);
        FirebaseRecyclerOptions<Floor> options = new FirebaseRecyclerOptions.Builder<Floor>()
                .setQuery(query, snapshot -> {
                    return snapshot.getValue(Floor.class);
                }).build();
        adapter = new TotalRoomsAdapter(options, new OnGroupBtnClickListener() {
            @Override
            public void addBtnClicked() {
                totalRoomSum++;
                binding.tvTotalRoomQuantity.setText("" + totalRoomSum);
            }

            @Override
            public void minusBtnCLicked() {
                totalRoomSum--;
                binding.tvTotalRoomQuantity.setText("" + totalRoomSum);
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        adapter.startListening();
    }
    int successCount = 0;

    private void uploadRooms(){

        binding.linearProgressIndicator.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("room");

        int count = 0;

        for (Floor floor : adapter.getFloorList()){
            String key = floor.getReference();
            HashMap<String, Object> roomHashMap = new HashMap<>();
            for (int i = 0; i < floor.getRoomsQuantity(); i++) {
                count ++;
                roomHashMap.put(myRef.push().getKey(),new Room(String.valueOf(count)));
            }
            myRef.child(key).updateChildren(roomHashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    successCount ++;
                    if (successCount == adapter.getFloorList().size()){
                        binding.linearProgressIndicator.setVisibility(View.GONE);
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_totalRoomsFragment_to_occupencyInputFragment,bundle);
                        successCount =0;
                    }
                }
            });
        }
    }
}