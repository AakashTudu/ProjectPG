package com.example.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Models.Transaction;
import com.example.hostel.databinding.LayoutPassbookBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PassbookAdapter extends FirebaseRecyclerAdapter<Transaction,PassbookAdapter.ViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PassbookAdapter(@NonNull FirebaseRecyclerOptions<Transaction> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PassbookAdapter.ViewHolder viewHolder, int position, @NonNull Transaction Transaction) {
        viewHolder.bind(position, Transaction);
    }

    @NonNull
    @Override
    public PassbookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutPassbookBinding binding = LayoutPassbookBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutPassbookBinding binding;
        public ViewHolder(@NonNull LayoutPassbookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position, Transaction transaction) {
            binding.tvDate.setText(transaction.getDate());
            binding.tvType.setText(transaction.getPaymentType());
            binding.tvAmount.setText("₹ " + transaction.getAmountPaid());
        }
    }
}
