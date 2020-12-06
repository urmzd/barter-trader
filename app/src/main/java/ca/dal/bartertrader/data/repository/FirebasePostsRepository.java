package ca.dal.bartertrader.data.repository;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import ca.dal.bartertrader.data.data_source.FirebaseAuthDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseFirestoreDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseStorageDataSource;
import ca.dal.bartertrader.data.data_source.ReceiverPostPagingSource;
import ca.dal.bartertrader.data.model.FirebasePostModel;
import ca.dal.bartertrader.di.data_source.ReceiverPostPagingSourceFactory;
import ca.dal.bartertrader.domain.model.PostModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FirebasePostsRepository {
    private final FirebaseFirestoreDataSource firebaseFirestoreDataSource;
    private final FirebaseStorageDataSource firebaseStorageDataSource;
    private final FirebaseAuthDataSource firebaseAuthDataSource;
    private final ReceiverPostPagingSourceFactory receiverPostPagingSourceFactory;

    public FirebasePostsRepository(FirebaseFirestoreDataSource firebaseFirestoreDataSource, FirebaseStorageDataSource firebaseStorageDataSource, FirebaseAuthDataSource firebaseAuthDataSource, ReceiverPostPagingSourceFactory receiverPostPagingSource) {
        this.firebaseFirestoreDataSource = firebaseFirestoreDataSource;
        this.firebaseStorageDataSource = firebaseStorageDataSource;
        this.firebaseAuthDataSource = firebaseAuthDataSource;
        this.receiverPostPagingSourceFactory = receiverPostPagingSource;
    }

    public Task<QuerySnapshot> getPosts() {
        return firebaseFirestoreDataSource.getPosts(firebaseAuthDataSource.getUser().getUid());
    }

    public Completable setPost(PostModel postModel) {
        if (postModel.getPostId() != null) {
            return firebaseAuthDataSource.reloadUser()
                    .subscribeOn(Schedulers.io())
                    .andThen(firebaseFirestoreDataSource.setPost(postModel))
                    .andThen(firebaseStorageDataSource.putPostImageFile(postModel.getPostId(), postModel.getImage()));
        } else {
            return firebaseAuthDataSource.reloadUser()
                    .subscribeOn(Schedulers.io())
                    .andThen(Single.defer(() -> firebaseFirestoreDataSource.addPost(postModel, firebaseAuthDataSource.getUser().getUid())))
                    .flatMapCompletable(documentReference -> firebaseStorageDataSource.putPostImageFile(documentReference.getId(), postModel.getImage()));
        }
    }

    public Flowable<PagingData<FirebasePostModel>> getPagedPost(String query) {
        ReceiverPostPagingSourceFactory temp = receiverPostPagingSourceFactory;
        temp.changeQuery(query);
        ReceiverPostPagingSource pagingSource = temp.create();
        Pager<String, FirebasePostModel> pager = new Pager<>(
                new PagingConfig(20),
                () -> pagingSource
        );
        return PagingRx.getFlowable(pager);
    }
}
