package ca.dal.bartertrader.data.repository;

import androidx.paging.rxjava3.RxPagingSource;

import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import ca.dal.bartertrader.data.data_source.FirebaseFirestoreDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseStorageDataSource;
import ca.dal.bartertrader.data.model.FirebasePostModel;
import io.reactivex.rxjava3.core.Single;

public class PostPagingSource extends RxPagingSource<QuerySnapshot, FirebasePostModel> {

    private final FirebaseFirestoreDataSource firebaseFirestoreDataSource;
    private final FirebaseStorageDataSource firebaseStorageDataSource;

    public PostPagingSource(FirebaseFirestoreDataSource firebaseFirestoreDataSource, FirebaseStorageDataSource firebaseStorageDataSource) {
        this.firebaseFirestoreDataSource = firebaseFirestoreDataSource;
        this.firebaseStorageDataSource = firebaseStorageDataSource;
    }

    @NotNull
    @Override
    public Single<LoadResult<QuerySnapshot, FirebasePostModel>> loadSingle(@NotNull LoadParams<QuerySnapshot> loadParams) {
        QuerySnapshot key = loadParams.getKey();
        if (key != null) {

        }

        // Get First Five
        // Get Next Five
        // Repeat
        return null;
    }
}
