package com.example.hostel.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
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
import com.example.hostel.Models.Expense;
import com.example.hostel.R;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ExpenseFragment extends Fragment {

    FragmentExpenseBinding binding;
    String currentDate;
    String month;


    public ExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExpenseBinding.inflate(inflater, container, false);

        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.btnAddExpense.setOnClickListener(view -> {
/*            Bundle bundle = new Bundle();
            bundle.putString("date", currentDate);
            bundle.putString("month", month);
            Navigation.findNavController(view).navigate(R.id.action_expenseFragment_to_addExpenseFragment, bundle);*/
/*            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference reference = firebaseDatabase.getReference("expense");
            Expense expense = new Expense("Vegetable", "1200", "2 kg onion", currentDate);
            reference.push().setValue(expense);*/
            Navigation.findNavController(view).navigate(
                ExpenseFragmentDirections.actionExpenseFragmentToAddExpenseFragment(null,null, OptionDialog.Option.ADD,currentDate,month)
            );
        });

        binding.tvExpenseDate.setOnClickListener(view -> {
            showDatePickerDialog();
        });

        Calendar cal = Calendar.getInstance();
        String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(cal.getTime());
        currentDate = date;
        if (Constants.dateExpense.isEmpty()){
            loadRoomRecyclerView(currentDate);
        }else{
            loadRoomRecyclerView(Constants.dateExpense);
        }

        return binding.getRoot();
    }

    private void loadRoomRecyclerView(String date) {
        currentDate = date;
        binding.tvExpenseDate.setText(currentDate);
        Constants.dateExpense = date;
        Query query = FirebaseDatabase.getInstance().getReference().child("expense").orderByChild("d").equalTo(date);

        FirebaseRecyclerOptions<Expense> options = new FirebaseRecyclerOptions.Builder<Expense>()
                .setQuery(query, dataSnapshot -> dataSnapshot.getValue(Expense.class)).build();
        ExpenseAdapter adapter = new ExpenseAdapter(options);

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
        DatabaseReference monthRef = firebaseDatabase.getReference("expenseCard/month").child(month);

        DatabaseReference dayRef = firebaseDatabase.getReference("expenseCard/day").child(date);

        monthRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    binding.tvMonthSpending.setText("₹ " + snapshot.getValue());
                }else{
                    binding.tvMonthSpending.setText("₹ 0");
                }
                Log.d("sdgsdfsdf", "onDataChange: " + snapshot);
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
                Log.d("sdgsdfsdf", "onDataChange: " + snapshot);
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
                loadRoomRecyclerView(currentDate);
                binding.tvExpenseDate.setText(date);
            }
        });
        dateDialog.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
}