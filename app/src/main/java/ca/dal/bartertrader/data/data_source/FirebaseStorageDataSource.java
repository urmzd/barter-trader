package ca.dal.bartertrader.data.data_source;

import com.google.firebase.storage.FirebaseStorage;

public class FirebaseStorageDataSource {
    private final FirebaseStorage firebaseStorage;

    public FirebaseStorageDataSource(FirebaseStorage firebaseStorage) {
        this.firebaseStorage = firebaseStorage;
    }
}
