package com.example.hostel.Adapters.paging;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hostel.Adapters.TenantsAdapter;
import com.example.hostel.Fragments.TenantSearchFragmentDirections;
import com.example.hostel.Fragments.TenantsFragmentDirections;
import com.example.hostel.Models.Tenant;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.LayoutTenantsBinding;
import com.shreyaspatil.firebase.recyclerpagination.DatabasePagingOptions;
import com.shreyaspatil.firebase.recyclerpagination.FirebaseRecyclerPagingAdapter;
import com.shreyaspatil.firebase.recyclerpagination.LoadingState;

public class TenantsPagingAdapter extends FirebaseRecyclerPagingAdapter<Tenant,TenantsPagingAdapter.ViewHolder> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private TenantsAdapter.Page page;

    public TenantsPagingAdapter(@NonNull DatabasePagingOptions<Tenant> options, SwipeRefreshLayout swipeRefreshLayout, TenantsAdapter.Page page) {
        super(options);
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.page = page;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Tenant tenant) {
        holder.bind(position, tenant);
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutTenantsBinding binding = LayoutTenantsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutTenantsBinding binding;

        public ViewHolder(@NonNull LayoutTenantsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position, Tenant tenant) {
            binding.tvTenantName.setText(tenant.getN());
            binding.tvRoomQuantity.setText(tenant.getR());
            binding.tvPgName.setText(tenant.getPn());
            binding.tvOccupancy.setText(tenant.getOccupancy());

            try {
                String ref = getRef(position).getKey();
                String code = ref.substring(ref.length() - 5);
                binding.tvCode.setText(code);

                switch (page){
                    case TENANT:
                        tenantCardCLickListener(tenant,code,position);
                        break;
                    case SEARCH_TENANT:
                        searchTenantCardListener(tenant, getRef(position).getKey());
                }

            }catch (Exception exception){
                exception.printStackTrace();
            }


            binding.ivCall.setOnClickListener(view -> {
                UserUtils.call(tenant.getP(),view.getContext());
            });
        }

        private void tenantCardCLickListener(Tenant tenant, String code, int position) {
            binding.cardView.setOnClickListener(view -> {
                Navigation.findNavController(view).navigate(
                        TenantsFragmentDirections.actionTenantsFragmentToTenantProfileFragment(tenant, code, getRef(position).getKey())
                );
            });
        }

        private void searchTenantCardListener(Tenant tenant, String tenantRefKey) {
            binding.cardView.setOnClickListener(view -> {
                Navigation.findNavController(view).navigate(
                        TenantSearchFragmentDirections.actionTenantSearchFragmentToRecordPaymentFragment(tenant, tenantRefKey)
                );
            });
        }
    }
}
