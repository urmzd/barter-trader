package ca.dal.bartertrader.di.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ca.dal.bartertrader.domain.use_case.ResetPasswordUseCase;
import ca.dal.bartertrader.presentation.view_model.PasswordResetViewModel;

public class PasswordResetViewModelFactory implements ViewModelProvider.Factory {

    private final ResetPasswordUseCase resetPasswordUseCase;

    public PasswordResetViewModelFactory(ResetPasswordUseCase resetPasswordUseCase) {
        this.resetPasswordUseCase = resetPasswordUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PasswordResetViewModel.class)) {
            return (T) new PasswordResetViewModel(resetPasswordUseCase);
        }

        throw new IllegalArgumentException("Unknown PasswordResetViewModel was provided!");
    }
}
