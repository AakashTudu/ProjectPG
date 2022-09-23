package com.example.hostel.Adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Models.Property;
import com.example.hostel.R;
import com.example.hostel.Utils.Constants;
import com.example.hostel.databinding.LayoutPropertiesBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RoomVacanciesAdapter extends FirebaseRecyclerAdapter<Property, RoomVacanciesAdapter.ViewHolder> {


    public RoomVacanciesAdapter(@NonNull FirebaseRecyclerOptions<Property> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Property property) {
        holder.bind(position, property);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutPropertiesBinding binding = LayoutPropertiesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutPropertiesBinding layoutPropertiesBinding;
        public ViewHolder(@NonNull LayoutPropertiesBinding layoutPropertiesBinding) {
            super(layoutPropertiesBinding.getRoot());
            this.layoutPropertiesBinding = layoutPropertiesBinding;
        }

        public void bind(int position, Property property) {
            layoutPropertiesBinding.tvPgName.setText(property.getName());
            layoutPropertiesBinding.tvType.setText(property.getType());
            layoutPropertiesBinding.tvLocation.setText(property.getLocation() + ", " + property.getCity());
            layoutPropertiesBinding.ivBuilding.setBackgroundResource(R.drawable.ic_building);
            layoutPropertiesBinding.cardView.setOnClickListener(view -> {
                String timeStampRef = getRef(position).getKey();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.propertyRef, timeStampRef);
                bundle.putString(Constants.fragment, Constants.managementFragment);
                Navigation.findNavController(view).navigate(R.id.action_roomVacanciesFragment_to_roomArrangementFragment, bundle);
            });
        }
    }
}
