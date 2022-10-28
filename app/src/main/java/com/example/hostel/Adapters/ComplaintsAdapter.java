package com.example.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Fragments.ComplaintsFragment;
import com.example.hostel.Fragments.ComplaintsFragmentDirections;
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
        LayoutComplaintsBinding binding;

        public ViewHolder(@NonNull LayoutComplaintsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position, Complaint complaint) {
            binding.tvTenantName.setText(complaint.getN());
            binding.tvPgName.setText(complaint.getPn());
            binding.tvRoomNumber.setText(complaint.getR());
            binding.tvComplaintDescription.setText(complaint.getC());

            binding.cardView.setOnClickListener(view -> {
                Navigation.findNavController(view).navigate(
                        ComplaintsFragmentDirections.actionComplaintsFragmentToComplaintStatusFragment()
                );
            });
        }
    }
}
