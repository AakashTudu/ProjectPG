package com.example.hostel.BottomSheetDialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.hostel.databinding.BottomSheetContactUsBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ContactUsDialog extends BottomSheetDialog {
    public ContactUsDialog(@NonNull Context context) {
        super(context);

        BottomSheetContactUsBinding binding = BottomSheetContactUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        show();
    }
}
