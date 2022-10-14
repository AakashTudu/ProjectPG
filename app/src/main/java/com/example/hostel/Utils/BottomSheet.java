package com.example.hostel.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.hostel.Listeners.OnAddBedListener;
import com.example.hostel.Listeners.OnBedOptionClickListener;
import com.example.hostel.Listeners.OnBtnClickListener;
import com.example.hostel.Listeners.OnEditBedListener;
import com.example.hostel.Listeners.OnEditOptionClickListener;
import com.example.hostel.Listeners.OnFloorOptionClickListener;
import com.example.hostel.Listeners.OnPropertyOptionClickListener;
import com.example.hostel.Listeners.OnRoomOptionClickListener;
import com.example.hostel.R;
import com.example.hostel.databinding.LayoutBottomOccupancyBinding;
import com.example.hostel.databinding.LayoutBottomOccupationBinding;
import com.example.hostel.databinding.LayoutBottomRoomBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

public class BottomSheet {

    public static void showPropertyBottomDialog(Context context, OnPropertyOptionClickListener onPropertyOptionClickListener){

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.layout_bottom_properties);

        TextView tvEdit = bottomSheetDialog.findViewById(R.id.tvEditName);
        TextView tvEditRoomBed = bottomSheetDialog.findViewById(R.id.tvEditRoomBed);
        TextView tvActivate = bottomSheetDialog.findViewById(R.id.tvActivate);
        TextView tvDeactivate = bottomSheetDialog.findViewById(R.id.tvDeactivate);
        TextView tvDelete = bottomSheetDialog.findViewById(R.id.tvDelete);
        TextView tvAddFloor = bottomSheetDialog.findViewById(R.id.tvAddFloor);

        tvEdit.setOnClickListener(view -> {
            onPropertyOptionClickListener.btnEditClicked();
            bottomSheetDialog.cancel();
        });

        tvEditRoomBed.setOnClickListener(view -> {
            onPropertyOptionClickListener.btnEditBedRoomClicked();
            bottomSheetDialog.cancel();
        });

        tvAddFloor.setOnClickListener(view -> {
            onPropertyOptionClickListener.btnAddFloorClicked();
            bottomSheetDialog.cancel();
        });

        tvActivate.setOnClickListener(view -> {
            onPropertyOptionClickListener.btnActivateClicked();
            bottomSheetDialog.cancel();
        });

        tvDeactivate.setOnClickListener(view -> {
            onPropertyOptionClickListener.btnDeactivateClicked();
            bottomSheetDialog.cancel();
        });

