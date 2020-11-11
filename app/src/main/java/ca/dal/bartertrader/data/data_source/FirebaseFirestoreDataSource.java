package ca.dal.bartertrader.data.data_source;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import ca.dal.bartertrader.domain.model.PostPOJO;
import ca.dal.bartertrader.utils.handler.async.CompletableTaskHandler;
import ca.dal.bartertrader.utils.handler.async.SingleTaskHandler;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirebaseFirestoreDataSource {
    private final FirebaseFirestore firebaseFirestore;
    private final CollectionReference userCollection;
    private final CollectionReference postCollection;

    public FirebaseFirestoreDataSource(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
        this.userCollection = firebaseFirestore.collection("users");
        this.postCollection = firebaseFirestore.collection("posts");
    }

    public Completable createNewUser(String uid, Boolean role) {
        Map<String, Object> newUser = new HashMap<>();

        newUser.put("role", role);
        newUser.put("created", Timestamp.now());

        return Completable.create(emitter -> CompletableTaskHandler.assign(emitter, userCollection.document(uid).set(newUser, SetOptions.merge())));
    }

    public Single<DocumentReference> addNewPost(PostPOJO post, String uid) {
        Map<String, Object> newPost = new HashMap<>();

        newPost.put("timestamp", FieldValue.serverTimestamp());
        newPost.put("authUid", uid);
        newPost.put("title", post.getTitle());
        newPost.put("description", post.getDescription());

        return Single.create(emitter -> SingleTaskHandler.assign(emitter, postCollection.add(newPost)));
    }

}
