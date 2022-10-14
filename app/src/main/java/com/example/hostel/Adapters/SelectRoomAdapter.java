package com.example.hostel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.DTO.AddTenantDTO;
import com.example.hostel.Models.Bed;
import com.example.hostel.Models.Room;
import com.example.hostel.databinding.LayoutRoomArrangementBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SelectRoomAdapter extends FirebaseRecyclerAdapter<Room,SelectRoomAdapter.ViewHolder> {

    OnEmptyCheckListener onEmptyCheckListener;
    AddTenantDTO addTenantDTO;

    public SelectRoomAdapter(@NonNull FirebaseRecyclerOptions<Room> options, OnEmptyCheckListener onEmptyCheckListener, AddTenantDTO addTenantDTO) {
        super(options);
        this.onEmptyCheckListener = onEmptyCheckListener;
        this.addTenantDTO = addTenantDTO;
    }

    @Override
    protected void onBindViewHolder(@NonNull SelectRoomAdapter.ViewHolder holder, int position, @NonNull Room room) {
        holder.bind(position, room);
    }

    @NonNull
    @Override
    public SelectRoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutRoomArrangementBinding binding = LayoutRoomArrangementBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        binding.ivMore.setVisibility(View.GONE);
        return new ViewHolder(binding);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        if (getItemCount() == 0){
            onEmptyCheckListener.isEmpty();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutRoomArrangementBinding layoutRoomArrangementBinding;
        public ViewHolder(@NonNull LayoutRoomArrangementBinding layoutRoomArrangementBinding) {
            super(layoutRoomArrangementBinding.getRoot());
            this.layoutRoomArrangementBinding = layoutRoomArrangementBinding;
        }

        public void bind(int position, Room room) {
            layoutRoomArrangementBinding.tvRoomQuantity.setText(getItem(position).getN());
            String ref = getRef(position).getKey();
            addTenantDTO.setRoomNumber(room.getN());
            loadBedRecyclerView(layoutRoomArrangementBinding.rvBed, itemView.getContext(),ref);
        }


        private void loadBedRecyclerView(RecyclerView recyclerView, Context context, String reference) {

            Query query = FirebaseDatabase.getInstance().getReference().child("bed").child(reference);
            FirebaseRecyclerOptions<Bed> options = new FirebaseRecyclerOptions.Builder<Bed>()
                    .setQuery(query, snapshot -> {
                        String bedNumber = snapshot.child("n").getValue().toString();
                        String bedStatus = snapshot.child("s").getValue().toString();
                        return new Bed(
                                bedNumber, bedStatus
                        );
                    }).build();



            SelectRoomBedAdapter bedAdapter = new SelectRoomBedAdapter(options, addTenantDTO);
            recyclerView.setAdapter(bedAdapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));

            bedAdapter.startListening();
        }
    }

    public interface OnEmptyCheckListener{
        void isEmpty();
    }
}
