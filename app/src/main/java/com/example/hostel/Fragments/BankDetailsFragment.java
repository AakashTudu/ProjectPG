package com.example.hostel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.Adapters.BankAdapter;
import com.example.hostel.Adapters.PropertiesAdapter;
import com.example.hostel.BottomSheetDialog.OptionDialog;
import com.example.hostel.Models.Bank;
import com.example.hostel.Models.Property;
import com.example.hostel.R;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.FragmentBankDetailsBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class BankDetailsFragment extends Fragment {


    FragmentBankDetailsBinding binding;

    public BankDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBankDetailsBinding.inflate(inflater,container,false);


        loadRecyclerView();

        binding.btnAddBank.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(
                    BankDetailsFragmentDirections.actionBankDetailsFragmentToAddBankFragment(OptionDialog.Option.ADD, null,"")
            );
        });

        return binding.getRoot();
    }

    private void loadRecyclerView() {
        Query query = FirebaseDatabase.getInstance().getReference().child("bank");
        FirebaseRecyclerOptions<Bank> options = new FirebaseRecyclerOptions.Builder<Bank>()
                .setQuery(query, snapshot -> {
                    return snapshot.getValue(Bank.class);
                }).build();
        BankAdapter adapter = new BankAdapter(options);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.startListening();
    }
}