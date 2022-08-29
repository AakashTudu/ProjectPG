package com.example.hostel.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Models.Option;
import com.example.hostel.R;

import java.util.ArrayList;

public class ManageAdapter extends RecyclerView.Adapter<ManageAdapter.ViewHolder> {

    ArrayList<Option> optionList;

    public ManageAdapter(ArrayList<Option> optionList) {
        this.optionList = optionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_manage,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_option;
        TextView tv_name, tv_description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_option = itemView.findViewById(R.id.iv_option);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_description = itemView.findViewById(R.id.tv_description);
        }

        public void bind() {
            Option option = optionList.get(getAdapterPosition());

            iv_option.setBackgroundResource(option.getImageID());

            tv_name.setText(option.getName());
            tv_description.setText(option.getDescription());
        }
    }
}
