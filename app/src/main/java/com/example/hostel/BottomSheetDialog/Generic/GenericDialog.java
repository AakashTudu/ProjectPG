package com.example.hostel.BottomSheetDialog.Generic;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hostel.databinding.BottomSheetGenericDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class GenericDialog extends BottomSheetDialog {

    Row[] rows;
    OnRowClickedListener onRowClickedListener;

    public GenericDialog(@NonNull Context context, OnRowClickedListener onRowClickedListener, Row... rows) {
        super(context);
        this.onRowClickedListener = onRowClickedListener;
        this.rows = rows;
        init(context);
    }

    GenericDialogAdapter adapter;
    private void init(Context context) {
        BottomSheetGenericDialogBinding binding = BottomSheetGenericDialogBinding.inflate(LayoutInflater.from(context));

        adapter = new GenericDialogAdapter(rows,onRowClickedListener,this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setContentView(binding.getRoot());
        show();
    }

    public interface OnRowClickedListener {
        void rowClicked(Row row);
    }

}
