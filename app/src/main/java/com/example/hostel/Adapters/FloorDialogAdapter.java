package com.example.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.BottomSheetDialog.FloorDialog;
import com.example.hostel.BottomSheetDialog.FloorDialog.OnFloorClickListener;
import com.example.hostel.Models.Floor;
import com.example.hostel.databinding.LayoutSheetFloorBinding;
import com.example.monthandyearpicker.Utils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class FloorDialogAdapter extends FirebaseRecyclerAdapter<Floor, FloorDialogAdapter.ViewHolder> {

    OnFloorClickListener onFloorClickListener;
    FloorDialog floorDialog;

    public FloorDialogAdapter(@NonNull FirebaseRecyclerOptions<Floor> options, OnFloorClickListener onFloorClickListener, FloorDialog floorDialog) {
        super(options);
        this.onFloorClickListener = onFloorClickListener;
        this.floorDialog = floorDialog;
    }

    @Override
    protected void onBindViewHolder(@NonNull FloorDialogAdapter.ViewHolder holder, int position, @NonNull Floor floor) {
        holder.bind(position, floor);
    }

    @NonNull
    @Override
    public FloorDialogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSheetFloorBinding binding = LayoutSheetFloorBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutSheetFloorBinding layoutSheetFloorBinding;

        public ViewHolder(@NonNull LayoutSheetFloorBinding layoutSheetFloorBinding) {
            super(layoutSheetFloorBinding.getRoot());
            this.layoutSheetFloorBinding = layoutSheetFloorBinding;
        }


        public void bind(int position, Floor floor) {
            TextView btnFloor = layoutSheetFloorBinding.tvFloorName;
            btnFloor.setText(Utils.numberToOrdinalWord(floor.getN()));
            btnFloor.setOnClickListener(view -> {
                onFloorClickListener.floorClicked(floor, getRef(position).getKey());
                floorDialog.cancel();
            });
        }
    }
}
