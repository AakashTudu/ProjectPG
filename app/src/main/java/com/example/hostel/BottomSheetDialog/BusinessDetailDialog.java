package com.example.hostel.BottomSheetDialog;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.example.hostel.databinding.LayoutBusinessDetailBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BusinessDetailDialog extends BottomSheetDialog {
    public BusinessDetailDialog(@NonNull Context context) {
        super(context);
        LayoutBusinessDetailBinding binding = LayoutBusinessDetailBinding.inflate(LayoutInflater.from(context));
        setContentView(binding.getRoot());
        show();
    }
}
