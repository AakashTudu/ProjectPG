package com.example.hostel.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.hostel.BottomSheetDialog.Generic.GenericDialog;
import com.example.hostel.BottomSheetDialog.Generic.Row;
import com.example.hostel.DTO.AddExpenseDTO;
import com.example.hostel.Models.Expense;
import com.example.hostel.Models.Property;
import com.example.hostel.R;
import com.example.hostel.Utils.Validation;
import com.example.hostel.databinding.FragmentAddExpenseBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class AddExpenseFragment extends Fragment {

    FragmentAddExpenseBinding binding;
    AddExpenseFragmentArgs args;
    AddExpenseDTO addExpenseDTO;
    public AddExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false);
        args = AddExpenseFragmentArgs.fromBundle(getArguments());
        addExpenseDTO = args.getAddExpenseDTO();
        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });


        switch (addExpenseDTO.getOption()) {
            case ADD:
                addOption();
                break;
            case EDIT:
                editOption();
                break;
        }

        if (addExpenseDTO.getPropertyName() != null)
            binding.tvProperty.setText(addExpenseDTO.getPropertyName());


        binding.tvExpenseType.setOnClickListener(view -> {
            new GenericDialog(view.getContext(), row -> {
                binding.tvExpenseType.setText(row.getTitle());
            },
                    new Row("Vegetables Items", null),
                    new Row("Repairing", null),
                    new Row("Maintenance", null),
                    new Row("Bill Payment", null)
            );
        });

        return binding.getRoot();
    }

    private void editOption() {
        binding.tvProperty.setBackgroundResource(R.drawable.text_input_layout_grey_background);
        Expense expense = addExpenseDTO.getExpense();
        binding.etExpenseTitle.setText(expense.getT());
        binding.etAmount.setText(expense.getP());
        binding.etDescription.setText(expense.getR());
        binding.tvExpenseType.setText(expense.getExpenseType());
        int amountBefore = Integer.valueOf(binding.etAmount.getText().toString());

        DatabaseReference expenseRef = FirebaseDatabase.getInstance().getReferenceFromUrl(addExpenseDTO.getExpenseRef());
        binding.btnSave.setOnClickListener(view -> {
            String expenseTitle = binding.etExpenseTitle.getText().toString();
            String amount = binding.etAmount.getText().toString();
            String description = binding.etDescription.getText().toString();
            String expenseType = binding.tvExpenseType.getText().toString();

            expense.setExpenseTitle(expenseTitle);
            expense.setAmount(amount);
            expense.setExpenseType(expenseType);
            expense.setDescription(description);
/*            String d_r = expense.getD_r().split("_")[0] + "_" + addExpenseDTO.getPropertyRefKey();
            expense.setD_r(d_r);*/


            expenseRef.setValue(expense);

            String date = addExpenseDTO.getDate();
            String month = addExpenseDTO.getMonth();

            int amountAfter = Integer.parseInt(binding.etAmount.getText().toString());

            int netAmount = amountAfter - amountBefore;

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference monthRef = firebaseDatabase.getReference("expenseCard/" + addExpenseDTO.getPropertyRefKey() + "/month").child(month);
            DatabaseReference dayRef = firebaseDatabase.getReference("expenseCard/" + addExpenseDTO.getPropertyRefKey() + "/day").child(date);
            monthRef.setValue(ServerValue.increment(netAmount));
            dayRef.setValue(ServerValue.increment(netAmount));


            DatabaseReference monthRefGlobal = firebaseDatabase.getReference("expenseCard/global/month").child(month);
            DatabaseReference dayRefGlobal = firebaseDatabase.getReference("expenseCard/global/day").child(date);
            monthRefGlobal.setValue(ServerValue.increment(netAmount));
            dayRefGlobal.setValue(ServerValue.increment(netAmount));

            Navigation.findNavController(view).popBackStack();
        });
    }

    private void addOption() {
        binding.tvProperty.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(
                    AddExpenseFragmentDirections.actionAddExpenseFragmentToPropertySearchFragment(addExpenseDTO)
            );
        });

        binding.btnSave.setOnClickListener(view -> {

            if (Validation.addExpensePage(binding)) {
                String title = binding.etExpenseTitle.getText().toString();
                String amount = binding.etAmount.getText().toString();
                String description = binding.etDescription.getText().toString();
                String expenseType = binding.tvExpenseType.getText().toString();

                String date = addExpenseDTO.getDate();
                String month = addExpenseDTO.getMonth();

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference reference = firebaseDatabase.getReference("expense");
                String propertyRefKey = addExpenseDTO.getPropertyRefKey();
                Expense expense = new Expense(title, amount, description, date + "_" + propertyRefKey, expenseType);
                reference.push().setValue(expense);


                DatabaseReference monthRef = firebaseDatabase.getReference("expenseCard/" + addExpenseDTO.getPropertyRefKey() + "/month").child(month);
                DatabaseReference dayRef = firebaseDatabase.getReference("expenseCard/" + addExpenseDTO.getPropertyRefKey() + "/day").child(date);
                monthRef.setValue(ServerValue.increment(Integer.parseInt(amount)));
                dayRef.setValue(ServerValue.increment(Integer.parseInt(amount)));

                DatabaseReference monthRefGlobal = firebaseDatabase.getReference("expenseCard/global/month").child(month);
                DatabaseReference dayRefGlobal = firebaseDatabase.getReference("expenseCard/global/day").child(date);
                monthRefGlobal.setValue(ServerValue.increment(Integer.parseInt(amount)));
                dayRefGlobal.setValue(ServerValue.increment(Integer.parseInt(amount)));

                Navigation.findNavController(view).popBackStack();
            }
        });
    }
}