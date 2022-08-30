package com.example.hostel.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Models.Floor;
import com.example.hostel.Models.Occupancy;
import com.example.hostel.R;

import java.util.ArrayList;

public class OccupancyInputAdapter extends RecyclerView.Adapter<OccupancyInputAdapter.ViewHolder> {
    ArrayList<Occupancy> occupancyList;

    public OccupancyInputAdapter(ArrayList<Occupancy> occupancyList) {
        this.occupancyList = occupancyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_floor_item, parent, false);
        return new ViewHolder(view);
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_name = itemView.findViewById(R.id.btn_name);
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
