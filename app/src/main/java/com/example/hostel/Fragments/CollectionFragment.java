package com.example.hostel.Fragments;

import android.graphics.Color;
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

import com.example.hostel.Adapters.paging.CollectionPaidAdapter;
import com.example.hostel.Adapters.paging.CollectionPendingAdapter;
import com.example.hostel.Models.Pending;
import com.example.hostel.Models.Transaction;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.FragmentCollectionBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shreyaspatil.firebase.recyclerpagination.DatabasePagingOptions;

import java.util.Locale;

public class CollectionFragment extends Fragment {

    FragmentCollectionBinding binding;
    CollectionPendingAdapter pagingAdapter;
    public CollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCollectionBinding.inflate(inflater, container, false);

/*        if (getArguments()!=null){
            MonthlyCollection collection = (MonthlyCollection) getArguments().getSerializable(Constants.collection);
            if (collection!=null){
                binding.tvCollectedMoney.setText("Rs " + collection.getPaidPrice());
                binding.tvPendingMoney.setText("Rs " + collection.getPendingPrice());

                binding.lpiPaid.setProgress(collection.getIntPercentage());
                binding.lpiPending.setProgress(100 - collection.getIntPercentage());
                binding.tvPaidPercentage.setText(collection.getPercentage());
                binding.tvPendingPercentage.setText(collection.getPendingPercentage());
            }
        }*/

        loadCards();
        loadPaidPagingRecyclerView();

        binding.paidCard.setOnClickListener(view -> {
            binding.tvMoneyType.setText("RECEIVED");
            loadPaidPagingRecyclerView();
        });

        binding.pendingCard.setOnClickListener(view -> {
            binding.tvMoneyType.setText("PENDING");
            loadPendingPagingRecyclerView();
        });

        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        return binding.getRoot();
    }


    Integer received;

    private void loadCards() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference receivedRef = database.getReference("transactionCard/" + UserUtils.phoneNumber()).child("received");
        DatabaseReference pendingRef = database.getReference("transactionCard/" + UserUtils.phoneNumber()).child("pending");

        receivedRef.keepSynced(true);
        pendingRef.keepSynced(true);

        receivedRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                received = snapshot.getValue(Integer.class);
/*                if (!received.equals("null")){
//                    binding.tvCollectedMoney.setText("₹ " + received);
                    re
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        pendingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer pending = snapshot.getValue(Integer.class);
                if (pending != null) {
                    binding.tvPendingMoney.setText("₹ " + pending);
                    float pendingPercentage = ((float) pending / (received + pending)) * 100;
                    String percent = String.format(Locale.getDefault(), "%.2f", pendingPercentage) + " %";
                    binding.tvPendingPercentage.setText(percent);
                    binding.lpiPending.setProgress((int) pendingPercentage);
                } else {
                    binding.tvPendingMoney.setText("₹ 0");
                }

                if (received != null) {
                    binding.tvCollectedMoney.setText("₹ " + received);
                    float receivedPercentage = ((float) received / (received + pending)) * 100;
                    String percent = String.format(Locale.getDefault(), "%.2f", receivedPercentage) + " %";
                    binding.tvPaidPercentage.setText(percent);
                    binding.lpiPaid.setProgress((int) receivedPercentage);
                } else {
                    binding.tvCollectedMoney.setText("₹ 0");
                }

                binding.tvPendingMoney.setBackgroundColor(Color.WHITE);
                binding.tvCollectedMoney.setBackgroundColor(Color.WHITE);

                binding.shimmerViewContainer.hideShimmer();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadPaidPagingRecyclerView() {
        Query mDatabase = FirebaseDatabase.getInstance().getReference().child("transaction");
        PagedList.Config config = new PagedList.Config.Builder().setEnablePlaceholders(false).setPrefetchDistance(5).setPageSize(8).build();

        DatabasePagingOptions<Transaction> options = new DatabasePagingOptions.Builder<Transaction>().setLifecycleOwner(this).setQuery(mDatabase, config, Transaction.class).build();
        CollectionPaidAdapter adapter = new CollectionPaidAdapter(options, binding.swipeRefreshLayout);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.refresh();
            }
        });

    }

    private void loadPendingPagingRecyclerView() {

        Query mDatabase = FirebaseDatabase.getInstance().getReference().child("pendingAmount");

        PagedList.Config config = new PagedList.Config.Builder().setEnablePlaceholders(false).setPrefetchDistance(5).setPageSize(8).build();

        DatabasePagingOptions<Pending> options = new DatabasePagingOptions.Builder<Pending>().setLifecycleOwner(this).setQuery(mDatabase, config, Pending.class).build();
        pagingAdapter = new CollectionPendingAdapter(options, binding.swipeRefreshLayout);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(pagingAdapter);

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pagingAdapter.refresh();
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}