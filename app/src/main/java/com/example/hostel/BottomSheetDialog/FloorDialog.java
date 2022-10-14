package com.example.hostel.BottomSheetDialog;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hostel.Adapters.FloorDialogAdapter;
import com.example.hostel.Models.Floor;
import com.example.hostel.databinding.BottomSheetFloorBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FloorDialog extends BottomSheetDialog {

    OnFloorClickListener onFloorClickListener;
    BottomSheetFloorBinding binding;
    FloorDialogAdapter adapter;
    String propertyRefKey;

    public FloorDialog(@NonNull Context context) {
        super(context);
    }

    public FloorDialog(@NonNull Context context, String propertyRefKey, OnFloorClickListener onFloorClickListener) {
        super(context);
        this.propertyRefKey = propertyRefKey;
        this.onFloorClickListener = onFloorClickListener;
        loadDialog(context);
    }

    private void loadDialog(Context context) {
        binding = BottomSheetFloorBinding.inflate(LayoutInflater.from(context));
        setContentView(binding.getRoot());
        loadRecyclerView();
        show();
    }

    public FloorDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected FloorDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void loadRecyclerView() {
        Query query = FirebaseDatabase.getInstance().getReference().child("floor").child(propertyRefKey);
        FirebaseRecyclerOptions<Floor> options = new FirebaseRecyclerOptions.Builder<Floor>()
                .setQuery(query, snapshot -> {
                    return snapshot.getValue(Floor.class);
                }).build();
        adapter = new FloorDialogAdapter(options, onFloorClickListener,this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public interface OnFloorClickListener {
        void floorClicked(Floor floor, String ref);
    }

    @Override
    public void setOnShowListener(@Nullable OnShowListener listener) {
        super.setOnShowListener(listener);
    }

    @Override
    public void setOnCancelListener(@Nullable OnCancelListener listener) {
        super.setOnCancelListener(listener);
        this.cancel();
        if (adapter != null)
            adapter.stopListening();
    }
}
