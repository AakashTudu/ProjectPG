package com.example.hostel.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.hostel.R;
import com.example.hostel.UI.AddPropertyActivity;
import com.example.hostel.UI.MainActivity;
import com.example.hostel.Utils.Constants;
import com.example.hostel.Utils.UserSharedPref;
import com.example.hostel.Utils.UserUtils;
import com.example.hostel.databinding.FragmentAddPropertyBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class AddPropertyFragment extends Fragment {

    FragmentAddPropertyBinding binding;
    ArrayList<String> listState = new ArrayList<>();
    ArrayList<String> listCity = new ArrayList<>();
    AutoCompleteTextView act;

    public AddPropertyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentAddPropertyBinding.inflate(inflater, container, false);
        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });


        AtomicReference<String> type = new AtomicReference<>("Men's PG");

        binding.btnMenPG.setOnClickListener(view1 -> {
            type.set("Men's PG");
            binding.btnLadiesPG.setBackgroundResource(R.drawable.text_input_layout);
            binding.btnMenPG.setBackgroundResource(R.drawable.outlined_border_10_dp_grey);
            binding.btnCoedPG.setBackgroundResource(R.drawable.text_input_layout);
            binding.tvMenPG.setTextColor(Color.WHITE);
            binding.tvLadiesPG.setTextColor(Color.BLACK);
            binding.tvCoedPG.setTextColor(Color.BLACK);
        });


        binding.btnLadiesPG.setOnClickListener(view1 -> {
            type.set("Ladies PG");
            binding.btnLadiesPG.setBackgroundResource(R.drawable.outlined_border_10_dp_grey);
            binding.btnMenPG.setBackgroundResource(R.drawable.text_input_layout);
            binding.btnCoedPG.setBackgroundResource(R.drawable.text_input_layout);
            binding.tvLadiesPG.setTextColor(Color.WHITE);
            binding.tvMenPG.setTextColor(Color.BLACK);
            binding.tvCoedPG.setTextColor(Color.BLACK);
        });

        binding.btnCoedPG.setOnClickListener(view1 -> {
            type.set("Coed PG");
            binding.btnLadiesPG.setBackgroundResource(R.drawable.text_input_layout);
            binding.btnMenPG.setBackgroundResource(R.drawable.text_input_layout);
            binding.btnCoedPG.setBackgroundResource(R.drawable.outlined_border_10_dp_grey);
            binding.tvCoedPG.setTextColor(Color.WHITE);
            binding.tvMenPG.setTextColor(Color.BLACK);
            binding.tvLadiesPG.setTextColor(Color.BLACK);
        });

        binding.btnContinue.setOnClickListener(view -> {

            String name = binding.etPropertyName.getText().toString();
            name = UserUtils.capitalize(name);
            String city = binding.etCityName.getText().toString();
            String location = binding.etPropertyLocation.getText().toString();


            if (name.equals("")){
                Snackbar.make(view, "Please enter property name",Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (city.equals("")){
                Snackbar.make(view, "Please enter city name",Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (location.equals("")){
                Snackbar.make(view, "Please enter property location",Snackbar.LENGTH_SHORT).show();
                return;
            }
            binding.linearProgressIndicator.setVisibility(View.VISIBLE);
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("type", type.toString());
            map.put("city", city);
            map.put("location", location);
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            String timeStampRef = String.valueOf(System.currentTimeMillis());
            DatabaseReference userRef = rootRef.child("properties").child(UserUtils.phoneNumber()).child(timeStampRef);
            userRef.setValue(map).addOnSuccessListener(unused -> {
                binding.linearProgressIndicator.setVisibility(View.GONE);

                if (getActivity().getClass().getName().equals(AddPropertyActivity.class.getName())){
                    UserSharedPref.initializeSharedPreferenceForEmptyPropertyCheck(view.getContext()).edit().putBoolean(UserSharedPref.emptyPropertyCheck, false).apply();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    ((AddPropertyActivity) getActivity()).startActivity(intent);
                    getActivity().finish();
                }else if(getActivity().getClass().getName().equals(MainActivity.class.getName())){
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.propertyRef, timeStampRef);
                    bundle.putString(Constants.fragment, "AddPropertyFragment");
                    Navigation.findNavController(view).navigate(R.id.action_addPropertyFragment_to_totalFloorFragment, bundle);
                }
            });
        });

        callAll();

        return binding.getRoot();
    }

    public void callAll() {
        obj_list();
        addCity();
        addState();
    }

    // Get the content of cities.json from assets directory and store it as string
    public String getJson() {
        String json = null;
        try {
            // Opening cities.json file
            InputStream is = getContext().getAssets().open("cities.json");
            // is there any content in the file
            int size = is.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            is.read(buffer);
            // close the stream --- very important
            is.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return json;
        }
        return json;
    }

    // This add all JSON object's data to the respective lists
    void obj_list() {
        // Exceptions are returned by JSONObject when the object cannot be created
        try {
            // Convert the string returned to a JSON object
            JSONObject jsonObject = new JSONObject(getJson());
            // Get Json array
            JSONArray array = jsonObject.getJSONArray("array");
            // Navigate through an array item one by one
            for (int i = 0; i < array.length(); i++) {
                // select the particular JSON data
                JSONObject object = array.getJSONObject(i);
                String city = object.getString("name");
                String state = object.getString("state");
                listCity.add(city);
                listState.add(state);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // The second auto complete text view
    void addCity() {
        act = binding.etCityName;
        adapterSetting(listCity);
    }

    // The third auto complete text view
    void addState() {
        Set<String> set = new HashSet<String>(listState);
        act = binding.etStateName;
        adapterSetting(new ArrayList(set));
    }

    // setting adapter for auto complete text views
    void adapterSetting(ArrayList arrayList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, arrayList);
        act.setAdapter(adapter);
        hideKeyBoard();
    }

    // hide keyboard on selecting a suggestion
    public void hideKeyBoard() {
        act.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });
    }
}