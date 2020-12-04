package ca.dal.bartertrader.di.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ca.dal.bartertrader.data.repository.ProviderOfferRepositoryCallback;
import ca.dal.bartertrader.presentation.view_model.HandleReviewViewModel;
import ca.dal.bartertrader.presentation.view_model.ProviderOfferViewModel;

public class ProviderOfferViewModelFactory implements ViewModelProvider.Factory {
    private ProviderOfferRepositoryCallback offerRepositoryCallback;

    public ProviderOfferViewModelFactory(ProviderOfferRepositoryCallback offerRepositoryCallback) {
        this.offerRepositoryCallback = offerRepositoryCallback;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProviderOfferViewModel.class)) {
            return (T) new ProviderOfferViewModel(offerRepositoryCallback);
        }

        throw new IllegalArgumentException("ProviderOfferViewModel was not provided!");
    }
}
