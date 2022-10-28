package com.example.hostel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.hostel.BottomSheetDialog.Generic.GenericDialog;
import com.example.hostel.BottomSheetDialog.Generic.Row;
import com.example.hostel.CustomViews.DatePickerFragment;
import com.example.hostel.Models.Pending;
import com.example.hostel.Models.Transaction;
import com.example.hostel.R;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.Utils.Validation;
import com.example.hostel.databinding.FragmentRecordPaymentBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordPaymentFragment extends Fragment {

    FragmentRecordPaymentBinding binding;
    RecordPaymentFragmentArgs args;

    public RecordPaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRecordPaymentBinding.inflate(inflater, container, false);
        args = RecordPaymentFragmentArgs.fromBundle(getArguments());
        binding.btnBack.setOnClickListener(view -> {
            //Navigation.findNavController(view).popBackStack(R.id.action_recordPaymentFragment_to_dashboardFragment);
            Navigation.findNavController(view).popBackStack();
        });

        binding.tvTenantName.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(RecordPaymentFragmentDirections.actionRecordPaymentFragmentToTenantSearchFragment());
        });

        binding.tvDateOfPayment.setOnClickListener(view -> {
            showDatePickerDialog();
        });

        binding.tvPaymentMode.setOnClickListener(view -> {
            new GenericDialog(view.getContext(), row -> {
                binding.tvPaymentMode.setText(row.getTitle());
            },
                    new Row("Cash", R.drawable.ic_cash),
                    new Row("UPI", R.drawable.ic_bhim_upi)
            );
        });

        binding.tvPaymentType.setOnClickListener(view -> {
            new GenericDialog(view.getContext(), row -> {
                binding.tvPaymentType.setText(row.getTitle());
            },
                    new Row("Rent", R.drawable.ic_transaction),
                    new Row("Security Deposit", R.drawable.ic_transaction)
            );
        });


        binding.btnContinue.setOnClickListener(view -> {
            if (Validation.recordPaymentPage(binding)){

                String tenantRef = args.getTenantRefKey();
                String date = binding.tvDateOfPayment.getText().toString();
                String paymentMode = binding.tvPaymentMode.getText().toString();
                String paymentType = binding.tvPaymentType.getText().toString();
                String amountPaid = binding.etAmountPaid.getText().toString();
                String receivedBy = binding.etReceivedBy.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();



                if (!binding.etPendingAmount.getText().toString().equals("")){
                    DatabaseReference pendingAmountRef = database.getReference("pendingAmount");
                    Pending pending = new Pending(binding.etPendingAmount.getText().toString(), date, tenantRef,paymentType);
                    pendingAmountRef.push().setValue(pending);

                    DatabaseReference reference = database.getReference("transactionCard/" + UserUtils.phoneNumber()).child("pending");
                    reference.setValue(ServerValue.increment(Integer.parseInt(binding.etPendingAmount.getText().toString())));
                }

                if (!binding.etAmountPaid.getText().toString().equals("")){
                    Transaction transaction = new Transaction(tenantRef, date, paymentMode, paymentType, amountPaid, receivedBy);
                    DatabaseReference reference = database.getReference("transaction");
                    reference.push().setValue(transaction);
                    updateTransactionCard(amountPaid);
                }

                Navigation.findNavController(view).popBackStack();
            }
        });

        if (args.getTenant() != null) {
            binding.tvTenantName.setText(args.getTenant().getN());
        }
        return binding.getRoot();
    }

    private void updateTransactionCard(String amountPaid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("transactionCard/"+UserUtils.phoneNumber()).child("received");
        int amount = Integer.parseInt(amountPaid);
        reference.setValue(ServerValue.increment(amount));
    }

    public void showDatePickerDialog() {
        DatePickerFragment dateDialog = new DatePickerFragment();
        dateDialog.setOnDateSetListener((datePicker, year, month, day) -> {

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-");
            Date d = new Date(year, month, day);
            String date = dateFormatter.format(d);
            binding.tvDateOfPayment.setText(date + year);
        });
        dateDialog.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
}