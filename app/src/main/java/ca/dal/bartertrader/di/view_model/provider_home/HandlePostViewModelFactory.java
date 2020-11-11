package ca.dal.bartertrader.di.view_model.provider_home;

import android.app.Activity;

import androidx.activity.result.ActivityResultRegistry;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ca.dal.bartertrader.domain.use_case.posts.SetPostBaseUseCase;
import ca.dal.bartertrader.presentation.view_model.provider_home.HandlePostViewModel;

public class HandlePostViewModelFactory implements ViewModelProvider.Factory {

    private final SetPostBaseUseCase setPostBaseUseCase;

    public HandlePostViewModelFactory(SetPostBaseUseCase setPostBaseUseCase) {
        this.setPostBaseUseCase = setPostBaseUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HandlePostViewModel.class)) {
            return (T) new HandlePostViewModel(setPostBaseUseCase);
        }

        throw new IllegalArgumentException("HandlePostViewModel was not provided!");
    }
}
