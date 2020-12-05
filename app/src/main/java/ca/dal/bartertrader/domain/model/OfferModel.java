package ca.dal.bartertrader.domain.model;

import java.io.Serializable;

import ca.dal.bartertrader.data.model.FirestorePostModel;

public class OfferModel implements Serializable {
    private String id;
    private FirestorePostModel providerPost;
    private FirestorePostModel receiverPost;
    private OfferStatus status;

    public OfferModel() {

    }

    public OfferModel(String id, FirestorePostModel providerPost, FirestorePostModel receiverPost, OfferStatus status) {
        this.id = id;
        this.providerPost = providerPost;
        this.receiverPost = receiverPost;
        this.status = status;
    }


    public FirestorePostModel getProviderPost() {
        return providerPost;
    }

    public void setProviderPost(FirestorePostModel providerPost) {
        this.providerPost = providerPost;
    }

    public FirestorePostModel getReceiverPost() {
        return receiverPost;
    }

    public void setReceiverPost(FirestorePostModel receiverPost) {
        this.receiverPost = receiverPost;
    }


    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
