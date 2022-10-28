package com.example.hostel.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.hostel.BottomSheetDialog.BusinessDetailDialog;
import com.example.hostel.BottomSheetDialog.ContactUsDialog;
import com.example.hostel.Models.User;
import com.example.hostel.R;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        binding.btnLogout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
        });

        binding.shimmerViewContainer.startShimmer();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userNameRef = rootRef.child("users").child(UserUtils.phoneNumber());
        userNameRef.keepSynced(true);
        userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Log.d("sdgsdfsdf", "user: " + user);
                binding.tvName.setText(user.getName());
                binding.tvCity.setText(user.getCity());
                binding.tvEmail.setText(user.getEmail());

                binding.tvName.setBackgroundColor(Color.WHITE);
                binding.tvCity.setBackgroundColor(Color.WHITE);
                binding.tvEmail.setBackgroundColor(Color.WHITE);

                binding.shimmerViewContainer.hideShimmer();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.tvContactUs.setOnClickListener(view -> {
            new ContactUsDialog(view.getContext());
        });

        binding.tvBandDetail.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(
                    ProfileFragmentDirections.actionProfileFragmentToBankDetailsFragment()
            );
        });

        binding.tvBusinessDetail.setOnClickListener(view -> {
            //new BusinessDetailDialog(view.getContext());
            Navigation.findNavController(view).navigate(
                    ProfileFragmentDirections.actionProfileFragmentToBusinessDetailsFragment()
            );
        });

        return binding.getRoot();
    }
}