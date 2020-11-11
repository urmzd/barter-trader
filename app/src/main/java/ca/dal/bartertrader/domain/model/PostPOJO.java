package ca.dal.bartertrader.domain.model;

import android.net.Uri;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class PostPOJO implements Serializable {

    private Uri image;
    private String title;
    private String description;

    public PostPOJO() {

    }

    public PostPOJO(Uri image, String title, String description) {
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

}
