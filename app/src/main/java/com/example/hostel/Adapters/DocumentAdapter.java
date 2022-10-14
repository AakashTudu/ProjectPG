package com.example.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Models.Document;
import com.example.hostel.databinding.LayoutDocumentBinding;

import java.util.ArrayList;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {

    ArrayList<Document> documents;
    OnItemClickListener onItemClickListener;

    public DocumentAdapter(ArrayList<Document> documents, OnItemClickListener onItemClickListener) {
        this.documents = documents;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public DocumentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutDocumentBinding binding = LayoutDocumentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutDocumentBinding layoutDocumentBinding;

        public ViewHolder(@NonNull LayoutDocumentBinding layoutDocumentBinding) {
            super(layoutDocumentBinding.getRoot());
            this.layoutDocumentBinding = layoutDocumentBinding;
        }

        public void bind(int position) {
            Document document = documents.get(position);

            layoutDocumentBinding.ivIcon.setBackgroundResource(document.getCardIcon());
            layoutDocumentBinding.tvCardName.setText(document.getCardName());
            layoutDocumentBinding.tvCardDetail.setText(document.getCardDetail());
            layoutDocumentBinding.cardView.setOnClickListener(view -> {
                onItemClickListener.itemClicked(document);
            });
        }
    }

    public interface OnItemClickListener{
        void itemClicked(Document document);
    }
}
