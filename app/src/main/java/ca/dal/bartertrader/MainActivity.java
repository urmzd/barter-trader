package ca.dal.bartertrader;

import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import ca.dal.bartertrader.utils.LocationServiceManager;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BarterTraderInjector injector = ((BarterTradeApplication) getApplication()).injector;
        getSupportFragmentManager().setFragmentFactory(injector.customFragmentFactory);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        LocationServiceManager locationService = new LocationServiceManager(locationManager, geocoder);
        locationService.startRequestingLocationUpdates();

        setNavigationUp();
    }

    public void setNavigationUp() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_container);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNav, navController);


        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getParent().getId() == R.id.auth_nav_graph) {
                bottomNav.setVisibility(View.GONE);
            } else {
                if (destination.getId() == R.id.handlePostFragment) {
                    bottomNav.setVisibility(View.GONE);
                } else {
                    bottomNav.setVisibility(View.VISIBLE);
                }
            }
        });
    }


}