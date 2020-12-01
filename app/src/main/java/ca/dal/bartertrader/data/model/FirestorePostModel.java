package ca.dal.bartertrader.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class FirestorePostModel implements Serializable {

    @DocumentId
    private String postId;

    private String authUid;
    private String title;
    private String description;
    private Timestamp timestamp;

    public FirestorePostModel() {
    }

    public FirestorePostModel(String postId, String authUid, String title, String description, Timestamp timestamp) {
        this.postId = postId;

        this.authUid = authUid;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getPostId() {
        return postId;
    }

    public String getAuthUid() {
        return authUid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
