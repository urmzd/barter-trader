package ca.dal.bartertrader.presentation.view_model;

import androidx.lifecycle.ViewModel;

import ca.dal.bartertrader.data.repository.OfferListLiveData;
import ca.dal.bartertrader.data.repository.ProviderOfferRepositoryCallback;
import ca.dal.bartertrader.domain.model.OfferModel;

@SuppressWarnings("WeakerAccess")
public class ProviderOfferViewModel extends ViewModel {
    private OfferRepository providerOfferRepositoryCallback;

    public ProviderOfferViewModel(ProviderOfferRepositoryCallback providerOfferRepositoryCallback) {
        this.providerOfferRepositoryCallback = providerOfferRepositoryCallback;
    }

    public OfferListLiveData getProductListLiveData() {
        return providerOfferRepositoryCallback.getProductListLiveData();
    }

    public void setStatus(OfferModel offer, String status) {
        providerOfferRepositoryCallback.setStatus(offer, status);
    }


    public interface OfferRepository {
        OfferListLiveData getProductListLiveData();

        void setStatus(OfferModel offer, String status);
    }


}
