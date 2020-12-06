package ca.dal.bartertrader.di.view_model.receiver_home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ca.dal.bartertrader.domain.use_case.posts.GetPagedPostsUseCase;
import ca.dal.bartertrader.presentation.view_model.receiver_home.ReceiverHomeViewModel;

public class ReceiverHomeViewModelFactory implements ViewModelProvider.Factory {

    private final GetPagedPostsUseCase getPagedPostsUseCase;

    public ReceiverHomeViewModelFactory(GetPagedPostsUseCase getPagedPostsUseCase) {
        this.getPagedPostsUseCase = getPagedPostsUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ReceiverHomeViewModel.class)) {
            return (T) new ReceiverHomeViewModel(getPagedPostsUseCase);
        }

        throw new IllegalArgumentException("ReceiverHomeViewModel was not provided!");
    }
}
