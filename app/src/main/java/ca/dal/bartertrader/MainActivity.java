package ca.dal.bartertrader;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import ca.dal.bartertrader.utils.handler.live_data.NavControllerLiveData;

public class MainActivity extends FragmentActivity {

    private NavControllerLiveData controller;
    private AppBarConfiguration appBarConfiguration;
    private BottomNavigationView bottomNavigationView;
    private MaterialToolbar toolbar;

    private static final int[] NAV_GRAPH_IDS = new int[]{R.navigation.auth_nav_graph, R.navigation.provider_nav_graph, R.navigation.profile_nav_graph};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BarterTraderInjector injector = ((BarterTradeApplication) getApplication()).injector;
        getSupportFragmentManager().setFragmentFactory(injector.customFragmentFactory);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.loginFragment, R.id.providerHomeFragment, R.id.profileFragment).build();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        toolbar = findViewById(R.id.toolbar_standard);

        if (savedInstanceState == null) {
            setNavigationUI();
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setNavigationUI();
    }

    private void setNavigationUI() {
        controller = new NavControllerLiveData(bottomNavigationView, NAV_GRAPH_IDS, getSupportFragmentManager(), R.id.nav_host_container);
        controller.observe(this, this::setNavigationLayout);
    }

    private void setNavigationLayout(@NotNull NavController navController) {
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        if (navController.getGraph().getId() == R.id.auth_nav_graph) {
            bottomNavigationView.setVisibility(View.GONE);
        } else {
            bottomNavigationView.getMenu().removeItem(R.id.auth_nav_graph);
            bottomNavigationView.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public boolean onNavigateUp() {
        NavController currentControl = controller.getValue();
        if (currentControl != null) {
            return currentControl.navigateUp();
        } else {
            return super.onNavigateUp();
        }
    }


}