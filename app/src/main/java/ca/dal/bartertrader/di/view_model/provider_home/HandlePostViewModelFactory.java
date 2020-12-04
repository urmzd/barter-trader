package ca.dal.bartertrader.di.view_model.provider_home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ca.dal.bartertrader.domain.use_case.posts.SetPostUseCase;
import ca.dal.bartertrader.presentation.view_model.provider_home.HandlePostViewModel;

public class HandlePostViewModelFactory implements ViewModelProvider.Factory {

    private final SetPostUseCase setPostUseCase;

    public HandlePostViewModelFactory(SetPostUseCase setPostUseCase) {
        this.setPostUseCase = setPostUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HandlePostViewModel.class)) {
            return (T) new HandlePostViewModel(setPostUseCase);
        }

        throw new IllegalArgumentException("HandlePostViewModel was not provided!");
    }
}
