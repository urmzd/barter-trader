package ca.dal.bartertrader.di.data_source;

import com.google.firebase.firestore.FirebaseFirestore;

import ca.dal.bartertrader.data.data_source.FirebaseFirestoreDataSource;
import ca.dal.bartertrader.di.Factory;

public class FirebaseFirestoreDataSourceFactory implements Factory<FirebaseFirestoreDataSource> {
    private final FirebaseFirestore firebaseFirestore;

    public FirebaseFirestoreDataSourceFactory(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public FirebaseFirestoreDataSource create() {
        return new FirebaseFirestoreDataSource(firebaseFirestore);
    }
}
