package com.example.tenant.ui.home;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tenant.R;
import com.example.tenant.adapter.MenuAdapter;
import com.example.tenant.databinding.FragmentHomeBinding;
import com.example.tenant.model.Menu;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    MenuAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        loadMenuRecyclerView();
        return binding.getRoot();
    }

    private void loadMenuRecyclerView() {
        ArrayList<Menu> menuList = new ArrayList<Menu>() {{
            add(new Menu("Breakfast", "8:00AM - 11:00AM", R.drawable.ic_breakfast));
            add(new Menu("Lunch", "12:00PM - 3:00PM", R.drawable.ic_lunch));
            add(new Menu("Dinner", "8:00PM - 10:00PM", R.drawable.ic_dinner));
        }};
        adapter = new MenuAdapter(menuList);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

/*    DisplayMetrics displayMetrics = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels/2;
        Toast.makeText(getContext(), "width "+width, Toast.LENGTH_SHORT).show();*/


/*

    ViewGroup.LayoutParams testingLayoutParams = binding.btnTesting.getLayoutParams();
    ViewGroup.LayoutParams originalLayoutParams = binding.btnOriginal.getLayoutParams();
//Button new width
        testingLayoutParams.width = originalLayoutParams.width;
                binding.btnTesting.setLayoutParams(testingLayoutParams);*/
