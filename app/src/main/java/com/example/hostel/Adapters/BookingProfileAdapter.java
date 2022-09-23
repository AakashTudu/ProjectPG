package com.example.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Models.TenantDetail;
import com.example.hostel.databinding.LayoutTenantDetailBinding;

import java.util.ArrayList;

public class BookingProfileAdapter extends RecyclerView.Adapter<BookingProfileAdapter.ViewHolder> {

    ArrayList<TenantDetail> tenantDetailsList;

    public BookingProfileAdapter(ArrayList<TenantDetail> tenantDetailsList) {
        this.tenantDetailsList = tenantDetailsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutTenantDetailBinding binding = LayoutTenantDetailBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return tenantDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutTenantDetailBinding binding;
        public ViewHolder(@NonNull LayoutTenantDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


        public void bind(int position) {
            binding.textView.setText(tenantDetailsList.get(position).getDetail());
            binding.ivImage.setBackgroundResource(tenantDetailsList.get(position).getId());
        }
    }
}
