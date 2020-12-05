package ca.dal.bartertrader.domain.model;

public enum OfferStatus {
    PENDING("Pending"),
    ACCEPTED("Accepted"),
    DECLINED("Declined"),
    REVIEW_IN_PROGRESS("Review in progress"),
    COMPLETE("Complete"),
    CLOSED("Closed");


    private String displayName;

    OfferStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
