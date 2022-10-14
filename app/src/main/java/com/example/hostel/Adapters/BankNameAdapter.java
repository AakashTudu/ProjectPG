package com.example.hostel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.BottomSheetDialog.BankNameDialog;
import com.example.hostel.Models.BankName;
import com.example.hostel.databinding.LayoutBankNameBinding;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BankNameAdapter extends RecyclerView.Adapter<BankNameAdapter.ViewHolder> {

    List<BankName> bankList;
    OnBankItemClickListener onBankItemClickListener;
    BankNameDialog bankNameDialog;
    public BankNameAdapter(Context context, OnBankItemClickListener onBankItemClickListener, BankNameDialog bankNameDialog) {
        bankList = getBankList(context);
        this.onBankItemClickListener = onBankItemClickListener;
        this.bankNameDialog = bankNameDialog;
    }

    @NonNull
    @Override
    public BankNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutBankNameBinding binding = LayoutBankNameBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BankNameAdapter.ViewHolder holder, int position) {
        try {
            holder.bind(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutBankNameBinding layoutBankNameBinding;

        public ViewHolder(@NonNull LayoutBankNameBinding layoutBankNameBinding) {
            super(layoutBankNameBinding.getRoot());
            this.layoutBankNameBinding = layoutBankNameBinding;
        }

        public void bind(int position) throws JSONException {
            BankName bank = bankList.get(position);
            Picasso.get().load(bank.getImageUrl()).into(layoutBankNameBinding.ivBankIcon);
            layoutBankNameBinding.tvBankName.setText(bank.getName());

            layoutBankNameBinding.bank.setOnClickListener(view -> {
                onBankItemClickListener.onItemClicked(bank);
                bankNameDialog.dismiss();
            });
        }
    }

    // This add all JSON object's data to the respective lists
    ArrayList<BankName> getBankList(Context context) {
        // Exceptions are returned by JSONObject when the object cannot be created
        ArrayList<BankName> bankList = new ArrayList<>();
        try {
            // Convert the string returned to a JSON object
            JSONObject jsonObject = new JSONObject(getJson(context));

            Iterator keyIterator = jsonObject.keys();

            while (keyIterator.hasNext()){
                String bankName = keyIterator.next().toString();
                String imageUrl = jsonObject.getString(bankName);
                bankList.add(new BankName(bankName,imageUrl));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bankList;
    }

    public String getJson(Context context) {
        String json = null;
        try {
            // Opening cities.json file
            InputStream is = context.getAssets().open("banks.json");
            // is there any content in the file
            int size = is.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            is.read(buffer);
            // close the stream --- very important
            is.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return json;
        }
        return json;
    }

    public interface OnBankItemClickListener {
        void onItemClicked(BankName bankName);
    }
}
