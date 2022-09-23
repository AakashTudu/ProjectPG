package com.example.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Listeners.OnBedOptionClickListener;
import com.example.hostel.Listeners.OnBtnClickListener;
import com.example.hostel.Listeners.OnEditBedListener;
import com.example.hostel.Models.Bed;
import com.example.hostel.R;
import com.example.hostel.Utils.BottomSheet;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class BedAdapter extends FirebaseRecyclerAdapter<Bed,BedAdapter.ViewHolder> {

    public BedAdapter(@NonNull FirebaseRecyclerOptions<Bed> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Bed bed) {
        holder.bind(position, bed);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bed,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBed;
        TextView tvBedNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBed = itemView.findViewById(R.id.ivBed);
            tvBedNumber = itemView.findViewById(R.id.tvBedNumber);

        }

        public void bind(int position, Bed bed) {
            tvBedNumber.setText(bed.getN());

            switch (bed.getS()){
                case "v":
                    ivBed.setBackgroundResource(R.drawable.ic_bed_vacant);
                    break;
                case "b":
                    ivBed.setBackgroundResource(R.drawable.ic_bed_booked);
                    break;
                case "o":
                    ivBed.setBackgroundResource(R.drawable.ic_bed_occupied);
                    break;
            }

            ivBed.setOnClickListener(view -> {
                BottomSheet.showBedOptionDialog(itemView.getContext(), new OnBedOptionClickListener() {
                    @Override
                    public void btnEditClicked() {
                        BottomSheet.editBedBottomDialog(itemView.getContext(), new OnEditBedListener() {
                            @Override
                            public void editBtnClicked(String bedNumber, String bedPrice) {
                                getItem(position).setN(bedNumber);
                                getItem(position).setP(bedPrice);
                                getRef(position).setValue(getItem(position));
                            }
                        }, bed.getN(), bed.getP());
                    }

                    @Override
                    public void btnDeleteClicked() {
                        getRef(position).removeValue();
                    }
                });
            });
        }
    }
}
