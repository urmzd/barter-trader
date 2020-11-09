package ca.dal.bartertrader.data.data_source;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import ca.dal.bartertrader.utils.handler.async.CompletableTaskHandler;
import io.reactivex.rxjava3.core.Completable;

public class FirebaseFirestoreDataSource {
    private final FirebaseFirestore firebaseFirestore;

    public FirebaseFirestoreDataSource(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    public Completable createNewUser(String uid, Boolean role) {
        Map<String, Object> newUser = new HashMap<>();

        newUser.put("role", role);
        newUser.put("created", Timestamp.now());

        return Completable.create(emitter -> CompletableTaskHandler.assign(emitter, firebaseFirestore.collection("users").document(uid).set(newUser, SetOptions.merge())));
    }

}
