package com.example.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Listeners.OnGroupBtnClickListener;
import com.example.hostel.Models.Floor;
import com.example.hostel.R;

import java.util.ArrayList;

public class TotalRoomsAdapter extends RecyclerView.Adapter<TotalRoomsAdapter.ViewHolder> {

    ArrayList<Floor> floorList;
    OnGroupBtnClickListener onGroupBtnClickListener;

    public TotalRoomsAdapter(ArrayList<Floor> floorList, OnGroupBtnClickListener onGroupBtnClickListener) {
        this.floorList = floorList;
        this.onGroupBtnClickListener = onGroupBtnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_room_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return floorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView btn_floor_name,tv_room_quantity;
        AppCompatButton btn_minus;
        AppCompatButton btn_plus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_floor_name = itemView.findViewById(R.id.btn_name);
            btn_minus = itemView.findViewById(R.id.btn_minus);
            btn_plus = itemView.findViewById(R.id.btn_plus);
            tv_room_quantity = itemView.findViewById(R.id.tv_room_quantity);
        }

        public void bind(int position) {
            Floor floor = floorList.get(position);
            btn_floor_name.setText(floor.getFloorName());
            tv_room_quantity.setText("" + floor.getRoomsQuantity());
            btn_minus.setOnClickListener(view -> {
                if (floor.getRoomsQuantity() > 0) {
                    int quantity = floor.getRoomsQuantity();
                    quantity = quantity - 1;
                    tv_room_quantity.setText("" + quantity);
                    onGroupBtnClickListener.minusBtnCLicked();
                    floor.setRoomsQuantity(quantity);
                }
            });

            btn_plus.setOnClickListener(view -> {
                int quantity = floor.getRoomsQuantity();
                quantity = quantity + 1;
                floor.setRoomsQuantity(quantity);
                tv_room_quantity.setText("" + quantity);
                onGroupBtnClickListener.addBtnClicked();
            });
        }
    }
}
