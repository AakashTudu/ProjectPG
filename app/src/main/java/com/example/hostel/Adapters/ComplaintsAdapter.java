package com.example.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Models.Complaint;
import com.example.hostel.databinding.LayoutComplaintsBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ComplaintsAdapter extends FirebaseRecyclerAdapter<Complaint, ComplaintsAdapter.ViewHolder> {

    public ComplaintsAdapter(@NonNull FirebaseRecyclerOptions<Complaint> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Complaint complaint) {
        holder.bind(position,complaint);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutComplaintsBinding binding = LayoutComplaintsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutComplaintsBinding layoutComplaintsBinding;

        public ViewHolder(@NonNull LayoutComplaintsBinding layoutComplaintsBinding) {
            super(layoutComplaintsBinding.getRoot());
            this.layoutComplaintsBinding = layoutComplaintsBinding;
        }

        public void bind(int position, Complaint complaint) {
            layoutComplaintsBinding.tvTenantName.setText(complaint.getN());
            layoutComplaintsBinding.tvPgName.setText(complaint.getPn());
            layoutComplaintsBinding.tvRoomNumber.setText(complaint.getR());
            layoutComplaintsBinding.tvComplaintDescription.setText(complaint.getC());
        }
    }
}



/*    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_complaints,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

public class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}*/
