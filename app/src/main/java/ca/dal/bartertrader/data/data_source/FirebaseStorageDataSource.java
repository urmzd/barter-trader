package ca.dal.bartertrader.data.data_source;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import ca.dal.bartertrader.utils.handler.async.CompletableTaskHandler;
import io.reactivex.rxjava3.core.Completable;

public class FirebaseStorageDataSource {
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

}
