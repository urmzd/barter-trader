package ca.dal.bartertrader.di.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ca.dal.bartertrader.domain.use_case.users.RegisterUseCase;
import ca.dal.bartertrader.domain.use_case.VerifyEmailExistsUseCase;
import ca.dal.bartertrader.presentation.view_model.RegistrationViewModel;

public class RegistrationViewModelFactory implements ViewModelProvider.Factory {

    private final RegisterUseCase registerUserUseCase;
    private final VerifyEmailExistsUseCase verifyEmailExistsUseCase;

    public RegistrationViewModelFactory(RegisterUseCase registerUserUseCase, VerifyEmailExistsUseCase verifyEmailExistsUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.verifyEmailExistsUseCase = verifyEmailExistsUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistrationViewModel.class)) {
            return (T) new RegistrationViewModel(registerUserUseCase, verifyEmailExistsUseCase);
        }

        throw new IllegalArgumentException("RegistrationViewModel was not provided!");
    }
}
