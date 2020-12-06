package ca.dal.bartertrader.di.repository;

import ca.dal.bartertrader.data.data_source.FirebaseAuthDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseFirestoreDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseStorageDataSource;
import ca.dal.bartertrader.data.repository.FirebasePostsRepository;
import ca.dal.bartertrader.di.Factory;
import ca.dal.bartertrader.di.data_source.ReceiverPostPagingSourceFactory;

public class FirebasePostsRepositoryFactory implements Factory<FirebasePostsRepository> {
    private final FirebaseStorageDataSource firebaseStorageDataSource;
    private final FirebaseFirestoreDataSource firebaseFirestoreDataSource;
    private final FirebaseAuthDataSource firebaseAuthDataSource;
    private final ReceiverPostPagingSourceFactory receiverPostPagingSourceFactory;


    public FirebasePostsRepositoryFactory(FirebaseStorageDataSource firebaseStorageDataSource, FirebaseFirestoreDataSource firebaseFirestoreDataSource, FirebaseAuthDataSource firebaseAuthDataSource, ReceiverPostPagingSourceFactory receiverPostPagingSourceFactory) {
        this.firebaseStorageDataSource = firebaseStorageDataSource;
        this.firebaseFirestoreDataSource = firebaseFirestoreDataSource;
        this.firebaseAuthDataSource = firebaseAuthDataSource;
        this.receiverPostPagingSourceFactory = receiverPostPagingSourceFactory;
    }

    @Override
    public FirebasePostsRepository create() {
        return new FirebasePostsRepository(firebaseFirestoreDataSource, firebaseStorageDataSource, firebaseAuthDataSource, receiverPostPagingSourceFactory);
    }
}
