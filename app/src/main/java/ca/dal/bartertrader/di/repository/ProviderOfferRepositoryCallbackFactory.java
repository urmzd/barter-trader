package ca.dal.bartertrader.di.repository;

import com.google.firebase.firestore.FirebaseFirestore;

import ca.dal.bartertrader.data.data_source.FirebaseAuthDataSource;
import ca.dal.bartertrader.data.repository.ProviderOfferRepositoryCallback;
import ca.dal.bartertrader.di.Factory;

public class ProviderOfferRepositoryCallbackFactory implements Factory<ProviderOfferRepositoryCallback> {
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseAuthDataSource firebaseAuthDataSource;


    public ProviderOfferRepositoryCallbackFactory(FirebaseFirestore firebaseFirestore, FirebaseAuthDataSource firebaseAuthDataSource) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuthDataSource = firebaseAuthDataSource;
    }

    @Override
    public ProviderOfferRepositoryCallback create() {
        return new ProviderOfferRepositoryCallback(firebaseFirestore, firebaseAuthDataSource);
    }
}