package ca.dal.bartertrader.data.repository;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import ca.dal.bartertrader.data.data_source.FirebaseAuthDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseFirestoreDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseStorageDataSource;
import ca.dal.bartertrader.domain.model.PostModel;
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

    public Single<List<byte[]>> getPostImages(QuerySnapshot snapshots) {
        return firebaseStorageDataSource.getPostsImageBytes(snapshots).subscribeOn(Schedulers.io());
    }

    public Task<QuerySnapshot> getPosts() {
        return firebaseFirestoreDataSource.getPosts(firebaseAuthDataSource.getUser().getUid());
    }

    public Single<QuerySnapshot> getPosts(DocumentSnapshot snapshot, String title) {
        return firebaseAuthDataSource.reloadUser().subscribeOn(Schedulers.io())
                .andThen(Single.defer(() -> firebaseFirestoreDataSource.getPosts(snapshot, title)));
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
}
