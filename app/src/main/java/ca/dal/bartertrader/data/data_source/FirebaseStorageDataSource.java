package ca.dal.bartertrader.data.data_source;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import ca.dal.bartertrader.utils.handler.async.CompletableTaskHandler;
import ca.dal.bartertrader.utils.handler.async.SingleTaskHandler;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirebaseStorageDataSource {
    private final long TWO_MEGABYTES = 2 * 1024 * 1024;
    private final FirebaseStorage firebaseStorage;
    private final StorageReference storageReference;

    public FirebaseStorageDataSource(FirebaseStorage firebaseStorage) {
        this.firebaseStorage = firebaseStorage;
        this.storageReference = firebaseStorage.getReference().child("posts");
    }

    public Completable putPostImageFile(String postUid, Uri imageLocation) {
        StorageReference currentPostReference = storageReference.child(String.format("%s.jpg", postUid));
        return Completable.create(emitter -> CompletableTaskHandler.assign(emitter, currentPostReference.putFile(imageLocation)));
    }

    public Single<byte[]> getPostImageBytes(String postUid) {
        StorageReference currentPostReference = storageReference.child(String.format("%s.jpg", postUid));
        return Single.create(emitter -> SingleTaskHandler.assign(emitter, currentPostReference.getBytes(TWO_MEGABYTES)));
    }

}
