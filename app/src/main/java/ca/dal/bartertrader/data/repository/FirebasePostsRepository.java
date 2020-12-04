package ca.dal.bartertrader.data.repository;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.QuerySnapshot;

import ca.dal.bartertrader.data.data_source.FirebaseAuthDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseFirestoreDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseStorageDataSource;
import ca.dal.bartertrader.domain.model.PostModel;
import ca.dal.bartertrader.utils.handler.resource.Resource;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FirebasePostsRepository {
    private final FirebaseFirestoreDataSource firebaseFirestoreDataSource;
    private final FirebaseStorageDataSource firebaseStorageDataSource;
    private final FirebaseAuthDataSource firebaseAuthDataSource;

    public FirebasePostsRepository(FirebaseFirestoreDataSource firebaseFirestoreDataSource, FirebaseStorageDataSource firebaseStorageDataSource, FirebaseAuthDataSource firebaseAuthDataSource) {
        this.firebaseFirestoreDataSource = firebaseFirestoreDataSource;
        this.firebaseStorageDataSource = firebaseStorageDataSource;
        this.firebaseAuthDataSource = firebaseAuthDataSource;
    }

    public Single<Resource<QuerySnapshot>> getPosts() {

        return null;
    }

    public Completable setPost(PostModel postModel) {
        return firebaseAuthDataSource.reloadUser()
                .subscribeOn(Schedulers.io())
                .andThen(Single.defer(() -> firebaseFirestoreDataSource.addPost(postModel, firebaseAuthDataSource.getUser().getUid())))
                .flatMapCompletable(documentReference -> firebaseStorageDataSource.putPostImageFile(documentReference.getId(), postModel.getImage()));
    }


    public Completable updatePost(PostModel postModel, @NonNull String postUid) {
        return firebaseAuthDataSource.reloadUser()
                .subscribeOn(Schedulers.io())
                .andThen( firebaseFirestoreDataSource.updatePost(postModel, firebaseAuthDataSource.getUser().getUid(), postUid));

    }


}
