package ca.dal.bartertrader.di.repository;

import ca.dal.bartertrader.data.data_source.FirebaseAuthDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseFirestoreDataSource;
import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.di.Factory;

public class FirebaseUserRepositoryFactory implements Factory<FirebaseUserRepository> {

    private final FirebaseAuthDataSource firebaseAuthDataSource;
    private final FirebaseFirestoreDataSource firebaseFirestoreDataSource;

    public FirebaseUserRepositoryFactory(FirebaseAuthDataSource firebaseAuthDataSource, FirebaseFirestoreDataSource firebaseFirestoreDataSource) {
        this.firebaseAuthDataSource = firebaseAuthDataSource;
        this.firebaseFirestoreDataSource = firebaseFirestoreDataSource;
    }

    @Override
    public FirebaseUserRepository create() {
        return new FirebaseUserRepository(firebaseAuthDataSource, firebaseFirestoreDataSource);
    }
}
