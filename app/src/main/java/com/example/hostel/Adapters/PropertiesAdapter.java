package com.example.hostel.Adapters;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.DTO.AddTenantDTO;
import com.example.hostel.Enums.FragmentEnum;
import com.example.hostel.FilterableAdapter.FirebaseRecyclerFilterableAdapter;
import com.example.hostel.Fragments.PropertyFragmentDirections;
import com.example.hostel.Listeners.OnPropertyOptionClickListener;
import com.example.hostel.Models.Property;
import com.example.hostel.R;
import com.example.hostel.Utils.BottomSheet;
import com.example.hostel.Utils.Constants;
import com.example.hostel.databinding.LayoutPropertiesBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PropertiesAdapter extends FirebaseRecyclerFilterableAdapter<Property, PropertiesAdapter.ViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PropertiesAdapter(@NonNull FirebaseRecyclerOptions<Property> options) {
        super(options, true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutPropertiesBinding binding = LayoutPropertiesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Property property) {
        holder.bind(position, property);
    }


    @Override
    protected boolean filterCondition(Property property, String queryString) {
        return property.getName().toLowerCase().contains(queryString) || property.getName().toLowerCase().contains(queryString);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutPropertiesBinding binding;

        public ViewHolder(@NonNull LayoutPropertiesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position, Property property) {

            binding.tvPgName.setText(property.getName());
            binding.tvType.setText(property.getType());
            binding.tvLocation.setText(property.getLocation() + ", " + property.getCity());

            Boolean isLive = Boolean.parseBoolean(property.getIsLive());

/*            if (isLive){
                ivBuilding.setBackgroundResource(R.drawable.ic_building_active);
            }else
                ivBuilding.setBackgroundResource(R.drawable.ic_building_deactivated);*/

            binding.ivBuilding.setBackgroundResource(R.drawable.ic_building);

            String ref = getRef(position).getKey();
            String code = ref.substring(ref.length() - 5);
            binding.tvCode.setText(code);

            binding.cardView.setOnClickListener(view -> {
/*                String timeStampRef = getRef(position).getKey();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.propertyRef, timeStampRef);
                bundle.putString("position", String.valueOf(position + 1));
                bundle.putSerializable(Constants.property, property);
                Navigation.findNavController(view).navigate(R.id.action_propertyFragment_to_userPropertyFragment, bundle);*/

                AddTenantDTO addTenantDTO = new AddTenantDTO();
                addTenantDTO.setPropertyRefKey(getRef(position).getKey());
                addTenantDTO.setProperty(property);
                addTenantDTO.setFragmentEnum(FragmentEnum.USER_PROPERTY);

                Navigation.findNavController(view).navigate(
                        PropertyFragmentDirections.actionPropertyFragmentToUserPropertyFragment(addTenantDTO)
                );

            });

            binding.btnMore.setOnClickListener(view -> {
                BottomSheet.showPropertyBottomDialog(itemView.getContext(), new OnPropertyOptionClickListener() {
                    @Override
                    public void btnEditClicked() {

                    }

                    @Override
                    public void btnAddFloorClicked() {
                        String timeStampRef = getRef(position).getKey();
                        Log.d("sdfsdfsd", "PropertiesAdapter: " + timeStampRef);
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.propertyRef, timeStampRef);
                        bundle.putString(Constants.fragment, "PropertyFragment");
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