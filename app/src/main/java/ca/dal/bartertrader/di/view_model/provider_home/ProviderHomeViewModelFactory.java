package ca.dal.bartertrader.di.view_model.provider_home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ca.dal.bartertrader.domain.use_case.posts.GetPostsUseCase;
import ca.dal.bartertrader.presentation.view_model.provider_home.ProviderHomeViewModel;

public class ProviderHomeViewModelFactory implements ViewModelProvider.Factory {
    private final GetPostsUseCase getPostsUseCase;

    public ProviderHomeViewModelFactory(GetPostsUseCase getPostsUseCase) {
        this.getPostsUseCase = getPostsUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProviderHomeViewModel.class)) {
            return (T) new ProviderHomeViewModel(getPostsUseCase);
        }

        throw new IllegalArgumentException("ProviderHomeViewModel was not provided!");
    }
}
