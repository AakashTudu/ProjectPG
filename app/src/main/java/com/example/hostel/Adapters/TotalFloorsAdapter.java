package com.example.hostel.Adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Listeners.OnBtnClickListener;
import com.example.hostel.Listeners.OnFloorOptionClickListener;
import com.example.hostel.Models.Floor;
import com.example.hostel.R;
import com.example.hostel.Utils.BottomSheet;
import com.example.hostel.databinding.LayoutFloorItemBinding;
import com.example.monthandyearpicker.Utils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.HashMap;
import java.util.Map;

public class TotalFloorsAdapter extends FirebaseRecyclerAdapter<Floor,TotalFloorsAdapter.ViewHolder> {

    public TotalFloorsAdapter(@NonNull FirebaseRecyclerOptions<Floor> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutFloorItemBinding binding = LayoutFloorItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Floor floor) {
        holder.bind(position, floor);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LayoutFloorItemBinding layoutFloorItemBinding;

        public ViewHolder(@NonNull LayoutFloorItemBinding layoutFloorItemBinding) {
            super(layoutFloorItemBinding.getRoot());
            this.layoutFloorItemBinding = layoutFloorItemBinding;
        }

        public void bind(int position, Floor floor) {

            TextView btnFloor = layoutFloorItemBinding.tvFloorName;

            btnFloor.setText(Utils.numberToOrdinalWord(floor.getN()));

            if (floor.getSelected()) {
                btnFloor.setTextColor(Color.WHITE);
                btnFloor.setBackgroundResource(R.drawable.solid_border_7_dp);
            } else {
                btnFloor.setTextColor(Color.BLACK);
                btnFloor.setBackgroundResource(R.drawable.outlined_border);
            }

            btnFloor.setOnClickListener(view -> {
                if (floor.getSelected()){
                    btnFloor.setTextColor(Color.BLACK);
                    btnFloor.setBackgroundResource(R.drawable.outlined_border);
                    floor.setSelected(false);
                }else{
                    btnFloor.setTextColor(Color.WHITE);
                    btnFloor.setBackgroundResource(R.drawable.solid_border_7_dp);
                    floor.setSelected(true);
                }
            });
        }
    }
}
