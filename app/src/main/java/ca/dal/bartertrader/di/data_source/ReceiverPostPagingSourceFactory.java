package ca.dal.bartertrader.di.data_source;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import ca.dal.bartertrader.data.data_source.ReceiverPostPagingSource;
import ca.dal.bartertrader.di.Factory;
import ca.dal.bartertrader.domain.model.ReceiverPostQuery;

public class ReceiverPostPagingSourceFactory implements Factory<ReceiverPostPagingSource> {
    private final FirebaseStorage firebaseStorage;
    private final FirebaseFirestore firebaseFirestore;
    private ReceiverPostQuery query;

    public ReceiverPostPagingSourceFactory(FirebaseStorage firebaseStorage, FirebaseFirestore firebaseFirestore, ReceiverPostQuery query) {
        this.firebaseStorage = firebaseStorage;
        this.firebaseFirestore = firebaseFirestore;
        this.query = query;
    }

    @Override
    public ReceiverPostPagingSource create() {
        return new ReceiverPostPagingSource(query, firebaseFirestore, firebaseStorage);
    }

    public void changeQuery(ReceiverPostQuery query) {
        this.query = query;
    }
}
