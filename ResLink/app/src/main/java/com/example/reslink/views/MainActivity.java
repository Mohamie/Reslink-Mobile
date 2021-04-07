package com.example.reslink.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.reslink.R;
import com.example.reslink.adapters.ComplaintAdapter;
import com.example.reslink.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private ComplaintAdapter adapter;
    private NavController navController;
    AppBarConfiguration appBarConfiguration;
    ActivityMainBinding mainBinding;

    int currentDayNight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        
        //this instance is made to prevent Fragments on menu to display up button
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.announcementFragment, R.id.eventFragment, R.id.complaintFragment)
                .setDrawerLayout(mainBinding.drawerLayout)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_fragment_host);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(mainBinding.navigationView, navController);
        NavigationUI.setupWithNavController(mainBinding.bottomNavigationView, navController);


        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.userProfileFragment || destination.getId() == R.id.HCFragment || destination.getId() == R.id.addComplaintFragment
                        || destination.getId() == R.id.announcementDetailsFragment || destination.getId() == R.id.eventDetailsFragment || destination.getId() == R.id.complaintDetailsFragment || destination.getId() == R.id.settingsFragment || destination.getId() == R.id.fragment_logout) {
                    mainBinding.bottomNavigationView.setVisibility(View.GONE);
                } else {
                    mainBinding.bottomNavigationView.setVisibility(View.VISIBLE);

                }
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {

        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

            alert.setMessage("Are sure you want to logout?")
                    .setTitle("Logout")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Backendless.UserService.logout(new AsyncCallback<Void>() {
                                @Override
                                public void handleResponse(Void response) {
                                    Toast.makeText(MainActivity.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                    finish();
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Toast.makeText(MainActivity.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .create().show();
        }
        return false;
    }

}