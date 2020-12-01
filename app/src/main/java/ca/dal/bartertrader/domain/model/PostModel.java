package ca.dal.bartertrader.domain.model;

import android.net.Uri;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;

@IgnoreExtraProperties
public class PostModel implements Serializable {

    private String authUid;
    private Uri image;
    private String title;
    private String description;

    @ServerTimestamp
    private Timestamp timestamp;

    public PostModel() {

    }

    public PostModel(Uri image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.timestamp = null;
        this.authUid = null;
    }

    @Exclude
    public Uri getImage() {
        return image;
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


    public String getAuthUid() {
        return authUid;
    }

    public void setAuthUid(String authUid) {
        this.authUid = authUid;
    }
}
