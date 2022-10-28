package com.example.hostel.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.BottomSheetDialog.OptionDialog;
import com.example.hostel.DTO.AddExpenseDTO;
import com.example.hostel.Fragments.ExpenseFragmentDirections;
import com.example.hostel.Models.Expense;
import com.example.hostel.Models.Property;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.LayoutExpenseBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;

public class ExpenseAdapter extends FirebaseRecyclerAdapter<Expense, ExpenseAdapter.ViewHolder> {
    AddExpenseDTO addExpenseDTO;

    public ExpenseAdapter(@NonNull FirebaseRecyclerOptions<Expense> options, AddExpenseDTO addExpenseDTO) {
        super(options);
        this.addExpenseDTO = addExpenseDTO;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Expense expense) {
        holder.bind(position, expense);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutExpenseBinding binding = LayoutExpenseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutExpenseBinding layoutExpenseBinding;
        String name = "";

        public ViewHolder(@NonNull LayoutExpenseBinding layoutExpenseBinding) {
            super(layoutExpenseBinding.getRoot());
            this.layoutExpenseBinding = layoutExpenseBinding;
        }

        public void bind(int position, Expense expense) {
            layoutExpenseBinding.tvName.setText(expense.getT());
            layoutExpenseBinding.tvAmount.setText("₹ " + expense.getP());

            layoutExpenseBinding.tvDescription.setText(expense.getR());

            layoutExpenseBinding.tvAmount.setOnClickListener(view -> {
                Snackbar.make(view, expense.getR(), Snackbar.LENGTH_SHORT).show();
            });

            layoutExpenseBinding.tvExpenseType.setText(expense.getExpenseType());
            if (addExpenseDTO.getProperty() != null){
                Property property = addExpenseDTO.getProperty();
                layoutExpenseBinding.tvProperty.setText(property.getName().split(" ")[0] + " PG for " + property.getType().replace("PG","").trim());
                layoutExpenseBinding.tvProperty.setBackgroundColor(Color.WHITE);
                layoutExpenseBinding.shimmerViewContainer.hideShimmer();
            }
            else {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference nameReference = database.getReference("properties").child(UserUtils.phoneNumber()).
                        child(expense.getPropertyRef())
                        .child("name");
                DatabaseReference typeReference = database.getReference("properties").child(UserUtils.phoneNumber()).
                        child(expense.getPropertyRef())
                        .child("type");

                nameReference.keepSynced(true);
                typeReference.keepSynced(true);

                nameReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        name = snapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                typeReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String type = snapshot.getValue(String.class);
                        layoutExpenseBinding.shimmerViewContainer.hideShimmer();
                        layoutExpenseBinding.tvProperty.setText(name.split(" ")[0] + " PG for " + type.replace("PG","").trim());
                        layoutExpenseBinding.tvProperty.setBackgroundColor(Color.WHITE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                layoutExpenseBinding.tvProperty.setText("");
            }

            layoutExpenseBinding.cardView.setOnClickListener(view -> {
                try {
                    addExpenseDTO.setPropertyName(layoutExpenseBinding.tvProperty.getText().toString());
                    addExpenseDTO.setExpense(expense);
                    addExpenseDTO.setExpenseRef(getRef(position).toString());
                    addExpenseDTO.setOption(OptionDialog.Option.EDIT);
                    addExpenseDTO.setDate(expense.getDate());
                    addExpenseDTO.setMonth(UserUtils.getMonth(expense.getDate()));
                    Navigation.findNavController(view).navigate(ExpenseFragmentDirections.actionExpenseFragmentToAddExpenseFragment(addExpenseDTO));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
