package com.example.hostel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hostel.Adapters.DocumentAdapter;
import com.example.hostel.BottomSheetDialog.AdharCardDialog;
import com.example.hostel.Models.Document;
import com.example.hostel.R;
import com.example.hostel.databinding.FragmentDocumentBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class DocumentFragment extends Fragment {

    FragmentDocumentBinding binding;
    public DocumentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDocumentBinding.inflate(inflater,container,false);

        ArrayList<Document> documents = new ArrayList<Document>(){{
            add(new Document(
                    R.drawable.ic_adhar,
                    "Adhar Card",
                    "Government ID Proof"
            ));

            add(new Document(
                    R.drawable.ic_identification,
                    "Identification Card",
                    "Organization ID Proof"
            ));

            add(new Document(
                    R.drawable.ic_identification,
                    "Pan Card",
                    "Government ID Proof"
            ));

            add(new Document(
                    R.drawable.ic_rental,
                    "Rental Agreement",
                    "Organization ID Proof"
            ));
        }};


        DocumentAdapter documentAdapter = new DocumentAdapter(documents, new DocumentAdapter.OnItemClickListener() {
            @Override
            public void itemClicked(Document document) {
                switch (document.getCardName()){
                    case "Adhar Card":
                        new AdharCardDialog(getContext());
                        break;
                    default:
                        Snackbar.make(binding.getRoot().getRootView(), "No " + document.getCardName() + " found",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        binding.recyclerView.setAdapter(documentAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        documentAdapter.notifyDataSetChanged();

        return binding.getRoot();
    }
}