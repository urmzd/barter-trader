package ca.dal.bartertrader.di.view_model.provider_home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ca.dal.bartertrader.domain.use_case.posts.GetPostsUseCase;
import ca.dal.bartertrader.domain.use_case.users.SwitchRoleUseCase;
import ca.dal.bartertrader.presentation.view_model.provider_home.ProviderHomeViewModel;

public class ProviderHomeViewModelFactory implements ViewModelProvider.Factory {
    private final GetPostsUseCase getPostsUseCase;
    private final SwitchRoleUseCase switchRoleUseCase;

    public ProviderHomeViewModelFactory(GetPostsUseCase getPostsUseCase, SwitchRoleUseCase switchRoleUseCase) {
        this.getPostsUseCase = getPostsUseCase;
        this.switchRoleUseCase = switchRoleUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProviderHomeViewModel.class)) {
            return (T) new ProviderHomeViewModel(getPostsUseCase, switchRoleUseCase);
        }

        throw new IllegalArgumentException("ProviderHomeViewModel was not provided!");
    }
}
