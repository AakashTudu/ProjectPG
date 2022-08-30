package com.example.hostel.Adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Listeners.OnRoomNumberChangeListener;
import com.example.hostel.Models.Occupancy;
import com.example.hostel.R;

import java.util.ArrayList;

public class TotalOccupancyAdapter extends RecyclerView.Adapter<TotalOccupancyAdapter.ViewHolder> {

    ArrayList<Occupancy> occupancyList;
    OnRoomNumberChangeListener onRoomNumberChangeListener;

    public TotalOccupancyAdapter(ArrayList<Occupancy> occupancyList, OnRoomNumberChangeListener onRoomNumberChangeListener) {
        this.occupancyList = occupancyList;
        this.onRoomNumberChangeListener = onRoomNumberChangeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_room_occupancy_item, parent, false);
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
        EditText et_room_quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_name = itemView.findViewById(R.id.btn_name);
            et_room_quantity = itemView.findViewById(R.id.et_room_quantity);
        }

        public void bind() {
            Occupancy occupancy = occupancyList.get(getAdapterPosition());
            btn_name.setText(occupancy.getName());
            if (occupancy.getRoomsQuantity() != 0)
                et_room_quantity.setText("" + occupancy.getRoomsQuantity());

            et_room_quantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() > 0)
                        occupancy.setRoomsQuantity(Integer.parseInt(charSequence.toString()));
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    onRoomNumberChangeListener.changed();
                }
            });
        }
    }
}
