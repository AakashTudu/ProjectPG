package com.example.hostel.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hostel.R;
import com.example.hostel.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.dashboardFragment, R.id.manageFragment, R.id.propertyFragment, R.id.transactionFragment, R.id.profileFragment).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        binding.toolbar.setTitleTextColor(Color.BLACK);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                int id = navDestination.getId();
                boolean condition = !(id == R.id.dashboardFragment || id == R.id.manageFragment || id == R.id.propertyFragment || id == R.id.transactionFragment || id == R.id.profileFragment);
                if (condition)
                    binding.toolbar.setNavigationIcon(R.drawable.ic_back_btn);

                if (id == R.id.dashboardFragment){
                    binding.toolbar.setNavigationIcon(R.drawable.ic_dashboard);
                    binding.menuNotification.setVisibility(View.VISIBLE);
                    binding.menuTranslate.setVisibility(View.VISIBLE);
                }else{
                    binding.menuNotification.setVisibility(View.GONE);
                    binding.menuTranslate.setVisibility(View.GONE);
                }
            }
        });

        //binding.navView.setVisibility(View.GONE);


        FirebaseAuth firebaseAuth;
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    //Do anything here which needs to be done after signout is complete
                    startActivity(new Intent(MainActivity.this, EnterNumberActivity.class));
                    finish();
                }
            }
        };

        //Init and attach
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(authStateListener);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}