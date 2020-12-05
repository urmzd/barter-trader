package ca.dal.bartertrader.domain.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class ReviewModel implements Serializable {
    private String offerId;
    private String recipientId;
    private String reviewText;
    private Float rating;
    private String from;

    public ReviewModel() {

    }

    public ReviewModel(String recipientId, String offerId, String reviewText, Float rating, String from) {
        this.offerId = offerId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.from = from;
        this.recipientId = recipientId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
