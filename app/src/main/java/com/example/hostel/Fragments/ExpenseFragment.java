package com.example.hostel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.hostel.R;
import com.example.hostel.databinding.FragmentExpenseBinding;


public class ExpenseFragment extends Fragment {

    FragmentExpenseBinding binding;

    public ExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExpenseBinding.inflate(inflater, container, false);

        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.btnAddExpense.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_expenseFragment_to_addExpenseFragment);
        });
        return binding.getRoot();
    }
}