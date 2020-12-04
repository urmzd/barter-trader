package ca.dal.bartertrader.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

@IgnoreExtraProperties
public class FirebaseUserModel {

    @DocumentId
    private String authUid;

    private Boolean provider;

    @ServerTimestamp
    private Timestamp timestamp;

    private FirebaseUser firebaseUser;

    public FirebaseUserModel() {
    }

    public FirebaseUserModel(Boolean provider) {
        this.provider = provider;
        this.timestamp = null;
    }

    public FirebaseUserModel(Boolean provider, Timestamp timestamp) {
        this.provider = provider;
        this.timestamp = timestamp;
    }

    public FirebaseUserModel(Boolean provider, Timestamp timestamp, String authUid) {
        this.provider = provider;
        this.timestamp = timestamp;
        this.authUid = authUid;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Boolean getProvider() {
        return provider;
    }

    public String getAuthUid() {
        return authUid;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }
}
