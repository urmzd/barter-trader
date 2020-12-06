package ca.dal.bartertrader.di.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import ca.dal.bartertrader.di.view_model.HandleReviewViewModelFactory;
import ca.dal.bartertrader.di.view_model.LoginViewModelFactory;
import ca.dal.bartertrader.di.view_model.PasswordResetViewModelFactory;
import ca.dal.bartertrader.di.view_model.ProviderOfferViewModelFactory;
import ca.dal.bartertrader.di.view_model.RegistrationViewModelFactory;
import ca.dal.bartertrader.di.view_model.provider_home.HandlePostViewModelFactory;
import ca.dal.bartertrader.di.view_model.provider_home.ProviderHomeViewModelFactory;
import ca.dal.bartertrader.di.view_model.receiver_home.ReceiverHomeViewModelFactory;
import ca.dal.bartertrader.presentation.view.HandleReviewFragment;
import ca.dal.bartertrader.presentation.view.LoginFragment;
import ca.dal.bartertrader.presentation.view.PasswordResetFragment;
import ca.dal.bartertrader.presentation.view.ProviderOfferFragment;
import ca.dal.bartertrader.presentation.view.RegistrationFragment;
import ca.dal.bartertrader.presentation.view.SwitchHandlerFragment;
import ca.dal.bartertrader.presentation.view.provider_home.HandlePostFragment;
import ca.dal.bartertrader.presentation.view.provider_home.ProviderHomeFragment;
import ca.dal.bartertrader.presentation.view.receiver_home.ReceiverHomeFragment;
import ca.dal.bartertrader.presentation.view.receiver_home.ReceiverHomePostFilterFragment;

public class CustomFragmentFactory extends FragmentFactory {
    private final LoginViewModelFactory loginViewModelFactory;
    private final RegistrationViewModelFactory registrationViewModelFactory;
    private final PasswordResetViewModelFactory passwordResetViewModelFactory;
    private final ProviderHomeViewModelFactory providerHomeViewModelFactory;
    private final HandlePostViewModelFactory handlePostViewModelFactory;
    private final ReceiverHomeViewModelFactory receiverHomeViewModelFactory;
    private final HandleReviewViewModelFactory handleReviewViewModelFactory;
    private final ProviderOfferViewModelFactory providerOfferViewModelFactory;

    public CustomFragmentFactory(LoginViewModelFactory loginViewModelFactory, RegistrationViewModelFactory registrationViewModelFactory, PasswordResetViewModelFactory passwordResetViewModelFactory, ProviderHomeViewModelFactory providerHomeViewModelFactory, HandlePostViewModelFactory handlePostViewModelFactory, ReceiverHomeViewModelFactory receiverHomeViewModelFactory, HandleReviewViewModelFactory handleReviewViewModelFactory, ProviderOfferViewModelFactory providerOfferViewModelFactory) {
        this.loginViewModelFactory = loginViewModelFactory;
        this.registrationViewModelFactory = registrationViewModelFactory;
        this.passwordResetViewModelFactory = passwordResetViewModelFactory;
        this.providerHomeViewModelFactory = providerHomeViewModelFactory;
        this.handlePostViewModelFactory = handlePostViewModelFactory;
        this.receiverHomeViewModelFactory = receiverHomeViewModelFactory;
        this.handleReviewViewModelFactory = handleReviewViewModelFactory;
        this.providerOfferViewModelFactory = providerOfferViewModelFactory;
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

        if (className.equals(HandlePostFragment.class.getName())) {
            return new HandlePostFragment(handlePostViewModelFactory);
        }

        if (className.equals(ReceiverHomeFragment.class.getName())) {
            return new ReceiverHomeFragment(receiverHomeViewModelFactory);
        }

        if (className.equals(SwitchHandlerFragment.class.getName())) {
            return new SwitchHandlerFragment();
        }

        if (className.equals(HandleReviewFragment.class.getName())) {
            return new HandleReviewFragment(handleReviewViewModelFactory);
        }

        if (className.equals(ProviderOfferFragment.class.getName())) {
            return new ProviderOfferFragment(providerOfferViewModelFactory);
        }

        if (className.equals(ReceiverHomePostFilterFragment.class.getName())) {
            return new ReceiverHomePostFilterFragment(receiverHomeViewModelFactory);
        }

        return super.instantiate(classLoader, className);
    }
}
