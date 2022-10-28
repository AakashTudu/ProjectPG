package com.example.hostel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel.Adapters.ManageAdapter;
import com.example.hostel.DTO.AddTenantDTO;
import com.example.hostel.Enums.FragmentEnum;
import com.example.hostel.Models.Option;
import com.example.hostel.R;
import com.example.hostel.databinding.FragmentManageBinding;

import java.util.ArrayList;

public class ManageFragment extends Fragment {

    private FragmentManageBinding binding;
    RecyclerView recyclerView;
    ArrayList<Option> optionList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentManageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerView;
        optionList = new ArrayList<Option>() {{
            add(new Option(
                    R.drawable.ic_tenant,
                    "Tenant"
            ));

            add(new Option(
                    R.drawable.ic_vacancy,
                    "Room \n" +
                            "Vacancies"
            ));

            add(new Option(
                    R.drawable.ic_enquiries,
                    "Enquiries"
            ));

            add(new Option(
                    R.drawable.ic_booking,
                    "Booking’s"
            ));

            add(new Option(
                    R.drawable.ic_report,
                    "Report\n" +
                            "Complaints"
            ));

            add(new Option(
                    R.drawable.ic_expense,
                    "Expenses"
            ));
        }};


        recyclerView.setAdapter(new ManageAdapter(optionList, data -> {
            switch (data) {
                case "Tenant":
                    AddTenantDTO addTenantDTO = new AddTenantDTO();
                    addTenantDTO.setFragmentEnum(FragmentEnum.MANAGE);
                    Navigation.findNavController(binding.getRoot()).navigate(
                            ManageFragmentDirections.actionManageFragmentToTenantsFragment(addTenantDTO)
                    );
                    break;
                case "Booking’s":
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_manageFragment_to_bookingsFragment);
                    break;
                case "Report\nComplaints":
                    Navigation.findNavController(binding.getRoot()).navigate(
                            ManageFragmentDirections.actionManageFragmentToComplaintsFragment(null)
                    );
                    break;
                case "Room \nVacancies":
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_manageFragment_to_roomVacanciesFragment);
                    break;
                case "Expenses":
                    Navigation.findNavController(binding.getRoot()).navigate(
                            ManageFragmentDirections.actionManageFragmentToExpenseFragment(null)
                    );
            }
        }));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}