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
    private double lat;
    private double lon;

    @ServerTimestamp
    private Timestamp timestamp;

    public PostModel() {

    }

    public PostModel(Uri image, String title, String description, double lat, double lon) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
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

    public double getLat() { return lat; }

    public double getLon() { return lon; }

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
