package com.example.hostel.Adapters.paging;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hostel.Models.Transaction;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.LayoutPendingAmountBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shreyaspatil.firebase.recyclerpagination.DatabasePagingOptions;
import com.shreyaspatil.firebase.recyclerpagination.FirebaseRecyclerPagingAdapter;
import com.shreyaspatil.firebase.recyclerpagination.LoadingState;

import java.text.ParseException;

public class CollectionPaidAdapter extends FirebaseRecyclerPagingAdapter<Transaction, CollectionPaidAdapter.ViewHolder> {

    SwipeRefreshLayout swipeRefreshLayout;

    public CollectionPaidAdapter(@NonNull DatabasePagingOptions<Transaction> options, SwipeRefreshLayout swipeRefreshLayout) {
        super(options);
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected void onBindViewHolder(@NonNull CollectionPaidAdapter.ViewHolder viewHolder, int position, @NonNull Transaction transaction) {
        viewHolder.bind(position, transaction);
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
            case ERROR:
                //Reached end of Data set
                // Stop Animation
                swipeRefreshLayout.setRefreshing(false);
                break;
            //retry();
        }
    }

    @NonNull
    @Override
    public CollectionPaidAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutPendingAmountBinding binding = LayoutPendingAmountBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LayoutPendingAmountBinding binding;
        String name = "";
        String roomNumber = "";
        String pgName = "";

        public ViewHolder(@NonNull LayoutPendingAmountBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position, Transaction transaction) {
            binding.tvPaymentType.setText("Paid date");
            binding.tvPendingAmount.setText("₹ " + transaction.getAmountPaid());

            try {
                String formattedDate = UserUtils.dateConverter(transaction.getDate(), "dd-MM-yyyy", "dd MMMM yyyy");
                binding.tvDateOfPayment.setText(formattedDate);

                if (transaction.getPaymentType().equals("Rent")) {
                    String str = UserUtils.dateConverter(transaction.getDate(), "dd-MM-yyyy", "MMMM") + " Month Rent";
                    binding.tvMonth.setText(str);
                } else {
                    binding.tvMonth.setText(transaction.getDate());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference nameReference = database.getReference("tenants").
                    child(transaction.getTenantRef())
                    .child("n");
            DatabaseReference roomReference = database.getReference("tenants").
                    child(transaction.getTenantRef())
                    .child("r");

            DatabaseReference pgNameReference = database.getReference("tenants").
                    child(transaction.getTenantRef())
                    .child("pn");

            nameReference.keepSynced(true);
            roomReference.keepSynced(true);
            pgNameReference.keepSynced(true);

            nameReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    name = snapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            roomReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    roomNumber = snapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            pgNameReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    pgName = snapshot.getValue(String.class);
                    binding.shimmerViewContainer.hideShimmer();
                    binding.tvTenantName.setBackgroundColor(Color.WHITE);
                    binding.tvRoomNumberDetail.setBackgroundColor(Color.WHITE);

                    binding.tvTenantName.setText(name);
                    binding.tvRoomNumberDetail.setText(roomNumber + " - " + pgName);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
