package com.example.hostel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.Adapters.FloorHorizontalAdapter;
import com.example.hostel.Adapters.RoomDetailAdapter;
import com.example.hostel.Listeners.OnFloorItemClickListener;
import com.example.hostel.Models.Floor;
import com.example.hostel.Models.Tenant;
import com.example.hostel.databinding.FragmentRoomDetailBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class RoomDetailFragment extends Fragment {

    FragmentRoomDetailBinding binding;
    RoomDetailAdapter adapter;
    RoomDetailFragmentArgs args;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRoomDetailBinding.inflate(inflater,container,false);
        args = RoomDetailFragmentArgs.fromBundle(getArguments());
        loadRecyclerview(args.getPropertyRefKey());
        return binding.getRoot();
    }

    private void loadRecyclerview(String propertyRefKey) {
        Log.d("sdgsdfsdf", "propertyRefKey: " + propertyRefKey);
        Query query = FirebaseDatabase.getInstance().getReference().child("tenants").orderByChild("rf").equalTo(propertyRefKey);
        FirebaseRecyclerOptions<Tenant> options = new FirebaseRecyclerOptions.Builder<Tenant>()
                .setQuery(query, snapshot -> {
                    return snapshot.getValue(Tenant.class);
                }).build();
        adapter = new RoomDetailAdapter(options);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter!=null)
            adapter.stopListening();
    }
}