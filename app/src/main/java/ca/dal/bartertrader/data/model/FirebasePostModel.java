package ca.dal.bartertrader.data.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FirebasePostModel extends FirestorePostModel {

    private byte[] image;

    public FirebasePostModel() {

    }

    public FirebasePostModel(byte[] image) {
        this.image = image;
    }

    public FirebasePostModel(String postId, String authUid, String title, String description, Long timestamp, byte[] image) {
        super(postId, authUid, title, description, timestamp);
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }
}
