package com.example.hostel.BottomSheetDialog;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.example.hostel.databinding.LayoutAdharCardBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class AdharCardDialog extends BottomSheetDialog {
    public AdharCardDialog(@NonNull Context context) {
        super(context);

        LayoutAdharCardBinding binding = LayoutAdharCardBinding.inflate(LayoutInflater.from(context));
        setContentView(binding.getRoot());
        show();
    }
}
