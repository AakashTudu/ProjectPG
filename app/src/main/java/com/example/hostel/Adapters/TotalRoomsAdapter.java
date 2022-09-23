package com.example.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Listeners.OnGroupBtnClickListener;
import com.example.hostel.Models.Floor;
import com.example.hostel.databinding.LayoutRoomItemBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TotalRoomsAdapter extends FirebaseRecyclerAdapter<Floor, TotalRoomsAdapter.ViewHolder> {

    OnGroupBtnClickListener onGroupBtnClickListener;

    public TotalRoomsAdapter(@NonNull FirebaseRecyclerOptions<Floor> options, OnGroupBtnClickListener onGroupBtnClickListener) {
        super(options);
        this.onGroupBtnClickListener = onGroupBtnClickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Floor floor) {
        holder.bind(position, floor);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutRoomItemBinding binding = LayoutRoomItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutRoomItemBinding binding;

        public ViewHolder(@NonNull LayoutRoomItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position, Floor floor) {

            binding.tvRoomQuantity.setText("" + floor.getRoomsQuantity());
            binding.btnName.setText(floor.getN());

            binding.btnMinus.setOnClickListener(view -> {
                if (floor.getRoomsQuantity() > 0) {
                    int quantity = floor.getRoomsQuantity();
                    quantity = quantity - 1;
                    binding.tvRoomQuantity.setText("" + quantity);
                    onGroupBtnClickListener.minusBtnCLicked();
                    floor.setRoomsQuantity(quantity);
                }
            });

            binding.btnPlus.setOnClickListener(view -> {
                int quantity = floor.getRoomsQuantity();
                quantity = quantity + 1;
                floor.setRoomsQuantity(quantity);
                binding.tvRoomQuantity.setText("" + quantity);
                onGroupBtnClickListener.addBtnClicked();
            });
        }
    }

    public HashMap<String,Floor> getFloorMap(){
        HashMap<String, Floor> map = new HashMap<>();
        for (int i = 0; i < this.getItemCount(); i++){
            map.put(getRef(i).getKey(),this.getItem(i));
        }
        return map;
    }

    public List<Floor> getFloorList(){
        List<Floor> floorList = new ArrayList<>();
        for (int i=0;i<this.getItemCount();i++){
            Floor floor = this.getItem(i);
            floor.setReference(this.getRef(i).getKey());
            floorList.add(floor);
        }
        return floorList;
    }
}
