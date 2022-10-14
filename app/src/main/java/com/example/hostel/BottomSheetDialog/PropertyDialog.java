package com.example.hostel.BottomSheetDialog;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.example.hostel.Models.Tenant;
import com.example.hostel.databinding.BottomSheetPropertyBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class PropertyDialog extends BottomSheetDialog {

    BottomSheetPropertyBinding binding;

    public PropertyDialog(@NonNull Context context, Tenant tenant) {
        super(context);
        binding = BottomSheetPropertyBinding.inflate(LayoutInflater.from(context));
        binding.tvPgName.setText(tenant.getPn());
        String pgType = tenant.getPn().substring(tenant.getPn().lastIndexOf("for")).replace("for","").trim();
        binding.tvPgType.setText(pgType);
        setContentView(binding.getRoot());
        show();
    }

    public PropertyDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected PropertyDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
