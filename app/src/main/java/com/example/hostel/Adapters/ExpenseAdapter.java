package com.example.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.BottomSheetDialog.OptionDialog;
import com.example.hostel.Fragments.ExpenseFragmentDirections;
import com.example.hostel.Models.Expense;
import com.example.hostel.R;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.LayoutExpenseBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;

public class ExpenseAdapter extends FirebaseRecyclerAdapter<Expense, ExpenseAdapter.ViewHolder> {

    public ExpenseAdapter(@NonNull FirebaseRecyclerOptions<Expense> options) {
        super(options);
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

        public ViewHolder(@NonNull LayoutExpenseBinding layoutExpenseBinding) {
            super(layoutExpenseBinding.getRoot());
            this.layoutExpenseBinding = layoutExpenseBinding;
        }

        public void bind(int position, Expense expense) {
            layoutExpenseBinding.tvName.setText(expense.getT());
            layoutExpenseBinding.tvAmount.setText("Rs " + expense.getP());
            if (expense.getM()) {
                layoutExpenseBinding.ivIcon.setBackgroundResource(R.drawable.ic_shoping);
            } else {
                layoutExpenseBinding.ivIcon.setBackgroundResource(R.drawable.ic_transaction);
            }

            layoutExpenseBinding.tvDescription.setText(expense.getR());

            layoutExpenseBinding.tvAmount.setOnClickListener(view -> {
                Snackbar.make(view, expense.getR(), Snackbar.LENGTH_SHORT).show();
            });


            layoutExpenseBinding.cardView.setOnClickListener(view -> {
                String date = expense.getD();
                try {
                    String month = UserUtils.getMonth(date);
                    Navigation.findNavController(view).navigate(ExpenseFragmentDirections.actionExpenseFragmentToAddExpenseFragment(expense, getRef(position).toString(), OptionDialog.Option.EDIT, date, month));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
