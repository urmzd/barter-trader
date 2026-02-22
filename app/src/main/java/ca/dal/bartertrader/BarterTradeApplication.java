package ca.dal.bartertrader;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class BarterTradeApplication extends Application {

    private static final String TAG = "BarterTradeApp";

    public BarterTraderInjector injector;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Debug build detected — connecting to Firebase Emulators");
            FirebaseAuth.getInstance().useEmulator("10.0.2.2", 9099);
            FirebaseFirestore.getInstance().useEmulator("10.0.2.2", 8080);
            FirebaseStorage.getInstance().useEmulator("10.0.2.2", 9199);
        }

        injector = new BarterTraderInjector();
    }

}
