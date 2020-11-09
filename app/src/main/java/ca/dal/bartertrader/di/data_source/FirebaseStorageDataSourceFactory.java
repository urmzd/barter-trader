package ca.dal.bartertrader.di.data_source;

import com.google.firebase.storage.FirebaseStorage;

import ca.dal.bartertrader.data.data_source.FirebaseStorageDataSource;
import ca.dal.bartertrader.di.Factory;

public class FirebaseStorageDataSourceFactory implements Factory<FirebaseStorageDataSource> {
    private final FirebaseStorage firebaseStorage;

    public FirebaseStorageDataSourceFactory(FirebaseStorage firebaseStorage) {
        this.firebaseStorage = firebaseStorage;
    }

    @Override
    public FirebaseStorageDataSource create() {
        return new FirebaseStorageDataSource(firebaseStorage);
    }
}
