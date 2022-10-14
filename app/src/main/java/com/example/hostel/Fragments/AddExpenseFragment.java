package com.example.hostel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.hostel.Models.Expense;
import com.example.hostel.databinding.FragmentAddExpenseBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class AddExpenseFragment extends Fragment {

    FragmentAddExpenseBinding binding;
    AddExpenseFragmentArgs args;
    public AddExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false);
        args = AddExpenseFragmentArgs.fromBundle(getArguments());
        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        switch (args.getOption()) {
            case ADD:
                addOption();
                break;
            case EDIT:
                editOption();
                break;
        }


        return binding.getRoot();
    }

    private void editOption() {
        Expense expense = args.getExpense();
        binding.etExpenseTitle.setText(expense.getT());
        binding.etAmount.setText(expense.getP());
        if (expense.getM()) binding.rbShopping.setChecked(true);
        else binding.rbTransaction.setChecked(true);
        binding.etDescription.setText(expense.getR());

        int amountBefore = Integer.valueOf(binding.etAmount.getText().toString());

        DatabaseReference expenseRef = FirebaseDatabase.getInstance().getReferenceFromUrl(args.getExpenseRef());
        binding.btnSave.setOnClickListener(view -> {
            String expenseTitle = binding.etExpenseTitle.getText().toString();
            String amount = binding.etAmount.getText().toString();
            boolean isShoppingChecked = binding.radioGroup.getCheckedRadioButtonId() == binding.rbShopping.getId();
            String description = binding.etDescription.getText().toString();

            expense.setExpenseTitle(expenseTitle);
            expense.setAmount(amount);
            expense.setShoppingChecked(isShoppingChecked);
            expense.setDescription(description);

            expenseRef.setValue(expense);

            String date = args.getDate();
            String month = args.getMonth();

            int amountAfter = Integer.parseInt(binding.etAmount.getText().toString());

            int netAmount = amountAfter -amountBefore;

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference monthRef = firebaseDatabase.getReference("expenseCard/month").child(month);
            DatabaseReference dayRef = firebaseDatabase.getReference("expenseCard/day").child(date);
            monthRef.setValue(ServerValue.increment(netAmount));
            dayRef.setValue(ServerValue.increment(netAmount));

            Navigation.findNavController(view).popBackStack();
        });
    }

    private void addOption() {
        binding.btnSave.setOnClickListener(view -> {
            String title = binding.etExpenseTitle.getText().toString();
            String amount = binding.etAmount.getText().toString();
            String description = binding.etDescription.getText().toString();

            String date = args.getDate();
            String month = args.getMonth();

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference reference = firebaseDatabase.getReference("expense");
            Expense expense = new Expense(title, amount, description, date, binding.radioGroup.getCheckedRadioButtonId() == binding.rbShopping.getId());
            reference.push().setValue(expense);

            DatabaseReference monthRef = firebaseDatabase.getReference("expenseCard/month").child(month);
            DatabaseReference dayRef = firebaseDatabase.getReference("expenseCard/day").child(date);
            monthRef.setValue(ServerValue.increment(Integer.parseInt(amount)));
            dayRef.setValue(ServerValue.increment(Integer.parseInt(amount)));

            Navigation.findNavController(view).popBackStack();
        });
    }
}