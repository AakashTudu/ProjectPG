package com.example.hostel.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.hostel.R;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.FragmentAddPropertyBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class AddPropertyFragment extends Fragment {

    FragmentAddPropertyBinding binding;

    public AddPropertyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddPropertyBinding.inflate(inflater, container, false);
        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });


        AtomicReference<String> type = new AtomicReference<>("Men's PG");

        binding.btnMenPG.setOnClickListener(view1 -> {
            type.set("Men's PG");
            binding.btnLadiesPG.setBackgroundResource(R.drawable.text_input_layout);
            binding.btnMenPG.setBackgroundResource(R.drawable.outlined_border_10_dp_grey);
            binding.btnCoedPG.setBackgroundResource(R.drawable.text_input_layout);
        });


        binding.btnLadiesPG.setOnClickListener(view1 -> {
            type.set("Ladies PG");
            binding.btnLadiesPG.setBackgroundResource(R.drawable.outlined_border_10_dp_grey);
            binding.btnMenPG.setBackgroundResource(R.drawable.text_input_layout);
            binding.btnCoedPG.setBackgroundResource(R.drawable.text_input_layout);
        });

        binding.btnCoedPG.setOnClickListener(view1 -> {
            type.set("Coed PG");
            binding.btnLadiesPG.setBackgroundResource(R.drawable.text_input_layout);
            binding.btnMenPG.setBackgroundResource(R.drawable.text_input_layout);
            binding.btnCoedPG.setBackgroundResource(R.drawable.outlined_border_10_dp_grey);
        });

        binding.btnContinue.setOnClickListener(view -> {

            String name = binding.etPropertyName.getText().toString();
            String city = binding.etCityName.getText().toString();
            String location = binding.etPropertyLocation.getText().toString();


            if (name.equals("")){
                Toast.makeText(getContext(), "Please enter property name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (city.equals("")){
                Toast.makeText(getContext(), "Please enter city name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (location.equals("")){
                Toast.makeText(getContext(), "Plwase enter properrty location", Toast.LENGTH_SHORT).show();
                return;
            }
            binding.linearProgressIndicator.setVisibility(View.VISIBLE);
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("type", type.toString());
            map.put("city", city);
            map.put("location", location);
            map.put("isLive", false);
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            String timeStampRef = String.valueOf(System.currentTimeMillis());
            DatabaseReference userRef = rootRef.child("properties").child(UserUtils.phoneNumber()).child(timeStampRef);
            userRef.setValue(map).addOnSuccessListener(unused -> {
                binding.linearProgressIndicator.setVisibility(View.GONE);
                Bundle bundle = new Bundle();
                bundle.putString("timeStampRef",timeStampRef);
                bundle.putString("fragment","AddPropertyFragment");
                Navigation.findNavController(view).navigate(R.id.action_addPropertyFragment_to_totalFloorFragment, bundle);
            });
        });


        return binding.getRoot();
    }
}