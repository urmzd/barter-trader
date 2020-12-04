package ca.dal.bartertrader.utils;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;

public class NavigationUtils {

    public static void setUpToolBar(View view, MaterialToolbar toolbar, int fragmentId) {
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(fragmentId)
                .build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
    }

}
