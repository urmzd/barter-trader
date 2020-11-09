package ca.dal.bartertrader;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BarterTraderInjector injector = ((BarterTradeApplication) getApplication()).injector;
        getSupportFragmentManager().setFragmentFactory(injector.customFragmentFactory);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}