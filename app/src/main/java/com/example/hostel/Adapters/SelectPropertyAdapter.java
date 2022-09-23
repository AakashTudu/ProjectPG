package com.example.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Listeners.OnSelectPropertyListener;
import com.example.hostel.Models.Property;
import com.example.hostel.R;
import com.example.hostel.databinding.LayoutSelectPropertyBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class SelectPropertyAdapter extends FirebaseRecyclerAdapter<Property, SelectPropertyAdapter.ViewHolder> {
    OnSelectPropertyListener onSelectPropertyListener;
    public SelectPropertyAdapter(@NonNull FirebaseRecyclerOptions<Property> options, OnSelectPropertyListener onSelectPropertyListener) {
        super(options);
        this.onSelectPropertyListener = onSelectPropertyListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Property property) {
        holder.bind(position,property);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSelectPropertyBinding binding = LayoutSelectPropertyBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutSelectPropertyBinding binding;
        public ViewHolder(@NonNull LayoutSelectPropertyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position, Property property) {

            binding.tvPgName.setText(property.getName());
            binding.tvType.setText(property.getType());
            binding.tvLocation.setText(property.getLocation() + ", " + property.getCity());

            Boolean isLive = Boolean.parseBoolean(property.getIsLive());

/*            if (isLive){
                ivBuilding.setBackgroundResource(R.drawable.ic_building_active);
            }else
                ivBuilding.setBackgroundResource(R.drawable.ic_building_deactivated);*/
            binding.ivBuilding.setBackgroundResource(R.drawable.ic_building);

            binding.cardView.setOnClickListener(view -> {
                onSelectPropertyListener.selected(property,getRef(position).getKey());
            });
        }
    }
}
