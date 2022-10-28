package com.example.hostel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.Adapters.PassbookAdapter;
import com.example.hostel.Models.Transaction;
import com.example.hostel.databinding.FragmentPassbookBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class PassbookFragment extends Fragment {

    FragmentPassbookBinding binding;
    PassbookAdapter adapter;
    PassbookFragmentArgs args;
    public PassbookFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPassbookBinding.inflate(inflater,container, false);
        args = PassbookFragmentArgs.fromBundle(getArguments());
        loadRecyclerView();

        return binding.getRoot();
    }

    private void loadRecyclerView() {

        Query query = FirebaseDatabase.getInstance().getReference().child("transaction").orderByChild("tenantRef").equalTo(args.getTenantRefKey());

        FirebaseRecyclerOptions<Transaction> options = new FirebaseRecyclerOptions.Builder<Transaction>()
                .setQuery(query, snapshot -> {
                    return snapshot.getValue(Transaction.class);
                }).build();

        adapter = new PassbookAdapter(options);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter!=null){
            adapter.startListening();
        }
    }
}