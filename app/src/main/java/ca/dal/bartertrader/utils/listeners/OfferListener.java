package ca.dal.bartertrader.utils.listeners;

import ca.dal.bartertrader.domain.model.OfferModel;

public interface OfferListener {
    void onAcceptDeclineClick(OfferModel offer, String status);

    void onReviewClick(OfferModel offerModel);
}
