package ca.dal.bartertrader.di.repository;

import ca.dal.bartertrader.data.data_source.FirebaseAuthDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseFirestoreDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseStorageDataSource;
import ca.dal.bartertrader.data.repository.FirebaseReviewRepository;
import ca.dal.bartertrader.di.Factory;

public class FirebaseReviewRepositoryFactory implements Factory<FirebaseReviewRepository> {
    private final FirebaseStorageDataSource firebaseStorageDataSource;
    private final FirebaseFirestoreDataSource firebaseFirestoreDataSource;
    private final FirebaseAuthDataSource firebaseAuthDataSource;


    public FirebaseReviewRepositoryFactory(FirebaseStorageDataSource firebaseStorageDataSource, FirebaseFirestoreDataSource firebaseFirestoreDataSource, FirebaseAuthDataSource firebaseAuthDataSource) {
        this.firebaseStorageDataSource = firebaseStorageDataSource;
        this.firebaseFirestoreDataSource = firebaseFirestoreDataSource;
        this.firebaseAuthDataSource = firebaseAuthDataSource;
    }

    @Override
    public FirebaseReviewRepository create() {
        return new FirebaseReviewRepository(firebaseFirestoreDataSource, firebaseStorageDataSource, firebaseAuthDataSource);
    }
}