package com.example.hostel.BottomSheetDialog.Generic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.databinding.LayoutGenericRowBinding;


public class GenericDialogAdapter extends RecyclerView.Adapter<GenericDialogAdapter.ViewHolder> {

    Row[] rows;
    GenericDialog.OnRowClickedListener onRowClickedListener;
    GenericDialog genericDialog;

    public GenericDialogAdapter(Row[] rows, GenericDialog.OnRowClickedListener onRowClickedListener, GenericDialog genericDialog) {
        this.rows = rows;
        this.onRowClickedListener = onRowClickedListener;
        this.genericDialog = genericDialog;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutGenericRowBinding binding = LayoutGenericRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return rows.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutGenericRowBinding binding;

        public ViewHolder(@NonNull LayoutGenericRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            Row row = rows[position];
            binding.tvRow.setText(row.getTitle());
            if (row.getIcon() != null)
                binding.ivRowIcon.setBackgroundResource(row.getIcon());
            else
                binding.ivRowIcon.setVisibility(View.GONE);

            binding.getRoot().setOnClickListener(view -> {
                genericDialog.dismiss();
                if (onRowClickedListener != null)
                    onRowClickedListener.rowClicked(row);
            });
        }
    }
}