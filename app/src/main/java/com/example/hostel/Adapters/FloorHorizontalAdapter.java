package com.example.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Listeners.OnFloorItemClickListener;
import com.example.hostel.Models.Floor;
import com.example.hostel.Models.User;
import com.example.hostel.R;
import com.example.monthandyearpicker.Utils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class FloorHorizontalAdapter extends FirebaseRecyclerAdapter<Floor,FloorHorizontalAdapter.ViewHolder> {

    OnFloorItemClickListener onFloorItemClickListener;
    Boolean isEmpty = true;

    public FloorHorizontalAdapter(@NonNull FirebaseRecyclerOptions<Floor> options, OnFloorItemClickListener onFloorItemClickListener) {
        super(options);
        this.onFloorItemClickListener = onFloorItemClickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Floor model) {
        if (isEmpty) {
            isEmpty = false;
            String roomRef = getRef(position).getKey();
            onFloorItemClickListener.firstItem(model.getN(),roomRef);
        }
        holder.bind(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_floor_horizontal,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFloorName;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFloorName = itemView.findViewById(R.id.tvFloorName);
            cardView = itemView.findViewById(R.id.cardView);
        }

        public void bind(int position) {
            Floor floor = getItem(position);
            tvFloorName.setText(Utils.numberToOrdinalWord(floor.getN()));
            cardView.setOnClickListener(view -> {
                onFloorItemClickListener.clicked(floor.getN(),getRef(position).getKey());
            });
        }
    }
}
