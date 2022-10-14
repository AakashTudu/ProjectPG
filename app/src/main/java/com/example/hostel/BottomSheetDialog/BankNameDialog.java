package com.example.hostel.BottomSheetDialog;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hostel.Adapters.BankNameAdapter;
import com.example.hostel.databinding.BottomSheetBankNameBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BankNameDialog extends BottomSheetDialog {

    public BankNameDialog(@NonNull Context context, BankNameAdapter.OnBankItemClickListener onBankItemClickListener) {
        super(context);
        BottomSheetBankNameBinding binding = BottomSheetBankNameBinding.inflate(LayoutInflater.from(context));
        BankNameAdapter bankNameAdapter = new BankNameAdapter(context,onBankItemClickListener,this);

        binding.recyclerView.setAdapter(bankNameAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));

        setContentView(binding.getRoot());
        show();
    }
}
