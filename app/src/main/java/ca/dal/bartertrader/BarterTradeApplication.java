package ca.dal.bartertrader;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class BarterTradeApplication extends Application {


    public BarterTraderInjector injector;
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        injector = new BarterTraderInjector();
    }

}
