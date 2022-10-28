package com.example.hostel.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hostel.Adapters.ExpenseAdapter;
import com.example.hostel.BottomSheetDialog.OptionDialog;
import com.example.hostel.CustomViews.DatePickerFragment;
import com.example.hostel.DTO.AddExpenseDTO;
import com.example.hostel.Models.Expense;
import com.example.hostel.Utils.Constants;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.FragmentExpenseBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ExpenseFragment extends Fragment {

    FragmentExpenseBinding binding;
    String currentDate;
    String month;
    ExpenseFragmentArgs args;
    AddExpenseDTO addExpenseDTO;

    public ExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExpenseBinding.inflate(inflater, container, false);
        args = ExpenseFragmentArgs.fromBundle(getArguments());
        if (args.getAddExpenseDTO() != null) {
            addExpenseDTO = args.getAddExpenseDTO();
        } else {
            addExpenseDTO = new AddExpenseDTO();
        }


        binding.btnAddExpense.setOnClickListener(view -> {

            addExpenseDTO.setOption(OptionDialog.Option.ADD);
            addExpenseDTO.setDate(currentDate);
            addExpenseDTO.setMonth(month);
            Navigation.findNavController(view).navigate(
                    ExpenseFragmentDirections.actionExpenseFragmentToAddExpenseFragment(addExpenseDTO)
            );
        });

        binding.tvExpenseDate.setOnClickListener(view -> {
            showDatePickerDialog();
        });

        if(Constants.currentDate==null){
            Calendar cal = Calendar.getInstance();
            String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(cal.getTime());
            currentDate = date;
            Constants.currentDate = date;
        }else{
            currentDate = Constants.currentDate;
        }


        if (addExpenseDTO.getProperty() != null) {
            loadExpenseRecyclerView(currentDate + "_" + addExpenseDTO.getPropertyRefKey());
        } else {
            loadRecyclerViewByDate(currentDate);
        }

        return binding.getRoot();
    }

    private void loadExpenseRecyclerView(String date_propertyRef) {
        currentDate = date_propertyRef.split("_")[0];
        binding.tvExpenseDate.setText(date_propertyRef.split("_")[0]);
        Query query = FirebaseDatabase.getInstance().getReference().child("expense");

        if (addExpenseDTO.getPropertyRefKey() != null) {
            query = query.orderByChild("d_r").equalTo(date_propertyRef);
        }

        FirebaseRecyclerOptions<Expense> options = new FirebaseRecyclerOptions.Builder<Expense>()
                .setQuery(query, dataSnapshot -> dataSnapshot.getValue(Expense.class)).build();
        ExpenseAdapter adapter = new ExpenseAdapter(options, addExpenseDTO);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        adapter.startListening();

        try {
            displayCardDetails(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void loadRecyclerViewByDate(String date) {
        binding.tvExpenseDate.setText(date);
        Query query = FirebaseDatabase.getInstance().getReference().child("expense").orderByChild("d_r").startAt(date)
                .endAt(date + "\uf8ff");
        FirebaseRecyclerOptions<Expense> options = new FirebaseRecyclerOptions.Builder<Expense>()
                .setQuery(query, dataSnapshot -> dataSnapshot.getValue(Expense.class)).build();
        ExpenseAdapter adapter = new ExpenseAdapter(options, addExpenseDTO);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        adapter.startListening();

        try {
            displayCardDetails(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void displayCardDetails(String date) throws ParseException {

        month = UserUtils.getMonth(date);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference monthRef = firebaseDatabase.getReference("expenseCard/global/month").child(month);
        DatabaseReference dayRef = firebaseDatabase.getReference("expenseCard/global/day").child(date);


        if (addExpenseDTO.getPropertyRefKey() != null) {
            monthRef = firebaseDatabase.getReference("expenseCard/" + addExpenseDTO.getPropertyRefKey() + "/month").child(month);
            dayRef = firebaseDatabase.getReference("expenseCard/" + addExpenseDTO.getPropertyRefKey() + "/day").child(date);
        }

        monthRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.tvMonthSpending.setText("₹ " + snapshot.getValue());
                } else {
                    binding.tvMonthSpending.setText("₹ 0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dayRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    binding.tvDaySpending.setText("₹ " + snapshot.getValue());
                }else{
                    binding.tvDaySpending.setText("₹ 0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void showDatePickerDialog() {
        DatePickerFragment dateDialog = new DatePickerFragment();
        dateDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM");
                Date d = new Date(year, month, day);
                String date = dateFormatter.format(d) + " " + year;
                currentDate = date;
                Constants.currentDate = date;
                if (addExpenseDTO.getProperty() != null)
                    loadExpenseRecyclerView(currentDate + "_" + addExpenseDTO.getPropertyRefKey());
                else{
                    loadRecyclerViewByDate(currentDate);
                }
                binding.tvExpenseDate.setText(date);
            }
        });
        dateDialog.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Constants.currentDate = null;
    }
}