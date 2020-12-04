package ca.dal.bartertrader.data.model;

import ca.dal.bartertrader.domain.model.OfferModel;

public class Operation {
    OfferModel offer;
    int type;

    public Operation(OfferModel offer, int type) {
        this.offer = offer;
        this.type = type;
    }

    public OfferModel getOffer() {
        return offer;
    }

    public void setOffer(OfferModel offer) {
        this.offer = offer;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
