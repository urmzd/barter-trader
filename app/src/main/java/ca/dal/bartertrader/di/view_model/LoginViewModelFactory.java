package ca.dal.bartertrader.di.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ca.dal.bartertrader.domain.use_case.users.LoginUseCase;
import ca.dal.bartertrader.presentation.view_model.LoginViewModel;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final LoginUseCase loginUseCase;

    public LoginViewModelFactory(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(loginUseCase);
        }

        throw new IllegalArgumentException("LoginViewModel was not provided!");
    }
}
