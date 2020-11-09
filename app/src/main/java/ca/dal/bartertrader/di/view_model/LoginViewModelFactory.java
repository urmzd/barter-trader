package ca.dal.bartertrader.di.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ca.dal.bartertrader.domain.use_case.LoginUserUseCase;
import ca.dal.bartertrader.presentation.view_model.LoginViewModel;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final LoginUserUseCase loginUserUseCase;

    public LoginViewModelFactory(LoginUserUseCase loginUserUseCase) {
        this.loginUserUseCase = loginUserUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(loginUserUseCase);
        }

        throw new IllegalArgumentException("Unknown LoginViewModel was provided!");
    }
}
