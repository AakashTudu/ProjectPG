package com.example.hostel.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Models.PropertiesOption;
import com.example.hostel.Models.TenantsOption;
import com.example.hostel.R;

import java.util.ArrayList;

public class TenantsAdapter extends RecyclerView.Adapter<TenantsAdapter.ViewHolder> {

    ArrayList<TenantsOption> optionList;

    public TenantsAdapter(ArrayList<TenantsOption> optionList) {
        this.optionList = optionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tenants,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_student_name, tv_pg_name;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_student_name = itemView.findViewById(R.id.tv_student_name);
            tv_pg_name = itemView.findViewById(R.id.tv_pg_name);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (getAdapterPosition()) {
                        case 0:
                            Intent intent = new Intent(itemView.getContext(), com.example.hostel.UI.TenantsProfileActivity.class);
                            itemView.getContext().startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(itemView.getContext(), com.example.hostel.UI.TenantsProfileActivity.class);
                            itemView.getContext().startActivity(intent);
                            break;
                    }
                }
            });
        }

        public void bind(){
            TenantsOption tenantsOption = optionList.get(getAdapterPosition());

            tv_student_name.setText(tenantsOption.getName());
            tv_pg_name.setText(tenantsOption.getType());
        }
    }


}
