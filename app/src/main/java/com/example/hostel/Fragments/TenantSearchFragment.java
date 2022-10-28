package com.example.hostel.Fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hostel.Adapters.TenantsAdapter;
import com.example.hostel.Adapters.paging.TenantsPagingAdapter;
import com.example.hostel.CustomViews.CustomEditText;
import com.example.hostel.Models.Tenant;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.FragmentTenantSearchBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.shreyaspatil.firebase.recyclerpagination.DatabasePagingOptions;

public class TenantSearchFragment extends Fragment {

    CustomEditText et_search;
    FragmentTenantSearchBinding binding;
    TenantsPagingAdapter pagingAdapter;
    TenantsAdapter normalAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTenantSearchBinding.inflate(inflater, container, false);

        et_search = binding.etSearch;


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

            if (event.getAction() == MotionEvent.ACTION_UP) {

                if (event.getRawX() >= (et_search.getRight() - et_search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
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

        loadPagingRecyclerView();
        return binding.getRoot();
    }

    private void loadSearchRecyclerView() {

        String queryText = et_search.getText().toString();
        queryText = UserUtils.capitalize(queryText);

        Query query = FirebaseDatabase.getInstance().getReference().child("tenants").orderByChild("n")
                .startAt(queryText).endAt(queryText+"\uf8ff");

        FirebaseRecyclerOptions<Tenant> options = new FirebaseRecyclerOptions.Builder<Tenant>()
                .setQuery(query, dataSnapshot -> {
                    return dataSnapshot.getValue(Tenant.class);
                }).build();
        normalAdapter = new TenantsAdapter(options, TenantsAdapter.Page.SEARCH_TENANT);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(normalAdapter);

        normalAdapter.startListening();
    }

    private void loadPagingRecyclerView() {

        Query mDatabase = FirebaseDatabase.getInstance().getReference().child("tenants");

        PagedList.Config config = new PagedList.Config.Builder().setEnablePlaceholders(false).setPrefetchDistance(5).setPageSize(10).build();

        DatabasePagingOptions<Tenant> options = new DatabasePagingOptions.Builder<Tenant>().setLifecycleOwner(this).setQuery(mDatabase, config, Tenant.class).build();
        pagingAdapter = new TenantsPagingAdapter(options, binding.swipeRefreshLayout, TenantsAdapter.Page.SEARCH_TENANT);
        binding.recyclerView.setAdapter(pagingAdapter);

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pagingAdapter.refresh();
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (pagingAdapter != null)
            pagingAdapter.stopListening();
    }
}