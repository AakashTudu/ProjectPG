package com.example.tenant.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tenant.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

/*

    ViewGroup.LayoutParams testingLayoutParams = binding.btnTesting.getLayoutParams();
    ViewGroup.LayoutParams originalLayoutParams = binding.btnOriginal.getLayoutParams();
//Button new width
        testingLayoutParams.width = originalLayoutParams.width;
                binding.btnTesting.setLayoutParams(testingLayoutParams);*/
