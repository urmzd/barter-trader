package ca.dal.bartertrader.presentation.view.profile;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Review {
    private String from;
    private String message;
    private float rating;
    private Date timestamp;
    private String to;

    public Review() {}

    public Review(String name, String review, float rating) {
        from = name;
        this.message = review;
        this.rating = rating;
    }

    public String getFrom() {return from;}
    public void setFrom(String name) { from = name;}
    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}
    public float getRating() {return rating;}
    public void setRating(float rating) {this.rating = rating;}
    public String getTo() {return to;}
    public void setTo(String to) {this.to = to;}

    @ServerTimestamp
    public Date getTimestamp() {return timestamp;}
    public void setTimestamp(Date timestamp) {this.timestamp = timestamp;}
}

