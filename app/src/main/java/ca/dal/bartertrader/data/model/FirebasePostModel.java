package ca.dal.bartertrader.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

@IgnoreExtraProperties
public class FirebasePostModel extends FirestorePostModel {

    @DocumentId
    private String postId;

    private byte[] image;
    private String title;
    private String description;
    private String authUid;
    private String providerId;
    @ServerTimestamp
    private Timestamp timestamp;

    public FirebasePostModel() {

    }

    public FirebasePostModel(String postId, String authUid, String title, String description, Timestamp timestamp) {
        super(postId, authUid, title, description, timestamp);
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getAuthUid() {
        return authUid;
    }

    public void setAuthUid(String authUid) {
        this.authUid = authUid;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Override
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getImage() {
        return image;
    }
}
