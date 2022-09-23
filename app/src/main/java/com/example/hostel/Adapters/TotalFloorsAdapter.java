package com.example.hostel.Adapters;

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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.HashMap;
import java.util.Map;

public class TotalFloorsAdapter extends FirebaseRecyclerAdapter<Floor,TotalFloorsAdapter.ViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TotalFloorsAdapter(@NonNull FirebaseRecyclerOptions<Floor> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_floor_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Floor model) {
        holder.bind(position);
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
        TextView tvFloorName;
        ImageView ivMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFloorName = itemView.findViewById(R.id.tvFloorName);
            ivMore = itemView.findViewById(R.id.ivMore);
        }

        public void bind(int position) {
            Floor floor = getItem(position);
            tvFloorName.setText(floor.getN());

            ivMore.setOnClickListener(view -> {
                BottomSheet.showFloorOptionDialog(itemView.getContext(), new OnFloorOptionClickListener() {
                    @Override
                    public void btnEditClicked() {

                        BottomSheet.editFloorBottomDialog(itemView.getContext(), data -> {

                            Map<String, Object> map = new HashMap<>();
                            map.put("n", data.trim());

                            getRef(position).setValue(map);
                        }, floor.getN().replace(" Floor",""));
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