        tvDelete.setOnClickListener(view -> {
            onPropertyOptionClickListener.btnDeleteClicked();
            bottomSheetDialog.cancel();
        });

        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
    }

    public static void showFloorOptionDialog(Context context, OnFloorOptionClickListener onFloorOptionClickListener) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.layout_bottom_floor);

        TextView tvEdit = bottomSheetDialog.findViewById(R.id.tvEditFloorName);
        TextView tvDelete = bottomSheetDialog.findViewById(R.id.tvDelete);

        tvEdit.setOnClickListener(view -> {
            onFloorOptionClickListener.btnEditClicked();
            bottomSheetDialog.cancel();
        });

        tvDelete.setOnClickListener(view -> {
            onFloorOptionClickListener.btnDeleteClicked();
            bottomSheetDialog.cancel();
        });

        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
    }

    public static void showBedOptionDialog(Context context, OnBedOptionClickListener onBedOptionClickListener) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.layout_bottom_bed);

        TextView tvEdit = bottomSheetDialog.findViewById(R.id.tvEditBedNumber);
        TextView tvDelete = bottomSheetDialog.findViewById(R.id.tvDelete);

        tvEdit.setOnClickListener(view -> {
            onBedOptionClickListener.btnEditClicked();
            bottomSheetDialog.cancel();
        });

        tvDelete.setOnClickListener(view -> {
            onBedOptionClickListener.btnDeleteClicked();
            bottomSheetDialog.cancel();
        });

        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
    }

    public static void showRoomOptionDialog(Context context, OnRoomOptionClickListener onRoomOptionClickListener) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        LayoutBottomRoomBinding binding = LayoutBottomRoomBinding.inflate(LayoutInflater.from(context));
        bottomSheetDialog.setContentView(binding.getRoot());

        binding.tvEditRoomNumber.setOnClickListener(view -> {
            onRoomOptionClickListener.btnEditClicked();
            bottomSheetDialog.cancel();
        });

        binding.tvAddBed.setOnClickListener(view -> {
            onRoomOptionClickListener.btnAddClicked();
            bottomSheetDialog.cancel();
        });

        binding.tvSharingType.setOnClickListener(view -> {
            onRoomOptionClickListener.btnSharingTypeClicked();
            bottomSheetDialog.cancel();
        });

        binding.tvDelete.setOnClickListener(view -> {
            onRoomOptionClickListener.btnDeleteClicked();
            bottomSheetDialog.cancel();
        });

        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
    }

    public static void showEditOptionDialog(Context context, OnEditOptionClickListener onEditOptionClickListener) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.layout_bottom_edit);

        TextView tvEdit = bottomSheetDialog.findViewById(R.id.tvEditFloor);

        tvEdit.setOnClickListener(view -> {
            onEditOptionClickListener.editBtnClicked();
            bottomSheetDialog.cancel();
        });


        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
    }

    public static void showOccupationOptionDialog(Context context, OnBtnClickListener onBtnClickListener) {

        LayoutBottomOccupationBinding binding = LayoutBottomOccupationBinding.inflate(LayoutInflater.from(context));
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(binding.getRoot());

        binding.tvProfessional.setOnClickListener(view -> {
            onBtnClickListener.btnClicked("Professional");
            bottomSheetDialog.cancel();
        });

        binding.tvStudent.setOnClickListener(view -> {
            onBtnClickListener.btnClicked("Student");
            bottomSheetDialog.cancel();
        });

        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
    }


    public static void showOccupancyOptionDialog(Context context, OnBtnClickListener onBtnClickListener) {
        LayoutBottomOccupancyBinding binding = LayoutBottomOccupancyBinding.inflate(LayoutInflater.from(context));
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(binding.getRoot());

        binding.tvSingleOccupancy.setOnClickListener(view -> {
            onBtnClickListener.btnClicked(binding.tvSingleOccupancy.getText().toString());
            bottomSheetDialog.cancel();
        });

        binding.tvDoubleOccupancy.setOnClickListener(view -> {
            onBtnClickListener.btnClicked(binding.tvDoubleOccupancy.getText().toString());
            bottomSheetDialog.cancel();
        });

        binding.tvTripleOccupancy.setOnClickListener(view -> {
            onBtnClickListener.btnClicked(binding.tvTripleOccupancy.getText().toString());
            bottomSheetDialog.cancel();
        });

        binding.tvFourOccupancy.setOnClickListener(view -> {
            onBtnClickListener.btnClicked(binding.tvFourOccupancy.getText().toString());
            bottomSheetDialog.cancel();
        });

        binding.tvFiveOccupancy.setOnClickListener(view -> {
            onBtnClickListener.btnClicked(binding.tvFiveOccupancy.getText().toString());
            bottomSheetDialog.cancel();
        });

        binding.tvSixOccupancy.setOnClickListener(view -> {
            onBtnClickListener.btnClicked(binding.tvSixOccupancy.getText().toString());
            bottomSheetDialog.cancel();
        });

        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
    }

    public static void addFloorBottomDialog(Context context, OnBtnClickListener onBtnClickListener){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_add_floor);
        AppCompatButton tvAdd = bottomSheetDialog.findViewById(R.id.btn_add_bottom_sheet);
        TextInputEditText etFloorName = bottomSheetDialog.findViewById(R.id.et_floor_name);
        tvAdd.setOnClickListener(view -> {
            String name = etFloorName.getText().toString();
            String s1 = String.valueOf(name.charAt(0));
            s1 = s1.toUpperCase() + name.substring(1);
            onBtnClickListener.btnClicked(s1);
            bottomSheetDialog.cancel();
        });
        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
    }

    public static void addRoomBottomDialog(Context context, OnBtnClickListener onBtnClickListener){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_add_room);
        AppCompatButton tvAdd = bottomSheetDialog.findViewById(R.id.btn_add_bottom_sheet);
        TextInputEditText etRoomName = bottomSheetDialog.findViewById(R.id.et_room_number);
        tvAdd.setOnClickListener(view -> {
            String name = etRoomName.getText().toString();
            onBtnClickListener.btnClicked(name);
            bottomSheetDialog.cancel();
        });
        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
    }

    public static void addBedBottomDialog(Context context, OnAddBedListener onAddBedListener){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_add_bed);
        AppCompatButton tvAdd = bottomSheetDialog.findViewById(R.id.btn_add_bottom_sheet);
        TextInputEditText etBedName = bottomSheetDialog.findViewById(R.id.etBedNumber);
        tvAdd.setOnClickListener(view -> {
            String name = etBedName.getText().toString();
            onAddBedListener.addBtnClicked(name);
            bottomSheetDialog.cancel();
        });
        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
    }

    public static void editBedBottomDialog(Context context, OnEditBedListener onBtnClickListener, String bedNumber) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_edit_bed);
        AppCompatButton tvAdd = bottomSheetDialog.findViewById(R.id.btn_edit_bottom_sheet);
        TextInputEditText etBedNumber = bottomSheetDialog.findViewById(R.id.etBedNumber);
        etBedNumber.setText(bedNumber);
        tvAdd.setOnClickListener(view -> {
            String number = etBedNumber.getText().toString();
            onBtnClickListener.editBtnClicked(number);
            bottomSheetDialog.cancel();
        });
        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
    }

    public static void editRoomBottomDialog(Context context, OnBtnClickListener onBtnClickListener, String roomNumber) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_edit_room);
        AppCompatButton btnEdit = bottomSheetDialog.findViewById(R.id.btn_edit_bottom_sheet);
        TextInputEditText etRoomName = bottomSheetDialog.findViewById(R.id.etRoomNumber);
        etRoomName.setText(roomNumber);
        btnEdit.setOnClickListener(view -> {
            String name = etRoomName.getText().toString();
            onBtnClickListener.btnClicked(name);
            bottomSheetDialog.cancel();
        });
        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
    }

    public static void editFloorBottomDialog(Context context, OnBtnClickListener onBtnClickListener, String floorName) {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_edit_floor);
        AppCompatButton btnEdit = bottomSheetDialog.findViewById(R.id.btn_edit_bottom_sheet);
        TextInputEditText etFloor = bottomSheetDialog.findViewById(R.id.etFloorName);
        etFloor.setText(floorName);
        btnEdit.setOnClickListener(view -> {
            String name = etFloor.getText().toString();
            onBtnClickListener.btnClicked(name);
            bottomSheetDialog.cancel();
        });
        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
    }
}
