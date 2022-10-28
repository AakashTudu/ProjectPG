package com.example.hostel.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.hostel.Models.MonthlyCollection;
import com.example.hostel.R;
import com.example.hostel.Utils.Constants;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.FragmentDashboardBinding;
import com.example.monthandyearpicker.MonthYearPickerDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DashboardFragment extends Fragment {

    FragmentDashboardBinding binding;
    MonthlyCollection collectionBundle;
    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(UserUtils.phoneNumber()).child("name");

        myRef.keepSynced(true);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                binding.tvUserName.setText(name + "\uD83D\uDC4B");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        binding.recordPay.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(
                    DashboardFragmentDirections.actionDashboardFragmentToRecordPaymentFragment(null, null)
            );
        });


        binding.btnCollection.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.collection, collectionBundle);
            Navigation.findNavController(view).navigate(R.id.action_dashboardFragment_to_collectionFragment, bundle);
        });

        binding.tvDate.setOnClickListener(view -> {
            showMonthPickerDialog();
        });

        binding.btnExpenses.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(
                    DashboardFragmentDirections.actionDashboardFragmentToExpenseFragment(null)
            );
        });


        binding.btnCollection.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.collection, collectionBundle);
            Navigation.findNavController(view).navigate(R.id.action_dashboardFragment_to_collectionFragment, bundle);
        });

        loadMonthlyCollectionCard();

        return binding.getRoot();
    }

    private void loadMonthlyCollectionCard() {
        binding.linearProgress.setVisibility(View.VISIBLE);
        binding.linearProgressIndicator.setVisibility(View.INVISIBLE);
        Calendar cal = Calendar.getInstance();
        String month = new SimpleDateFormat("MMM yyyy", Locale.getDefault()).format(cal.getTime());

        //Query query = FirebaseDatabase.getInstance().getReference().child(UserUtils.phoneNumber()).child("collection/list").orderByChild("month").equalTo(month);
        Query query = FirebaseDatabase.getInstance().getReference().child("collection").child(UserUtils.phoneNumber()).child("list").orderByChild("month").equalTo(month);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                binding.linearProgressIndicator.setVisibility(View.VISIBLE);

                monthlyCollection = snapshot.getValue(MonthlyCollection.class);
                collectionBundle = snapshot.getValue(MonthlyCollection.class);

                binding.tvTotalCollection.setText("Rs " + monthlyCollection.getTotalPrice());
                binding.tvCollectedMoney.setText("Rs " + monthlyCollection.getPaidPrice());
                binding.tvPendingMoney.setText("Rs " + monthlyCollection.getPendingPrice());
                binding.tvMoneyPercentage.setText(monthlyCollection.getPercentage());
                binding.linearProgressIndicator.setProgress(monthlyCollection.getIntPercentage());
                binding.linearProgress.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MonthlyCollection monthlyCollection = snapshot.getValue(MonthlyCollection.class);
                collectionBundle = snapshot.getValue(MonthlyCollection.class);
                binding.tvTotalCollection.setText("Rs " + monthlyCollection.getTotalPrice());
                binding.tvCollectedMoney.setText("Rs " + monthlyCollection.getPaidPrice());
                binding.tvPendingMoney.setText("Rs " + monthlyCollection.getPendingPrice());
                binding.tvMoneyPercentage.setText(monthlyCollection.getPercentage());
                binding.linearProgressIndicator.setProgress(monthlyCollection.getIntPercentage());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (monthlyCollection == null) {
                    binding.tvTotalCollection.setText("Rs 0");
                    binding.tvCollectedMoney.setText("Rs 0");
                    binding.tvPendingMoney.setText("Rs 0");
                    binding.tvMoneyPercentage.setText("0 %");
                    binding.linearProgressIndicator.setProgress(0);
                    binding.linearProgress.setVisibility(View.INVISIBLE);
                    binding.linearProgressIndicator.setVisibility(View.INVISIBLE);
                }
                monthlyCollection = null;
                binding.linearProgress.setVisibility(View.GONE);

            }
        }, 2000);

        binding.tvDate.setText(month);
    }

    MonthlyCollection monthlyCollection;

    public void showMonthPickerDialog() {

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        MonthYearPickerDialog.Builder builder = new MonthYearPickerDialog.Builder(getContext(), com.example.monthandyearpicker.R.style.Style_MonthYearPickerDialog_Black,
                new MonthYearPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int year, int month) {
                        String monthString = new DateFormatSymbols().getMonths()[month];
                        binding.linearProgress.setVisibility(View.VISIBLE);
                        String monthYear = monthString.substring(0, 3) + " " + year;



                        Query query = FirebaseDatabase.getInstance().getReference().child("collection").child(UserUtils.phoneNumber()).child("list").orderByChild("month").equalTo(monthYear);

                        query.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                binding.linearProgressIndicator.setVisibility(View.VISIBLE);
                                monthlyCollection = snapshot.getValue(MonthlyCollection.class);
                                collectionBundle = snapshot.getValue(MonthlyCollection.class);

                                Log.d("dsgsdgsd", String.valueOf(monthlyCollection));

                                binding.tvTotalCollection.setText("Rs " + monthlyCollection.getTotalPrice());
                                binding.tvCollectedMoney.setText("Rs " + monthlyCollection.getPaidPrice());
                                binding.tvPendingMoney.setText("Rs " + monthlyCollection.getPendingPrice());
                                binding.tvMoneyPercentage.setText(monthlyCollection.getPercentage());
                                binding.linearProgressIndicator.setProgress(monthlyCollection.getIntPercentage());
                                binding.linearProgress.setVisibility(View.GONE);

                                //query.removeEventListener(this);

                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                MonthlyCollection monthlyCollection = snapshot.getValue(MonthlyCollection.class);
                                collectionBundle = snapshot.getValue(MonthlyCollection.class);

                                binding.tvTotalCollection.setText("Rs " + monthlyCollection.getTotalPrice());
                                binding.tvCollectedMoney.setText("Rs " + monthlyCollection.getPaidPrice());
                                binding.tvPendingMoney.setText("Rs " + monthlyCollection.getPendingPrice());
                                binding.tvMoneyPercentage.setText(monthlyCollection.getPercentage());
                                binding.linearProgressIndicator.setProgress(monthlyCollection.getIntPercentage());
                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d("dsgsdgsd", "error: " + error);
                            }
                        });

                        binding.tvDate.setText(monthYear);


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (monthlyCollection == null) {
                                    binding.tvTotalCollection.setText("Rs 0");
                                    binding.tvCollectedMoney.setText("Rs 0");
                                    binding.tvPendingMoney.setText("Rs 0");
                                    binding.tvMoneyPercentage.setText("0 %");
                                    binding.linearProgressIndicator.setProgress(0);
                                    binding.linearProgressIndicator.setVisibility(View.INVISIBLE);
                                }
                                monthlyCollection = null;
                                binding.linearProgress.setVisibility(View.GONE);
                            }
                        }, 2000);


                    }
                }, currentYear, currentMonth);

        builder.build().show();

    }
}