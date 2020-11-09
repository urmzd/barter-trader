package ca.dal.bartertrader.data.repository;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import ca.dal.bartertrader.data.data_source.FirebaseFirestoreDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseStorageDataSource;
import ca.dal.bartertrader.domain.model.PostPOJO;
import ca.dal.bartertrader.utils.handler.resource.Resource;

public class FirebasePostsRepository {
    private final FirebaseFirestoreDataSource firebaseFirestoreDataSource;
    private final FirebaseStorageDataSource firebaseStorageDataSource;

    public FirebasePostsRepository(FirebaseFirestoreDataSource firebaseFirestoreDataSource, FirebaseStorageDataSource firebaseStorageDataSource) {
        this.firebaseFirestoreDataSource = firebaseFirestoreDataSource;
        this.firebaseStorageDataSource = firebaseStorageDataSource;
    }

    public LiveData<Resource<QueryDocumentSnapshot>> getPosts(int numberOfItems) {
        return null;
    }

    public LiveData<Resource<Void>> setPost(PostPOJO post) {
        return null;
    }
}
