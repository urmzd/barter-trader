package ca.dal.bartertrader.di.data_source;

import com.google.firebase.auth.FirebaseAuth;

import ca.dal.bartertrader.data.data_source.FirebaseAuthDataSource;
import ca.dal.bartertrader.di.Factory;

public class FirebaseAuthDataSourceFactory implements Factory<FirebaseAuthDataSource> {
    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthDataSourceFactory(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public FirebaseAuthDataSource create() {
        return new FirebaseAuthDataSource(firebaseAuth);
    }
}
