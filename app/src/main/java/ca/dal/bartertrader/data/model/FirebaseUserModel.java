package ca.dal.bartertrader.data.model;

import com.google.firebase.Timestamp;
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

    public FirebaseUserModel() {
    }

    public FirebaseUserModel(Boolean provider) {
        this.provider = provider;
        this.timestamp = null;
    }

    public FirebaseUserModel(Boolean provider, Timestamp timeStamp) {
        this.provider = provider;
        this.timestamp = timeStamp;
    }

    public Boolean isProvider() {
        return provider;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getAuthUid() {
        return authUid;
    }
}
