package com.example.hostel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Fragments.RoomArrangementFragmentDirections;
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
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class RoomsArrangementAdapter extends FirebaseRecyclerAdapter<Room,RoomsArrangementAdapter.ViewHolder> {

    String propertyRefKey;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     * @param propertyRefKey
     */
    public RoomsArrangementAdapter(@NonNull FirebaseRecyclerOptions<Room> options, String propertyRefKey) {
        super(options);
        this.propertyRefKey = propertyRefKey;
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
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomNumber = itemView.findViewById(R.id.tvRoomQuantity);
            rVBed = itemView.findViewById(R.id.rvBed);
            ivMore = itemView.findViewById(R.id.ivMore);
            cardView = itemView.findViewById(R.id.cardView);
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
                        BottomSheet.addBedBottomDialog(itemView.getContext(), (bedNumber) -> {
                            String ref1 = getRef(position).getKey();
                            Map<String, Object> map = new HashMap<>();
                            map.put("n", bedNumber);
                            map.put("s", "v");
                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference userRef = rootRef.child("bed").child(ref1);
                            userRef.push().setValue(map);

                            updateBedCards(propertyRefKey,1);
                        });
                    }

                    @Override
                    public void btnSharingTypeClicked() {
                        BottomSheet.showOccupancyOptionDialog(itemView.getContext(), data -> {

                            int sharing = 0;

                            switch (data){
                                case "Single Occupancy":
                                    sharing = 1;
                                    break;
                                case "Double Occupancy":
                                    sharing = 2;
                                    break;
                                case "Triple Occupancy":
                                    sharing = 3;
                                    break;
                                case "Four Occupancy":
                                    sharing = 4;
                                    break;
                                case "Five Occupancy":
                                    sharing = 5;
                                    break;
                                case "Six Occupancy":
                                    sharing = 6;
                                    break;
                            }

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("bed");
                            String roomRef = getRef(position).getKey();

                            HashMap<String, Object> bedHashMap = new HashMap<>();

                            for (int i = 1; i <= sharing; i++) {
                                bedHashMap.put(myRef.push().getKey(),new Bed(String.valueOf(i), "v"));
                            }
                            myRef.child(roomRef).updateChildren(bedHashMap);

                            updateBedCards(propertyRefKey,sharing);

                        });
                    }

                    @Override
                    public void btnDeleteClicked() {
                        getRef(position).removeValue();
                    }
                });
            });

            cardView.setOnClickListener(view -> {
                Navigation.findNavController(view).navigate(
                        RoomArrangementFragmentDirections.actionRoomArrangementFragmentToRoomDetailFragment(propertyRefKey)
                );
            });
        }
    }

    private void updateBedCards(String propertyRefKey , int vacantBed) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("bedCard").child(propertyRefKey).child("t");
        reference.setValue(ServerValue.increment(vacantBed));
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
        BedAdapter bedAdapter = new BedAdapter(options);
        recyclerView.setAdapter(bedAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));

        bedAdapter.startListening();
    }
}
