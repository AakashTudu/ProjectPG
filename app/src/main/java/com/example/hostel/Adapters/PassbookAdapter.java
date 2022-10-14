package com.example.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.databinding.LayoutPassbookBinding;

public class PassbookAdapter extends RecyclerView.Adapter<PassbookAdapter.ViewHolder> {

    @NonNull
    @Override
    public PassbookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutPassbookBinding binding = LayoutPassbookBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PassbookAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull LayoutPassbookBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
