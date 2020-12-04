package ca.dal.bartertrader.di.view_model.provider_home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ca.dal.bartertrader.domain.use_case.posts.SetPostBaseUseCase;
import ca.dal.bartertrader.domain.use_case.posts.UpdatePostBaseUseCase;
import ca.dal.bartertrader.presentation.view_model.provider_home.EditPostViewModel;

public class EditPostViewModelFactory implements ViewModelProvider.Factory {

    private final UpdatePostBaseUseCase updatePostBaseUseCase;

    public EditPostViewModelFactory(UpdatePostBaseUseCase updatePostBaseUseCase) {
        this.updatePostBaseUseCase = updatePostBaseUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EditPostViewModel.class)) {
            return (T) new EditPostViewModel(updatePostBaseUseCase);
        }

        throw new IllegalArgumentException("HandlePostViewModel was not provided!");
    }
}
