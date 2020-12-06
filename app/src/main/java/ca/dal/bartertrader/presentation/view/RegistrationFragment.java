package ca.dal.bartertrader.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import ca.dal.bartertrader.R;
import ca.dal.bartertrader.databinding.FragmentRegistrationBinding;
import ca.dal.bartertrader.di.view_model.RegistrationViewModelFactory;
import ca.dal.bartertrader.presentation.view_model.RegistrationViewModel;
import ca.dal.bartertrader.utils.BindingUtils;
import ca.dal.bartertrader.utils.NavigationUtils;
import ca.dal.bartertrader.utils.handler.resource.Status;

public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;
    public RegistrationViewModel viewModel;
    private final RegistrationViewModelFactory registrationViewModelFactory;

    public RegistrationFragment(RegistrationViewModelFactory registrationViewModelFactory) {
        this.registrationViewModelFactory = registrationViewModelFactory;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, registrationViewModelFactory).get(RegistrationViewModel.class);

        binding = FragmentRegistrationBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);

        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        NavigationUtils.setUpToolBar(getView(), binding.toolbar, R.id.loginFragment);

        viewModel.getFirstNameIsValid().observe(getViewLifecycleOwner(), validity -> {
            BindingUtils.setErrorOnTextInputLayout(binding.registrationFirstName, validity, "Names cannot have numbers or special characters in them.");
        });

        viewModel.getLastNameIsValid().observe(getViewLifecycleOwner(), validity -> {
            BindingUtils.setErrorOnTextInputLayout(binding.registrationLastName, validity, "Names cannot have numbers or special characters in them.");
        });

        viewModel.getEmailIsValid().observe(getViewLifecycleOwner(), validity -> {
            BindingUtils.setErrorOnTextInputLayout(binding.registrationEmail, validity, getString(R.string.error_email_invalid));
        });

        viewModel.getPasswordIsValid().observe(getViewLifecycleOwner(), validity -> {
            BindingUtils.setErrorOnTextInputLayout(binding.registrationPassword, validity, getString(R.string.error_password_weak));
        });

        viewModel.getConfirmedPasswordIsValid().observe(getViewLifecycleOwner(), validity -> {
            BindingUtils.setErrorOnTextInputLayout(binding.registrationConfirmedPassword, validity, getString(R.string.error_password_no_match));
        });

        viewModel.getGoToLoginLiveEvent().observe(getViewLifecycleOwner(), __ -> {
            Navigation.findNavController(getView()).navigateUp();
        });

        viewModel.getRegistrationResult().observe(getViewLifecycleOwner(), response -> {
            Status status = response.getStatus();

            if (status == Status.FULFILLED) {
                Toast.makeText(getContext(), "Registration Successful! An email was sent to your account!", Toast.LENGTH_LONG).show();
                Navigation.findNavController(getView()).navigateUp();
                return;
            }

            if (status == Status.REJECTED) {
                Toast.makeText(getContext(), response.getError().getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }
}
