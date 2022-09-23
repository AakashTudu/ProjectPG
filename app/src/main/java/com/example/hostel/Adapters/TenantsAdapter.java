package com.example.hostel.Adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Models.Tenant;
import com.example.hostel.R;
import com.example.hostel.Utils.Constants;
import com.example.hostel.databinding.LayoutTenantsBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class TenantsAdapter extends FirebaseRecyclerAdapter<Tenant, TenantsAdapter.ViewHolder> {
    public TenantsAdapter(@NonNull FirebaseRecyclerOptions<Tenant> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutTenantsBinding binding = LayoutTenantsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Tenant tenant) {
        holder.bind(position, tenant);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        LayoutTenantsBinding binding;

        public ViewHolder(@NonNull LayoutTenantsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position, Tenant tenant) {
            binding.tvTenantName.setText(tenant.getN());
            binding.tvRoomQuantity.setText(tenant.getR());
            binding.tvPgName.setText(tenant.getPn());
            binding.tvOccupancy.setText(tenant.getO());
            binding.cardView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.tenant, tenant);
                Navigation.findNavController(view).navigate(R.id.action_tenantsFragment_to_tenantProfileFragment, bundle);
            });
        }
    }
}
