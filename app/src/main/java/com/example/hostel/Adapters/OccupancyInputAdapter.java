package com.example.hostel.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Models.Occupancy;
import com.example.hostel.R;
import com.example.hostel.databinding.LayoutFloorItemBinding;

import java.util.ArrayList;

public class OccupancyInputAdapter extends RecyclerView.Adapter<OccupancyInputAdapter.ViewHolder> {
    ArrayList<Occupancy> occupancyList;

    public OccupancyInputAdapter(ArrayList<Occupancy> occupancyList) {
        this.occupancyList = occupancyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutFloorItemBinding binding = LayoutFloorItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return occupancyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView btn_name;
        public ViewHolder(@NonNull LayoutFloorItemBinding layoutFloorItemBinding) {
            super(layoutFloorItemBinding.getRoot());
            btn_name = layoutFloorItemBinding.tvFloorName;
        }

        public void bind() {
            Occupancy occupancy = occupancyList.get(getAdapterPosition());

            if (occupancy.getName().equals(""))
                btn_name.setText("");
            else {
                btn_name.setText(occupancyList.get(getAdapterPosition()).getName());
            }

            if (occupancy.getSelected()) {
                btn_name.setTextColor(Color.WHITE);
                btn_name.setBackgroundResource(R.drawable.solid_border_7_dp);
            } else {
                btn_name.setTextColor(Color.BLACK);
                btn_name.setBackgroundResource(R.drawable.outlined_border);
            }


            btn_name.setOnClickListener(view -> {
                if (occupancy.getSelected()){
                    btn_name.setTextColor(Color.BLACK);
                    btn_name.setBackgroundResource(R.drawable.outlined_border);
                    occupancy.setSelected(false);
                }else{
                    btn_name.setTextColor(Color.WHITE);
                    btn_name.setBackgroundResource(R.drawable.solid_border_7_dp);
                    occupancy.setSelected(true);
                }
            });
        }
    }
}
