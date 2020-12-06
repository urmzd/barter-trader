package ca.dal.bartertrader.utils;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class NavigationUtils {

    public static void setUpToolBar(View view, View toolbar, int fragmentId) {
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(fragmentId)
                .build();
        NavigationUI.setupWithNavController((Toolbar) toolbar, navController, appBarConfiguration);
    }

}
