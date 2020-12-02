package ca.dal.bartertrader.domain.model;

import android.net.Uri;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class PostModel implements Serializable {

    private Uri image;
    private String title;
    private String description;
    private Timestamp timestamp;
    private String authUid;
    private String imageName;

    public PostModel() {

    }

    public PostModel(Uri image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;

    }

    public Uri getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getAuthUid() {
        return authUid;
    }

    public void setAuthUid(String authUid) {
        this.authUid = authUid;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
