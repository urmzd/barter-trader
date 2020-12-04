package ca.dal.bartertrader;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends FragmentActivity {

    private static final int[] NAV_GRAPH_IDS = new int[]{R.navigation.auth_nav_graph, R.navigation.user_nav_graph};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BarterTraderInjector injector = ((BarterTradeApplication) getApplication()).injector;
        getSupportFragmentManager().setFragmentFactory(injector.customFragmentFactory);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNavigationUp();
    }

    public void setNavigationUp() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_container);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNav, navController);


        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            Log.d("destinationUrmzd", String.valueOf(destination.getParent()));
            Log.d("destinationUrmzd", String.valueOf(controller.getCurrentDestination()));

            if (destination.getParent().getId() == R.id.auth_nav_graph) {
                bottomNav.setVisibility(View.GONE);
            } else {
                bottomNav.setVisibility(View.VISIBLE);
            }
        });
    }


}