package com.example.hostel.Adapters.paging;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hostel.Adapters.PropertiesAdapter;
import com.example.hostel.DTO.AddExpenseDTO;
import com.example.hostel.DTO.AddTenantDTO;
import com.example.hostel.Enums.FragmentEnum;
import com.example.hostel.Fragments.PropertyFragmentDirections;
import com.example.hostel.Fragments.PropertySearchFragmentDirections;
import com.example.hostel.Listeners.OnPropertyOptionClickListener;
import com.example.hostel.Models.Property;
import com.example.hostel.R;
import com.example.hostel.Utils.BottomSheet;
import com.example.hostel.Utils.Constants;
import com.example.hostel.databinding.LayoutPropertiesBinding;
import com.google.firebase.database.DatabaseError;
import com.shreyaspatil.firebase.recyclerpagination.DatabasePagingOptions;
import com.shreyaspatil.firebase.recyclerpagination.FirebaseRecyclerPagingAdapter;
import com.shreyaspatil.firebase.recyclerpagination.LoadingState;

public class PropertyPagingAdapter extends FirebaseRecyclerPagingAdapter<Property, PropertyPagingAdapter.ViewHolder> {

    SwipeRefreshLayout swipeRefreshLayout;
    AddExpenseDTO addExpenseDTO;
    PropertiesAdapter.Page page;


    public PropertyPagingAdapter(@NonNull DatabasePagingOptions<Property> options, SwipeRefreshLayout swipeRefreshLayout,PropertiesAdapter.Page page) {
        super(options);
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.page = page;
    }

    public PropertyPagingAdapter(@NonNull DatabasePagingOptions<Property> options, SwipeRefreshLayout swipeRefreshLayout, AddExpenseDTO addExpenseDTO, PropertiesAdapter.Page page) {
        super(options);
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.addExpenseDTO = addExpenseDTO;
        this.page = page;
    }

    @Override
    protected void onBindViewHolder(@NonNull PropertyPagingAdapter.ViewHolder viewHolder, int position, @NonNull Property property) {
        viewHolder.bind(position,property);
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        switch (state) {
            case LOADING_INITIAL:
            case LOADING_MORE:
                // Do your loading animation
                swipeRefreshLayout.setRefreshing(true);
                break;

            case LOADED:
            case FINISHED:
                swipeRefreshLayout.setRefreshing(false);
                break;

            case ERROR:
                //Reached end of Data set
                // Stop Animation
                swipeRefreshLayout.setRefreshing(false);
                break;
            //retry();
        }
    }


    @NonNull
    @Override
    public PropertyPagingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutPropertiesBinding binding = LayoutPropertiesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
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
            binding.ivBuilding.setBackgroundResource(R.drawable.ic_building);

            String ref = getRef(position).getKey();
            String code = ref.substring(ref.length() - 5);
            binding.tvCode.setText(code);

            switch (page){
                case PROPERTY:
                    initClickListeners(position,property);
                    break;
                case SEARCH_PROPERTY:
                    initSearchClickListener(position, property);
            }

        }

        private void initClickListeners(int position, Property property) {


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
        }

        private void initSearchClickListener(int position, Property property) {
            binding.btnMore.setVisibility(View.GONE);

            binding.cardView.setOnClickListener(view -> {
                addExpenseDTO.setPropertyRefKey(getRef(position).getKey());
                addExpenseDTO.setProperty(property);
                addExpenseDTO.setPropertyName(property.getName().split(" ")[0] + " PG for " + property.getType().replace("PG","").trim());
                Navigation.findNavController(view).navigate(
                        PropertySearchFragmentDirections.actionPropertySearchFragmentToAddExpenseFragment(addExpenseDTO)
                );
            });
        }
    }
}
