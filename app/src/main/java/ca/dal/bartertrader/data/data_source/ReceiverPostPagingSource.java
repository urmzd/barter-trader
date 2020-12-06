package ca.dal.bartertrader.data.data_source;

import android.util.Log;

import androidx.paging.rxjava3.RxPagingSource;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import ca.dal.bartertrader.data.model.FirebasePostModel;
import ca.dal.bartertrader.utils.handler.async.SingleTaskHandler;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReceiverPostPagingSource extends RxPagingSource<String, FirebasePostModel> {

    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseStorage firebaseStorage;
    private final String query;
    private final long FIVE_MEGABYTES = 5 * 1024 * 1024;

    public ReceiverPostPagingSource(String query, FirebaseFirestore firebaseFirestore, FirebaseStorage firebaseStorage) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseStorage = firebaseStorage;
        this.query = query;
    }

    private Single<QuerySnapshot> getPosts(String lastPostId, String title) {
        Query queryToMake = firebaseFirestore.collection("posts").limit(10);

        if (title != null) {
            queryToMake = queryToMake.whereGreaterThanOrEqualTo("title", title);
        }

        if (lastPostId != null) {
            final Query finalQueryToMake = queryToMake;
            return Single.create(emitter -> SingleTaskHandler.assign(emitter, firebaseFirestore.collection("posts").document(lastPostId).get()))
                    .flatMap(document -> Single.create(emitter -> SingleTaskHandler.assign(emitter, finalQueryToMake.startAfter(document).get())));
        }

        final Query finalQueryToMake = queryToMake;
        return Single.create(emitter -> SingleTaskHandler.assign(emitter, finalQueryToMake.get()));
    }

    private Single<List<byte[]>> getPostsImageBytes(QuerySnapshot snapshots) {
        return Observable.fromIterable(snapshots)
                .flatMapSingle(snapshot -> Single.<byte[]>create(emitter -> SingleTaskHandler.assign(emitter,
                        firebaseStorage.getReference()
                                .child("posts")
                                .child(String.format("%s.jpg", snapshot.getId()))
                                .getBytes(FIVE_MEGABYTES))))
                .toList();
    }

    @NotNull
    @Override
    public Single<LoadResult<String, FirebasePostModel>> loadSingle(@NotNull LoadParams<String> loadParams) {
        AtomicReference<QuerySnapshot> querySnapshotAtomicReference = new AtomicReference<>();
        return this.getPosts(loadParams.getKey(), query).subscribeOn(Schedulers.io()).flatMap(queryDocumentSnapshots -> {
            querySnapshotAtomicReference.set(queryDocumentSnapshots);
            return this.getPostsImageBytes(queryDocumentSnapshots);
        }).map(imagesReturned -> {
            List<FirebasePostModel> posts = querySnapshotAtomicReference.get().toObjects(FirebasePostModel.class);
            for (int index = 0; index < imagesReturned.size(); index++) {
                posts.get(index).setImage(imagesReturned.get(0));
            }
            return posts;
        }).map(this::toLoadResult).onErrorReturn(LoadResult.Error::new);
    }

    private LoadResult<String, FirebasePostModel> toLoadResult(List<FirebasePostModel> response) {
        return new LoadResult.Page<>(
                response,
                null,
                response.get(response.size() - 1).getPostId(),
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }
}
