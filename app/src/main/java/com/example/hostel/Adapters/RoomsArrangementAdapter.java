package com.example.hostel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Listeners.OnRoomOptionClickListener;
import com.example.hostel.Models.Bed;
import com.example.hostel.Models.Room;
import com.example.hostel.R;
import com.example.hostel.Utils.BottomSheet;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

public class RoomsArrangementAdapter extends FirebaseRecyclerAdapter<Room,RoomsArrangementAdapter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RoomsArrangementAdapter(@NonNull FirebaseRecyclerOptions<Room> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_room_arrangement,parent,false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Room room) {
        holder.bind(position, room);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomNumber;
        RecyclerView rVBed;
        ImageView ivMore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomNumber = itemView.findViewById(R.id.tvRoomQuantity);
            rVBed = itemView.findViewById(R.id.rvBed);
            ivMore = itemView.findViewById(R.id.ivMore);
        }

        public void bind(int position, Room room) {
            tvRoomNumber.setText(getItem(position).getN());
            String ref = getRef(position).getKey();
            loadBedRecyclerView(rVBed, itemView.getContext(),ref);

            ivMore.setOnClickListener(view -> {
                BottomSheet.showRoomOptionDialog(rVBed.getContext(), new OnRoomOptionClickListener() {
                    @Override
                    public void btnEditClicked() {
                        BottomSheet.editRoomBottomDialog(itemView.getContext(), roomNumber -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("n", roomNumber);
                            getRef(position).setValue(map);
                        }, room.getN());
                    }

                    @Override
                    public void btnAddClicked() {
                        BottomSheet.addBedBottomDialog(itemView.getContext(), (bedNumber,bedPrice) -> {
                            String ref1 = getRef(position).getKey();
                            Map<String, Object> map = new HashMap<>();
                            map.put("n", bedNumber);
                            map.put("p", bedPrice);
                            map.put("s", "v");
                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference userRef = rootRef.child("bed").child(ref1).child(String.valueOf(System.currentTimeMillis()));
                            userRef.setValue(map);
                        });
                    }

                    @Override
                    public void btnDeleteClicked() {
                        getRef(position).removeValue();
                    }
                });
            });

            /*ivMore.setOnClickListener(view -> {
                BottomSheet.addBedBottomDialog(itemView.getContext(), new OnBtnClickListener() {
                    @Override
                    public void btnClicked(String data) {
                        Log.d("sdgsdgd", "ref: " + getRef(position).getKey());
                        String ref = getRef(position).getKey();
                        Map<String, Object> map = new HashMap<>();
                        map.put("n", data);
                        map.put("s", "v");
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference userRef = rootRef.child("bed").child(ref).child(String.valueOf(System.currentTimeMillis()));
                        userRef.setValue(map);
                    }
                });
            });*/
        }
    }

    private void loadBedRecyclerView(RecyclerView recyclerView, Context context, String reference) {

        Query query = FirebaseDatabase.getInstance().getReference().child("bed").child(reference);
        FirebaseRecyclerOptions<Bed> options = new FirebaseRecyclerOptions.Builder<Bed>()
                .setQuery(query, snapshot -> {
                    String bedNumber = snapshot.child("n").getValue().toString();
                    String bedPrice = snapshot.child("p").getValue().toString();
                    String bedStatus = snapshot.child("s").getValue().toString();
                    return new Bed(
                            bedNumber,bedPrice, bedStatus
                    );
                }).build();
        BedAdapter bedAdapter = new BedAdapter(options);
        recyclerView.setAdapter(bedAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));

        bedAdapter.startListening();
    }
}
