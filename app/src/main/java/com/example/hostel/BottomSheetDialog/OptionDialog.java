package com.example.hostel.BottomSheetDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.hostel.databinding.BottomSheetOptionBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class OptionDialog extends BottomSheetDialog {

    public enum Option {
        ADD,
        EDIT,
        DELETE
    }

    BottomSheetOptionBinding binding;

    public OptionDialog(@NonNull Context context, OnOptionClickListener onOptionClickListener, Option... options) {
        super(context);

        binding = BottomSheetOptionBinding.inflate(LayoutInflater.from(context));

        init(options);

        binding.tvDelete.setOnClickListener(view -> {
            onOptionClickListener.clicked(Option.DELETE);
            dismiss();
        });

        setContentView(binding.getRoot());
        show();
    }

    private void init(Option... options) {
        for (Option option : options){
            switch (option){
                case EDIT:
                    binding.tvEdit.setVisibility(View.VISIBLE);
                    break;
                case DELETE:
                    binding.tvDelete.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    public interface OnOptionClickListener{
        void clicked(Option option);
    }
}
