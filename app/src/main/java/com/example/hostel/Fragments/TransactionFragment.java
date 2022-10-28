package com.example.hostel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hostel.Adapters.paging.TransactionAdapter;
import com.example.hostel.Models.Transaction;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.FragmentTransactionBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shreyaspatil.firebase.recyclerpagination.DatabasePagingOptions;

public class TransactionFragment extends Fragment {

    FragmentTransactionBinding binding;
    TransactionAdapter adapter;

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTransactionBinding.inflate(inflater, container, false);

        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        loadRecyclerView();
        loadCards();

        return binding.getRoot();
    }

    private void loadCards() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("transactionCard/"+UserUtils.phoneNumber()).child("received");
        myRef.keepSynced(true);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String received = String.valueOf(snapshot.getValue());
                if (!received.equals("null")){
                    binding.tvReceivedAmount.setText("₹ " + received);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadRecyclerView() {

        Query mDatabase = FirebaseDatabase.getInstance().getReference().child("transaction");
        PagedList.Config config = new PagedList.Config.Builder().setEnablePlaceholders(false).setPrefetchDistance(5).setPageSize(10).build();

        DatabasePagingOptions<Transaction> options = new DatabasePagingOptions.Builder<Transaction>().setLifecycleOwner(this).setQuery(mDatabase, config, Transaction.class).build();
        adapter = new TransactionAdapter(options, binding.swipeRefreshLayout);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.refresh();
            }
        });

        adapter.startListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (adapter != null)
            adapter.stopListening();
    }

}