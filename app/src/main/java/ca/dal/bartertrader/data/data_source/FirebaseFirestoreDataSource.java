package ca.dal.bartertrader.data.data_source;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
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

    public Single<DocumentSnapshot> getUser(String authUid) {
        return Single.create(emitter -> SingleTaskHandler.assign(emitter, userCollection.document(authUid).get()));
    }

    public Completable switchRole(FirebaseUserModel user) {
        return Completable.create(emitter -> CompletableTaskHandler.assign(emitter, userCollection.document(user.getAuthUid()).update("provider", !user.getProvider())));
    }

    public Single<QuerySnapshot> getPosts(DocumentSnapshot startAfter, String title) {
        Query queryToMake = postCollection.limit(10);
        if (startAfter == null) {
            final Query finalQueryToMake = queryToMake;
            return Single.create(emitter -> SingleTaskHandler.assign(emitter, finalQueryToMake.get()));
        }
        if (startAfter != null) {
            final Query finalQueryToMake = queryToMake;
            return Single.create(emitter -> SingleTaskHandler.assign(emitter, finalQueryToMake.startAfter(startAfter).get()));
        }

        if (title != null) {
            queryToMake = queryToMake.whereGreaterThanOrEqualTo("title", title);
        }

        Query finalQueryToMake = queryToMake;
        return Single.create(emitter -> SingleTaskHandler.assign(emitter, finalQueryToMake.get()));
    }
}
