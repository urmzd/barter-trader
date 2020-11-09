package ca.dal.bartertrader.di.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import ca.dal.bartertrader.di.view_model.LoginViewModelFactory;
import ca.dal.bartertrader.di.view_model.PasswordResetViewModelFactory;
import ca.dal.bartertrader.di.view_model.RegistrationViewModelFactory;
import ca.dal.bartertrader.di.view_model.provider_home.ProviderHomeViewModelFactory;
import ca.dal.bartertrader.presentation.view.LoginFragment;
import ca.dal.bartertrader.presentation.view.PasswordResetFragment;
import ca.dal.bartertrader.presentation.view.RegistrationFragment;
import ca.dal.bartertrader.presentation.view.provider_home.ProviderHomeFragment;

public class CustomFragmentFactory extends FragmentFactory {
    private final LoginViewModelFactory loginViewModelFactory;
    private final RegistrationViewModelFactory registrationViewModelFactory;
    private final PasswordResetViewModelFactory passwordResetViewModelFactory;
    private final ProviderHomeViewModelFactory providerHomeViewModelFactory;

    public CustomFragmentFactory(LoginViewModelFactory loginViewModelFactory, RegistrationViewModelFactory registrationViewModelFactory, PasswordResetViewModelFactory passwordResetViewModelFactory, ProviderHomeViewModelFactory providerHomeViewModelFactory) {
        this.loginViewModelFactory = loginViewModelFactory;
        this.registrationViewModelFactory = registrationViewModelFactory;
        this.passwordResetViewModelFactory = passwordResetViewModelFactory;
        this.providerHomeViewModelFactory = providerHomeViewModelFactory;
    }

    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {

        if (className.equals(LoginFragment.class.getName())) {
            return new LoginFragment(loginViewModelFactory);
        }

        if (className.equals(RegistrationFragment.class.getName())) {
            return new RegistrationFragment(registrationViewModelFactory);
        }

        if (className.equals(PasswordResetFragment.class.getName())) {
            return new PasswordResetFragment(passwordResetViewModelFactory);
        }

        if (className.equals(ProviderHomeFragment.class.getName())) {
            return new ProviderHomeFragment(providerHomeViewModelFactory);
        }

        return super.instantiate(classLoader, className);
    }
}
