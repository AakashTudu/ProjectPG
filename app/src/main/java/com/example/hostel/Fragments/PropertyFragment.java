package com.example.hostel.Fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hostel.Adapters.PropertiesAdapter;
import com.example.hostel.Adapters.paging.PropertyPagingAdapter;
import com.example.hostel.CustomViews.CustomEditText;
import com.example.hostel.Models.Property;
import com.example.hostel.R;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.FragmentPropertyBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.shreyaspatil.firebase.recyclerpagination.DatabasePagingOptions;

public class PropertyFragment extends Fragment {

    private FragmentPropertyBinding binding;
    RecyclerView recyclerView;
    PropertyPagingAdapter pagingAdapter;
    PropertiesAdapter normalAdapter;
    CustomEditText et_search;
    Button btn_add_property;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPropertyBinding.inflate(inflater, container, false);

        et_search = binding.etSearch;
        btn_add_property = binding.btnAddProperty;

        recyclerView = binding.recyclerView;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!et_search.getText().toString().equals("")){
                        loadSearchRecyclerView();
                        et_search.hideKeyboard();
                    }
                    return true;
                }
                return false;
            }
        });

        et_search.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;

            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (et_search.getRight() - et_search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (et_search.hasFocus()) {
                        et_search.clearFocus();
                    }
                    et_search.setText("");
                    et_search.hideKeyboard();
                    normalAdapter.stopListening();
                    loadPagingRecyclerView();
                    return true;
                }
            }
            return false;
        });

        binding.btnAddProperty.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_propertyFragment_to_addPropertyFragment);

        });

        binding.tvRequestCall.setOnClickListener(view -> {
            UserUtils.call("1234576890", view.getContext());
        });

        loadPagingRecyclerView();

        return binding.getRoot();
    }

    private void loadSearchRecyclerView() {

        String queryText = et_search.getText().toString();
        queryText = UserUtils.capitalize(queryText);

        Query query = FirebaseDatabase.getInstance().getReference().child("properties").child(UserUtils.phoneNumber()).orderByChild("name")
                .startAt(queryText).endAt(queryText + "\uf8ff");

        FirebaseRecyclerOptions<Property> options = new FirebaseRecyclerOptions.Builder<Property>()
                .setQuery(query, snapshot -> {
                    return snapshot.getValue(Property.class);
                }).build();
        normalAdapter = new PropertiesAdapter(options, PropertiesAdapter.Page.PROPERTY);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(normalAdapter);

        normalAdapter.startListening();
    }

    private void loadPagingRecyclerView() {

        Query mDatabase = FirebaseDatabase.getInstance().getReference().child("properties").child(UserUtils.phoneNumber());

        PagedList.Config config = new PagedList.Config.Builder().setEnablePlaceholders(false).setPrefetchDistance(5).setPageSize(10).build();

        DatabasePagingOptions<Property> options = new DatabasePagingOptions.Builder<Property>().setLifecycleOwner(this).setQuery(mDatabase, config, Property.class).build();
        pagingAdapter = new PropertyPagingAdapter(options, binding.swipeRefreshLayout, PropertiesAdapter.Page.PROPERTY);

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pagingAdapter.refresh();
            }
        });

        binding.recyclerView.setAdapter(pagingAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (normalAdapter != null)
            normalAdapter.stopListening();
        binding = null;
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}