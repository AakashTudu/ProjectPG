package com.example.hostel.Adapters;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Models.Floor;
import com.example.hostel.R;

import java.util.ArrayList;

public class TotalFloorsAdapter extends RecyclerView.Adapter<TotalFloorsAdapter.ViewHolder> {

    ArrayList<Floor> floorList;

    public TotalFloorsAdapter(ArrayList<Floor> floorList) {
        this.floorList = floorList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_floor_item, parent, false);
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

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatButton btnMinus, btnPlus;
        EditText btnFloor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnFloor = itemView.findViewById(R.id.btn_floor_name);
            btnMinus = itemView.findViewById(R.id.btn_minus);
            btnPlus = itemView.findViewById(R.id.btn_plus);
        }

        public void bind(int position) {
            Floor floor = floorList.get(position);

            if (floor.getFloorName().equals(""))
                btnFloor.setText("");
            else {
                btnFloor.setText(floorList.get(position).getFloorName());
            }

            if (floor.getSelected()) {
                btnFloor.setTextColor(Color.WHITE);
                btnFloor.setBackgroundResource(R.drawable.solid_border);
            } else {
                btnFloor.setTextColor(Color.BLACK);
                btnFloor.setBackgroundResource(R.drawable.outlined_border);
            }
            btnMinus.setOnClickListener(view -> {
                btnFloor.setTextColor(Color.BLACK);
                btnFloor.setBackgroundResource(R.drawable.outlined_border);
                floor.setSelected(false);
            });
            btnPlus.setOnClickListener(view -> {
                btnFloor.setTextColor(Color.WHITE);
                btnFloor.setBackgroundResource(R.drawable.solid_border);
                floor.setSelected(true);
            });

            btnFloor.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable != null) {
                        floorList.get(position).setFloorName(editable.toString());
                    }
                }
            });

        }
    }
}
