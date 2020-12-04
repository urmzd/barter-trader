package ca.dal.bartertrader.di.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ca.dal.bartertrader.domain.use_case.reviews.SetReviewUseCase;
import ca.dal.bartertrader.presentation.view_model.HandleReviewViewModel;

public class HandleReviewViewModelFactory implements ViewModelProvider.Factory {

    private final SetReviewUseCase setReviewUseCase;

    public HandleReviewViewModelFactory(SetReviewUseCase setReviewUseCase) {
        this.setReviewUseCase = setReviewUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HandleReviewViewModel.class)) {
            return (T) new HandleReviewViewModel(setReviewUseCase);
        }

        throw new IllegalArgumentException("HandleReviewViewModel was not provided!");
    }
}