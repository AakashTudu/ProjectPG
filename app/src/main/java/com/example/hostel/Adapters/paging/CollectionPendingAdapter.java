package com.example.hostel.Adapters.paging;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hostel.Models.Pending;
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

public class CollectionPendingAdapter extends FirebaseRecyclerPagingAdapter<Pending,CollectionPendingAdapter.ViewHolder> {

    private SwipeRefreshLayout swipeRefreshLayout;

    public CollectionPendingAdapter(@NonNull DatabasePagingOptions<Pending> options, SwipeRefreshLayout swipeRefreshLayout) {
        super(options);
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected void onBindViewHolder(@NonNull CollectionPendingAdapter.ViewHolder holder, int position, @NonNull Pending pending) {
        holder.bind(position, pending);
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
    public CollectionPendingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutPendingAmountBinding binding = LayoutPendingAmountBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
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

        public void bind(int position, Pending pending) {
            binding.tvPendingAmount.setText("₹ " + pending.getAmount());
            try {
                String formattedDate = UserUtils.dateConverter(pending.getDate(),"dd-MM-yyyy","dd MMMM yyyy");
                binding.tvDateOfPayment.setText(formattedDate);

                if (pending.getPaymentType().equals("Rent")){
                    String str = UserUtils.dateConverter(pending.getDate(),"dd-MM-yyyy","MMMM") + " Month Rent";
                    binding.tvMonth.setText(str);
                }else{
                    binding.tvMonth.setText(pending.getDate());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            binding.shimmerViewContainer.startShimmer();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference nameReference = database.getReference("tenants").
                    child(pending.getTenantRef())
                    .child("n");
            DatabaseReference roomReference = database.getReference("tenants").
                    child(pending.getTenantRef())
                    .child("r");

            DatabaseReference pgNameReference = database.getReference("tenants").
                    child(pending.getTenantRef())
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


            binding.tvPaymentType.setText("Due date");
        }
    }
}
