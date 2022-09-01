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
import com.example.hostel.R;

import java.util.ArrayList;

public class PropertiesAdapter extends RecyclerView.Adapter<PropertiesAdapter.ViewHolder> {

    ArrayList<PropertiesOption> optionList;

    public PropertiesAdapter(ArrayList<PropertiesOption> optionList) {
        this.optionList = optionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_properties, parent, false);
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

        TextView iv_text, iv_type, iv_location;
        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_text = itemView.findViewById(R.id.iv_text);
            iv_type = itemView.findViewById(R.id.iv_type);
            iv_location = itemView.findViewById(R.id.iv_location);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (getAdapterPosition()) {
                        case 0:
                            Intent intent = new Intent(itemView.getContext(), com.example.hostel.UI.UserPropertyActivity.class);
                            itemView.getContext().startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(itemView.getContext(), com.example.hostel.UI.UserPropertyActivity.class);
                            itemView.getContext().startActivity(intent);
                            break;
                    }
                }
            });
        }

        public void bind() {
            PropertiesOption propertiesOption = optionList.get(getAdapterPosition());

            iv_text.setText(propertiesOption.getPropName());
            iv_type.setText(propertiesOption.getPropType());
            iv_location.setText(propertiesOption.getPropLocation());

        }
    }

}


