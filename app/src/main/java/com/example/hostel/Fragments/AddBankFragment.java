package com.example.hostel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.hostel.Adapters.BankNameAdapter;
import com.example.hostel.BottomSheetDialog.BankNameDialog;
import com.example.hostel.Models.Bank;
import com.example.hostel.Models.BankName;
import com.example.hostel.Utils.BottomSheet;
import com.example.hostel.Utils.Validation;
import com.example.hostel.databinding.FragmentAddBankBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddBankFragment extends Fragment {

    FragmentAddBankBinding binding;
    AddBankFragmentArgs args;

    public AddBankFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddBankBinding.inflate(inflater, container, false);
        args = AddBankFragmentArgs.fromBundle(getArguments());

        binding.etSelectBank.setOnClickListener(view -> {
            new BankNameDialog(getContext(), bankName -> {
                binding.etSelectBank.setText(bankName.getName());
            });
        });

        switch (args.getOption()){
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
        Bank bank = args.getBank();
        binding.etSelectBank.setText(bank.getBankName());
        binding.etHolderName.setText(bank.getHolderName());
        binding.etAccountNumber.setText(bank.getAccountNumber());
        binding.etIfscCode.setText(bank.getIfscCode());


        DatabaseReference bedRef = FirebaseDatabase.getInstance().getReferenceFromUrl(args.getBankRef());
        binding.btnSave.setOnClickListener(view -> {

            if (Validation.addBankPage(binding)) {
                String bankName = binding.etSelectBank.getText().toString();
                String holderName = binding.etHolderName.getText().toString();
                String accountNumber = binding.etAccountNumber.getText().toString();
                String ifscCode = binding.etIfscCode.getText().toString();

                bank.setBankName(bankName);
                bank.setHolderName(holderName);
                bank.setAccountNumber(accountNumber);
                bank.setIfscCode(ifscCode);

                bedRef.setValue(bank);
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });
    }

    private void addOption() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference bankRef = database.getReference("bank");

        binding.btnSave.setOnClickListener(view -> {
            if (Validation.addBankPage(binding)) {
                String bankName = binding.etSelectBank.getText().toString();
                String holderName = binding.etHolderName.getText().toString();
                String accountNumber = binding.etAccountNumber.getText().toString();
                String ifscCode = binding.etIfscCode.getText().toString();
                Bank bank = new Bank(bankName, holderName, accountNumber, ifscCode);
                bankRef.push().setValue(bank);
                Navigation.findNavController(view).popBackStack();
            }
        });
    }
}