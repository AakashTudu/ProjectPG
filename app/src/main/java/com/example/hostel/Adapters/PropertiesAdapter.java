package com.example.hostel.Adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Listeners.OnPropertyOptionClickListener;
import com.example.hostel.Models.Property;
import com.example.hostel.R;
import com.example.hostel.Utils.BottomSheet;
import com.example.hostel.Utils.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PropertiesAdapter extends FirebaseRecyclerAdapter<Property, PropertiesAdapter.ViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PropertiesAdapter(@NonNull FirebaseRecyclerOptions<Property> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_properties, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Property property) {
        holder.bind(position, property);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView iv_text, iv_type, iv_location;
        ImageView btn_more, ivBuilding;
        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_text = itemView.findViewById(R.id.tvPgName);
            iv_type = itemView.findViewById(R.id.tvType);
            iv_location = itemView.findViewById(R.id.tv_location);
            cardView = itemView.findViewById(R.id.cardView);
            btn_more = itemView.findViewById(R.id.btn_more);
            ivBuilding = itemView.findViewById(R.id.iv_building);

        }

        public void bind(int position, Property property) {

            iv_text.setText(property.getName());
            iv_type.setText(property.getType());
            iv_location.setText(property.getLocation() + ", " + property.getCity());

            Boolean isLive = Boolean.parseBoolean(property.getIsLive());

/*            if (isLive){
                ivBuilding.setBackgroundResource(R.drawable.ic_building_active);
            }else
                ivBuilding.setBackgroundResource(R.drawable.ic_building_deactivated);*/

            ivBuilding.setBackgroundResource(R.drawable.ic_building);

            cardView.setOnClickListener(view -> {
                String timeStampRef = getRef(position).getKey();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.propertyRef, timeStampRef);
                bundle.putString("position", String.valueOf(position + 1));
                bundle.putSerializable(Constants.property, property);
                Navigation.findNavController(view).navigate(R.id.action_propertyFragment_to_userPropertyFragment, bundle);
            });

            btn_more.setOnClickListener(view -> {
                BottomSheet.showPropertyBottomDialog(itemView.getContext(), new OnPropertyOptionClickListener() {
                    @Override
                    public void btnEditClicked() {

                    }

                    @Override
                    public void btnAddFloorClicked() {
                        String timeStampRef = getRef(position).getKey();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.propertyRef, timeStampRef);
                        Navigation.findNavController(view).navigate(R.id.action_propertyFragment_to_totalFloorFragment, bundle);
                    }

                    @Override
                    public void btnEditBedRoomClicked() {
                        String timeStampRef = getRef(position).getKey();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.propertyRef, timeStampRef);
                        Navigation.findNavController(view).navigate(R.id.action_propertyFragment_to_roomArrangementFragment, bundle);
                    }

                    @Override
                    public void btnActivateClicked() {

                    }

                    @Override
                    public void btnDeactivateClicked() {

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