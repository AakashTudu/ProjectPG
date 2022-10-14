package com.example.hostel.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hostel.Adapters.TenantsAdapter;
import com.example.hostel.CustomViews.CustomEditText;
import com.example.hostel.DTO.AddTenantDTO;
import com.example.hostel.Enums.FragmentEnum;
import com.example.hostel.Models.Property;
import com.example.hostel.Models.Tenant;
import com.example.hostel.R;
import com.example.hostel.Utils.Constants;
import com.example.hostel.databinding.FragmentTenantsBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class TenantsFragment extends Fragment {

    FragmentTenantsBinding binding;
    TenantsAdapter adapter;
    CustomEditText et_search;
    TenantsFragmentArgs args;
    AddTenantDTO addTenantDTO;
    public TenantsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTenantsBinding.inflate(inflater, container, false);
        et_search = binding.etSearch;
        args = TenantsFragmentArgs.fromBundle(getArguments());

        addTenantDTO = args.getAddTenantDTO();



        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.btnAddTenant.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(
                    TenantsFragmentDirections.actionTenantsFragmentToAddTenantFirstFragment(addTenantDTO)
            );
        });

        binding.etSearch.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (et_search.getRight() - et_search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (et_search.hasFocus()) {
                        et_search.clearFocus();
                    }
                    et_search.setText("");
                    et_search.hideKeyboard();
                    return true;
                }
            }
            return false;
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(binding.etSearch.getText().toString());
            }
        });


        loadRoomRecyclerView();

        return binding.getRoot();
    }

    private void loadRoomRecyclerView() {

        Query query;

        if (addTenantDTO.getFragmentEnum() == FragmentEnum.MANAGE) {
            query = FirebaseDatabase.getInstance().getReference().child("tenants");
        } else {
            query = FirebaseDatabase.getInstance().getReference().child("tenants").orderByChild("rf").equalTo(addTenantDTO.getPropertyRefKey());
        }

        FirebaseRecyclerOptions<Tenant> options = new FirebaseRecyclerOptions.Builder<Tenant>()
                .setQuery(query, dataSnapshot -> {
                    Log.d("dsfsdfsd", "dataSnapshot: " + dataSnapshot);
                    return dataSnapshot.getValue(Tenant.class);
                }).build();
        adapter = new TenantsAdapter(options);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        adapter.startListening();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (adapter != null)
            adapter.stopListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter != null)
            adapter.stopListening();
    }
}