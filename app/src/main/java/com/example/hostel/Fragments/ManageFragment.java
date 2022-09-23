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
                    "TENANT",
                    getResources().getString(R.string.add_search_your_tenants)
            ));

            add(new Option(
                    R.drawable.ic_vacancy,
                    "ROOM \n" +
                            "VACANCIES",
                    getResources().getString(R.string.check_room_vacancies)
            ));

            add(new Option(
                    R.drawable.ic_enquiries,
                    "ENQUIRIES",
                    getResources().getString(R.string.clear_your_doubts)
            ));

            add(new Option(
                    R.drawable.ic_booking,
                    "BOOKING’S",
                    getResources().getString(R.string.check_your_booking_s_status)
            ));

            add(new Option(
                    R.drawable.ic_report,
                    "REPORT\n" +
                            "COMPLAINTS",
                    getResources().getString(R.string.report_your_complaints)
            ));

            add(new Option(
                    R.drawable.ic_expense,
                    "EXPENSES",
                    getResources().getString(R.string.track_your_expenses)
            ));
        }};


        recyclerView.setAdapter(new ManageAdapter(optionList, data -> {
            switch (data){
                case "TENANT":
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_manageFragment_to_tenantsFragment);
                    break;
                case "BOOKING’S":
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_manageFragment_to_bookingsFragment);
                    break;
                case "REPORT\nCOMPLAINTS":
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_manageFragment_to_complaintsFragment);
                    break;
                case "ROOM \nVACANCIES":
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_manageFragment_to_roomVacanciesFragment);
                    break;
                case "EXPENSES":
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_manageFragment_to_expenseFragment);
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