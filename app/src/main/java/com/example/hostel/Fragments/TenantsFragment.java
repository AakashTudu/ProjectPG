package com.example.hostel.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hostel.Adapters.TenantsAdapter;
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
    String ref;
    TenantsAdapter adapter;

    public TenantsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTenantsBinding.inflate(inflater, container, false);

        if (getArguments() != null)
            ref = getArguments().getString(Constants.propertyRef);



        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.btnAddTenant.setOnClickListener(view -> {
/*            HashMap<String, Object> map = new HashMap<>();
            map.put("n","Suraj Sharma");
            map.put("r","201");
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            String timeStampRef = String.valueOf(System.currentTimeMillis());
            DatabaseReference userRef = rootRef.child("tenants").child(ref).child(timeStampRef);
            userRef.setValue(map);*/


            Bundle bundle = new Bundle();
            bundle.putString(Constants.propertyRef, ref);
            if (getArguments()!=null){
                Property property = (Property) getArguments().getSerializable(Constants.property);
                bundle.putSerializable(Constants.property, property);
            }
            Navigation.findNavController(view).navigate(R.id.action_tenantsFragment_to_addTenantFragment, bundle);


/*            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("tenants");
            Tenant tenant = new Tenant("Anurag sagar", "101", ref);
            myRef.push().setValue(tenant);*/
        });

        loadRoomRecyclerView();

        return binding.getRoot();
    }

    private void loadRoomRecyclerView() {

        Query query;

        if (ref == null) {
            query = FirebaseDatabase.getInstance().getReference().child("tenants");
        } else {
            query = FirebaseDatabase.getInstance().getReference().child("tenants").orderByChild("rf").equalTo(ref);
        }

        FirebaseRecyclerOptions<Tenant> options = new FirebaseRecyclerOptions.Builder<Tenant>()
                .setQuery(query, dataSnapshot -> {
                    Log.d("sfsdfsdf", "snapshot: " + dataSnapshot);
                    String tenantName = dataSnapshot.child("n").getValue().toString();
                    String tenantRoomNumber = dataSnapshot.child("r").getValue().toString();
                    String mobileNumber = dataSnapshot.child("p").getValue().toString();
                    String occupancy = dataSnapshot.child("o").getValue().toString();
                    String emailId = dataSnapshot.child("e").getValue().toString();
                    String propertyName = dataSnapshot.child("pn").getValue().toString();
                    String reference = dataSnapshot.child("rf").getValue().toString();
                    return new Tenant(
                            tenantName, tenantRoomNumber,mobileNumber, occupancy, emailId, propertyName, reference
                    );
                }).build();
        adapter = new TenantsAdapter(options);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        adapter.startListening();

    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.stopListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }
}