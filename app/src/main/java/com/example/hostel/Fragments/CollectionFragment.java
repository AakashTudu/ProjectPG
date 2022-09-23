package com.example.hostel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.Models.MonthlyCollection;
import com.example.hostel.R;
import com.example.hostel.Utils.Constants;
import com.example.hostel.databinding.FragmentCollectionBinding;

public class CollectionFragment extends Fragment {

    FragmentCollectionBinding binding;
    public CollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCollectionBinding.inflate(inflater, container, false);
        MonthlyCollection collection = (MonthlyCollection) getArguments().getSerializable(Constants.collection);

        binding.tvCollectedMoney.setText("Rs " + collection.getPaidPrice());
        binding.tvPendingMoney.setText("Rs " + collection.getPendingPrice());

        binding.lpiPaid.setProgress(collection.getIntPercentage());
        binding.lpiPending.setProgress(100-collection.getIntPercentage());
        binding.tvPaidPercentage.setText(collection.getPercentage());
        binding.tvPendingPercentage.setText(collection.getPendingPercentage());

        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });
        return binding.getRoot();
    }
}