package com.example.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.DTO.AddTenantDTO;
import com.example.hostel.Fragments.SelectPropertyFragmentDirections;
import com.example.hostel.Models.Property;
import com.example.hostel.R;
import com.example.hostel.databinding.LayoutSelectPropertyBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class SelectPropertyAdapter extends FirebaseRecyclerAdapter<Property, SelectPropertyAdapter.ViewHolder> {
    AddTenantDTO addTenantDTO;

    public SelectPropertyAdapter(@NonNull FirebaseRecyclerOptions<Property> options, AddTenantDTO addTenantDTO) {
        super(options);
        this.addTenantDTO = addTenantDTO;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Property property) {
        holder.bind(position, property);
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

            //Boolean isLive = Boolean.parseBoolean(property.getIsLive());

/*            if (isLive){
                ivBuilding.setBackgroundResource(R.drawable.ic_building_active);
            }else
                ivBuilding.setBackgroundResource(R.drawable.ic_building_deactivated);*/
            binding.ivBuilding.setBackgroundResource(R.drawable.ic_building);


            String ref = getRef(position).getKey();
            String code = ref.substring(ref.length() - 5);
            binding.tvCode.setText(code);

            binding.cardView.setOnClickListener(view -> {
                addTenantDTO.setPropertyRefKey(getRef(position).getKey());
                addTenantDTO.setProperty(property);
                Navigation.findNavController(view).navigate(
                        SelectPropertyFragmentDirections.actionSelectPropertyFragmentToSelectRoomFragment(addTenantDTO)
                );
            });
        }
    }
}
