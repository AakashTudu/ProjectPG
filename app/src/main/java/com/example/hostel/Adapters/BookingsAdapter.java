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
import com.example.hostel.databinding.LayoutOnboardedBinding;
import com.example.hostel.databinding.LayoutTenantsBinding;

import java.util.ArrayList;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> {

    ArrayList<Tenant> tenantList;
    String binding;

    public BookingsAdapter(ArrayList<Tenant> tenantList, String binding) {
        this.tenantList = tenantList;
        this.binding = binding;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (binding.equals(Constants.layoutTenantsBinding)) {
            LayoutTenantsBinding layoutTenantsBinding = LayoutTenantsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(layoutTenantsBinding);
        }else {
            LayoutOnboardedBinding layoutOnboardedBinding = LayoutOnboardedBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return new ViewHolder(layoutOnboardedBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (binding.equals(Constants.layoutTenantsBinding))
            holder.bindBoarded(position);
        else
            holder.bindOnboarded(position);
    }

    @Override
    public int getItemCount() {
        return tenantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutTenantsBinding layoutTenantsBinding;
        LayoutOnboardedBinding layoutOnboardedBinding;

        public ViewHolder(@NonNull LayoutTenantsBinding layoutTenantsBinding) {
            super(layoutTenantsBinding.getRoot());
            this.layoutTenantsBinding = layoutTenantsBinding;
        }

        public ViewHolder(@NonNull LayoutOnboardedBinding layoutOnboardedBinding) {
            super(layoutOnboardedBinding.getRoot());
            this.layoutOnboardedBinding = layoutOnboardedBinding;
        }

        public void bindBoarded(int position) {
            Tenant tenant = tenantList.get(position);
            layoutTenantsBinding.tvTenantName.setText(tenant.getN());
            layoutTenantsBinding.tvRoomQuantity.setText(tenant.getR());
            layoutTenantsBinding.tvPgName.setText(tenant.getPn());
            layoutTenantsBinding.tvOccupancy.setText(tenant.getO());
            layoutTenantsBinding.cardView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.tenant, tenant);
                Navigation.findNavController(view).navigate(R.id.action_bookingsFragment_to_bookingProfileFragment, bundle);
            });
        }

        public void bindOnboarded(int position) {
            Tenant tenant = tenantList.get(position);
            layoutOnboardedBinding.tvTenantName.setText(tenant.getN());
            layoutOnboardedBinding.tvRoomQuantity.setText(tenant.getR());
            layoutOnboardedBinding.tvPgName.setText(tenant.getPn());
            layoutOnboardedBinding.tvOccupancy.setText(tenant.getO());
        }
    }
}
