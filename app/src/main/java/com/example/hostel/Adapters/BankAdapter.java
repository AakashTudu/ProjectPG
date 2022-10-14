package com.example.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.BottomSheetDialog.OptionDialog;
import com.example.hostel.Fragments.BankDetailsFragmentDirections;
import com.example.hostel.Models.Bank;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.LayoutBankAccountBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class BankAdapter extends FirebaseRecyclerAdapter<Bank, BankAdapter.ViewHolder> {

    public BankAdapter(@NonNull FirebaseRecyclerOptions<Bank> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BankAdapter.ViewHolder holder, int position, @NonNull Bank bank) {
        holder.bind(position, bank);
    }

    @NonNull
    @Override
    public BankAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutBankAccountBinding binding = LayoutBankAccountBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutBankAccountBinding layoutBankAccountBinding;

        public ViewHolder(@NonNull LayoutBankAccountBinding layoutBankAccountBinding) {
            super(layoutBankAccountBinding.getRoot());
            this.layoutBankAccountBinding = layoutBankAccountBinding;
        }

        public void bind(int position, Bank bank) {
            layoutBankAccountBinding.bankName.setText(bank.getBankName());
            layoutBankAccountBinding.accHolderName.setText(bank.getHolderName());
            layoutBankAccountBinding.accountNumber.setText(bank.getAccountNumber());
            layoutBankAccountBinding.tvIfscCode.setText((bank.getIfscCode()));
            try {
                Picasso.get().load(UserUtils.getBankImageUrl(bank.getBankName(), layoutBankAccountBinding.accHolderName.getContext())).into(layoutBankAccountBinding.ivBankIcon);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            layoutBankAccountBinding.ivMore.setOnClickListener(view -> {
                new OptionDialog(view.getContext(), option -> {
                    getRef(position).removeValue();
                }, OptionDialog.Option.DELETE);
            });

            layoutBankAccountBinding.cardViewDetails.setOnClickListener(view -> {
                Navigation.findNavController(view).navigate(
                        BankDetailsFragmentDirections.actionBankDetailsFragmentToAddBankFragment(OptionDialog.Option.EDIT,bank,getRef(position).toString())
                );
            });
        }
    }
}
