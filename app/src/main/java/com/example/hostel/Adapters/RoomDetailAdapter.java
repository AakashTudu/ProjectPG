package com.example.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Models.Tenant;
import com.example.hostel.databinding.LayoutRoomDetailBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RoomDetailAdapter extends FirebaseRecyclerAdapter<Tenant,RoomDetailAdapter.ViewHolder> {

    public RoomDetailAdapter(@NonNull FirebaseRecyclerOptions<Tenant> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RoomDetailAdapter.ViewHolder viewHolder, int position, @NonNull Tenant tenant) {
        viewHolder.bind(position, tenant);
    }

    @NonNull
    @Override
    public RoomDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutRoomDetailBinding binding = LayoutRoomDetailBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutRoomDetailBinding binding;
        public ViewHolder(@NonNull LayoutRoomDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position, Tenant tenant) {
            binding.tvTenantName.setText(tenant.getN());
            binding.tvEmail.setText(tenant.getE());
            binding.tvNumber.setText(tenant.getP());

            String ref = getRef(position).getKey();
            String code = ref.substring(ref.length() - 5);
            binding.tvCode.setText(code);
        }
    }
}
