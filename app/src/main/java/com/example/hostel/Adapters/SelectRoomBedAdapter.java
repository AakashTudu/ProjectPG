package com.example.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.DTO.AddTenantDTO;
import com.example.hostel.Fragments.SelectRoomFragmentDirections;
import com.example.hostel.Models.Bed;
import com.example.hostel.Models.Property;
import com.example.hostel.Models.Room;
import com.example.hostel.R;
import com.example.hostel.databinding.LayoutBedBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;

public class SelectRoomBedAdapter extends FirebaseRecyclerAdapter<Bed,SelectRoomBedAdapter.ViewHolder> {

    AddTenantDTO addTenantDTO;

    public SelectRoomBedAdapter(@NonNull FirebaseRecyclerOptions<Bed> options,AddTenantDTO addTenantDTO) {
        super(options);
        this.addTenantDTO  = addTenantDTO;
    }

    @Override
    protected void onBindViewHolder(@NonNull SelectRoomBedAdapter.ViewHolder holder, int position, @NonNull Bed bed) {
        holder.bind(position, bed);
    }

    @NonNull
    @Override
    public SelectRoomBedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutBedBinding binding = LayoutBedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutBedBinding layoutBedBinding;
        public ViewHolder(@NonNull LayoutBedBinding layoutBedBinding) {
            super(layoutBedBinding.getRoot());
            this.layoutBedBinding = layoutBedBinding;
        }

        public void bind(int position, Bed bed) {
            layoutBedBinding.tvBedNumber.setText(bed.getN());

            switch (bed.getS()){
                case "v":
                    layoutBedBinding.ivBed.setBackgroundResource(R.drawable.ic_bed_vacant);
                    break;
                case "o":
                    layoutBedBinding.ivBed.setBackgroundResource(R.drawable.ic_bed_occupied);
                    break;
            }

            layoutBedBinding.ivBed.setOnClickListener(view -> {
                if (bed.getS().equals("v")){
                    addTenantDTO.setBedNumber(bed.getN());
                    addTenantDTO.setBedRef(getRef(position).toString());
                    addTenantDTO.setSharingType(getItemCount());

                    Navigation.findNavController(view).navigate(
                            SelectRoomFragmentDirections.actionSelectRoomFragmentToSelectSharingFragment(addTenantDTO)
                    );
                }else{
                    Snackbar.make(view, "Please select Vacant Bed",Snackbar.LENGTH_SHORT).show();
                }
            });

        }
    }
}
