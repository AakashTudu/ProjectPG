package com.example.hostel.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Adapters.PropertiesAdapter;
import com.example.hostel.CustomViews.CustomEditText;
import com.example.hostel.Models.Property;
import com.example.hostel.R;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.FragmentPropertyBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PropertyFragment extends Fragment {

    private FragmentPropertyBinding binding;
    RecyclerView recyclerView;
    PropertiesAdapter adapter;

    CustomEditText et_search;
    Button btn_add_property;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPropertyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        et_search = binding.etSearch;
        btn_add_property = binding.btnAddProperty;

        recyclerView = binding.recyclerView;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadRecyclerView();

        et_search.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;

            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (et_search.getRight() - et_search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (et_search.hasFocus()) {
                        et_search.clearFocus();
                    }
                    et_search.setText("");
                    et_search.hideKeyboard();
                    return true;
                }
            }
            return false;
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(binding.etSearch.getText().toString());
            }
        });

        binding.btnAddProperty.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_propertyFragment_to_addPropertyFragment);

        });

        return root;
    }

    private void loadRecyclerView() {
        Query query = FirebaseDatabase.getInstance().getReference().child("properties").child(UserUtils.phoneNumber());
        FirebaseRecyclerOptions<Property> options = new FirebaseRecyclerOptions.Builder<Property>()
                .setQuery(query, snapshot -> {
                    String name = snapshot.child("name").getValue().toString();
                    String type = snapshot.child("type").getValue().toString();
                    String city = snapshot.child("city").getValue().toString();
                    String location = snapshot.child("location").getValue().toString();
                    String isLive = snapshot.child("isLive").getValue().toString();
                    return new Property(
                            name,type, city ,location,isLive
                    );
                }).build();
        adapter = new PropertiesAdapter(options);

        recyclerView.setAdapter(adapter);

        adapter.startListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.stopListening();
        binding = null;
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}