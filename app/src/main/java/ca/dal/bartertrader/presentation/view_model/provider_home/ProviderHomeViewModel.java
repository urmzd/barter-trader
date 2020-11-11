package ca.dal.bartertrader.presentation.view_model.provider_home;

import androidx.lifecycle.ViewModel;

import ca.dal.bartertrader.domain.use_case.posts.GetPostsUseCase;

public class ProviderHomeViewModel extends ViewModel {

    private final GetPostsUseCase getPostsUseCase;

    public ProviderHomeViewModel(GetPostsUseCase getPostsUseCase) {
        this.getPostsUseCase = getPostsUseCase;
    }

}
