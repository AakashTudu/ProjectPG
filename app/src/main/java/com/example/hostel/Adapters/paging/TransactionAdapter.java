package com.example.hostel.Adapters.paging;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hostel.Models.Transaction;
import com.example.hostel.R;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.LayoutTranBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shreyaspatil.firebase.recyclerpagination.DatabasePagingOptions;
import com.shreyaspatil.firebase.recyclerpagination.FirebaseRecyclerPagingAdapter;
import com.shreyaspatil.firebase.recyclerpagination.LoadingState;

import java.text.ParseException;

public class TransactionAdapter extends FirebaseRecyclerPagingAdapter<Transaction,TransactionAdapter.ViewHolder> {

    SwipeRefreshLayout swipeRefreshLayout;

    public TransactionAdapter(@NonNull DatabasePagingOptions<Transaction> options,SwipeRefreshLayout swipeRefreshLayout) {
        super(options);
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull Transaction transaction) {
        viewHolder.bind(position,transaction);
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        switch (state) {
            case LOADING_INITIAL:
            case LOADING_MORE:
                // Do your loading animation
                swipeRefreshLayout.setRefreshing(true);
                break;

            case LOADED:
            case FINISHED:
                swipeRefreshLayout.setRefreshing(false);
                break;

            case ERROR:
                //Reached end of Data set
                // Stop Animation
                swipeRefreshLayout.setRefreshing(false);
                break;
            //retry();
        }
    }

    @Override
    protected void onError(@NonNull DatabaseError databaseError) {
        super.onError(databaseError);
        swipeRefreshLayout.setRefreshing(false);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutTranBinding binding = LayoutTranBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutTranBinding binding;
        String name = "";
        public ViewHolder(@NonNull LayoutTranBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position, Transaction transaction) {
            binding.tvAmount.setText("₹" + transaction.getAmountPaid());
            binding.tvPaymentMode.setText(transaction.getPaymentMode());

            if (transaction.getPaymentMode().equals("Cash")){
                binding.ivPaymentMode.setBackgroundResource(R.drawable.ic_cash);
            }else{
                binding.ivPaymentMode.setBackgroundResource(R.drawable.ic_bhim_upi);
            }

            try {
                String formattedDate = UserUtils.dateConverter(transaction.getDate(),"dd-MM-yyyy","dd MMMM yyyy");
                binding.tvPaymentDate.setText(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference nameReference = database.getReference("tenants").
                    child(transaction.getTenantRef())
                    .child("n");
            DatabaseReference numberReference = database.getReference("tenants").
                    child(transaction.getTenantRef())
                    .child("p");

            nameReference.keepSynced(true);
            numberReference.keepSynced(true);
            nameReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    name = snapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            numberReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    binding.shimmerViewContainer.hideShimmer();
                    binding.tvTenantPhoneNumber.setText(snapshot.getValue(String.class));
                    binding.tvTenantPhoneNumber.setBackgroundColor(Color.WHITE);

                    binding.tvTenantName.setText(name);
                    binding.tvTenantName.setBackgroundColor(Color.WHITE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
    }
}
