package ca.dal.bartertrader.data.data_source;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import ca.dal.bartertrader.data.model.FirebaseUserModel;
import ca.dal.bartertrader.domain.model.PostModel;
import ca.dal.bartertrader.utils.handler.async.CompletableTaskHandler;
import ca.dal.bartertrader.utils.handler.async.SingleTaskHandler;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirebaseFirestoreDataSource {
    private final CollectionReference userCollection;
    private final CollectionReference postCollection;

    public FirebaseFirestoreDataSource(FirebaseFirestore firebaseFirestore) {
        this.userCollection = firebaseFirestore.collection("users");
        this.postCollection = firebaseFirestore.collection("posts");
    }

    public Completable createUser(String uid, Boolean role) {
        return Completable.create(emitter -> CompletableTaskHandler.assign(emitter, userCollection.document(uid).set(new FirebaseUserModel(role), SetOptions.merge())));
    }

    public Single<DocumentReference> addPost(@NonNull PostModel postModel, @NonNull String authUid) {
        postModel.setAuthUid(authUid);

        return Single.create(emitter -> SingleTaskHandler.assign(emitter, postCollection.add(postModel)));
    }

    public Completable updatePost(@NonNull PostModel postModel, @NonNull String authUid, @NonNull String postUid)
    {
        postModel.setAuthUid(authUid);
        return Completable.create(emitter -> CompletableTaskHandler.assign(emitter,
                postCollection.document(postUid).update(
                        "image", postModel.getImage(),
                        "title", postModel.getTitle(),
                        "description", postModel.getDescription()
                )
        ));
    }

    public Single<DocumentSnapshot> getUser(String authUid) {
        return Single.create(emitter -> SingleTaskHandler.assign(emitter, userCollection.document(authUid).get()));
    }

    public Completable switchRole(FirebaseUserModel user) {
        return Completable.create(emitter -> CompletableTaskHandler.assign(emitter, userCollection.document(user.getAuthUid()).update("provider", !user.isProvider())));
    }

}
