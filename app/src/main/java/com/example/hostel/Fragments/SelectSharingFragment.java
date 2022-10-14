package com.example.hostel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.hostel.DTO.AddTenantDTO;
import com.example.hostel.Models.MonthlyCollection;
import com.example.hostel.Models.Property;
import com.example.hostel.Models.Tenant;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.Utils.Validation;
import com.example.hostel.databinding.FragmentSelectSharingBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SelectSharingFragment extends Fragment {

    FragmentSelectSharingBinding binding;

    SelectSharingFragmentArgs args;

    public SelectSharingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectSharingBinding.inflate(inflater, container, false);
        args = SelectSharingFragmentArgs.fromBundle(getArguments());
        AddTenantDTO addTenantDTO = args.getAddTenantDTO();

        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.tvBedNumber.setText(addTenantDTO.getBedNumber());
        binding.tvRoomNumber.setText(addTenantDTO.getRoomNumber());

        binding.tvSharingType.setText(UserUtils.getOccupancy(addTenantDTO.getSharingType()));

        binding.btnDone.setOnClickListener(view -> {

            Property property = addTenantDTO.getProperty();
            String propertyRefKey = addTenantDTO.getPropertyRefKey();


            String tenantName = addTenantDTO.getName();
            String mobileNumber = addTenantDTO.getPhoneNumber();
            String emailId = addTenantDTO.getEmailId();
            String dob = addTenantDTO.getDOB();
            Boolean gender = addTenantDTO.isGender();
            Boolean martialStatus = addTenantDTO.isMartialStatus();
            String occupation = addTenantDTO.getOccupation();

            int sharingType = addTenantDTO.getSharingType();
            String roomNumber = binding.tvRoomNumber.getText().toString();
            String bedNumber = binding.tvBedNumber.getText().toString();

            if (Validation.selectSharingPage(binding)) {
                binding.linearProgressIndicator.setVisibility(View.VISIBLE);

                String rent = binding.etRoomRent.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("tenants");
                String propertyName = property.getName() + " PG for " + property.getType().replace("PG", "").trim();
                Tenant tenant = new Tenant(tenantName, roomNumber, mobileNumber, sharingType, emailId, propertyName, propertyRefKey, dob, gender, martialStatus, occupation, bedNumber, rent);
                myRef.push().setValue(tenant).addOnSuccessListener(unused -> {
                    binding.linearProgressIndicator.setVisibility(View.GONE);
                    Navigation.findNavController(view).navigate(SelectSharingFragmentDirections.actionSelectSharingFragmentToCongratulationTenantFragment(addTenantDTO));
                });

                /*                ref: https://hostel-8a0f7-default-rtdb.firebaseio.com/bed/1664643038972/-NDMHznD125N5hhvmaNL*/

                DatabaseReference bedRef = FirebaseDatabase.getInstance().getReferenceFromUrl(addTenantDTO.getBedRef());
                bedRef.child("s").setValue("o");
                
                updateBedCards(addTenantDTO.getPropertyRefKey());

                Calendar cal = Calendar.getInstance();
                String month = new SimpleDateFormat("MMM yyyy", Locale.getDefault()).format(cal.getTime());
                DatabaseReference collection = database.getReference("collection");
                MonthlyCollection monthlyCollection = new MonthlyCollection(month, 0, Integer.parseInt(rent));

                Query check = FirebaseDatabase.getInstance().getReference().child("collection").child(UserUtils.phoneNumber()).child("list").orderByChild("month").equalTo(month);
                check.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                childSnapshot.getRef().child("totalPrice").setValue(ServerValue.increment(Integer.parseInt(rent)));
                            }
                        } else {
                            collection.child(UserUtils.phoneNumber()).child("list").push().setValue(monthlyCollection);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });

        return binding.getRoot();
    }

    private void updateBedCards(String propertyRefKey ) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("bedCard").child(propertyRefKey).child("o");
        reference.setValue(ServerValue.increment(1));
    }
}