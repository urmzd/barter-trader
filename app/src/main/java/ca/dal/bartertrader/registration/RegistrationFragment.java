package ca.dal.bartertrader.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import ca.dal.bartertrader.R;
import ca.dal.bartertrader.SingleEvent;
import ca.dal.bartertrader.databinding.FragmentRegistrationBinding;

public class RegistrationFragment extends Fragment {
private FragmentRegistrationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RegistrationViewModel model = new ViewModelProvider(RegistrationFragment.this).get(RegistrationViewModel.class);

        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel(model);

        model.getShowEmailError().observe(getViewLifecycleOwner(), emailObserver() );
        model.getShowPasswordError().observe(getViewLifecycleOwner(), passwordObserver() );
        model.getShowConfirmationPasswordError().observe(getViewLifecycleOwner(), confirmationPasswordObserver() );
        model.getSendRegistrationEmailEvent().observe(getViewLifecycleOwner(), emailRegistrationResponseObserver());
        model.getAccountRegistrationEvent().observe(getViewLifecycleOwner(), accountRegistrationResponseObserver());
    }

    public final Observer<Boolean> emailObserver() {
        TextInputLayout registrationEmailInput = binding.email;
        return showEmailError -> {
            Log.d("emailError", showEmailError.toString());
            if(showEmailError == registrationEmailInput.isErrorEnabled()){
                return;
            }
            registrationEmailInput.setErrorEnabled(showEmailError);
            registrationEmailInput.setError(showEmailError ? getString(R.string.error_invalid_email): null);
        };
    }

    public final Observer<Boolean> passwordObserver() {
        TextInputLayout registrationPasswordInput = binding.password;
        return showPasswordError -> {
            Log.d("passwordError", showPasswordError.toString());
            if(showPasswordError == registrationPasswordInput.isErrorEnabled()){
                return;
            }
            registrationPasswordInput.setErrorEnabled(showPasswordError);
            registrationPasswordInput.setError(showPasswordError ? getString(R.string.error_invalid_password): null);
        };
    }

    public final Observer<Boolean> confirmationPasswordObserver() {
        TextInputLayout registrationPasswordInput = binding.confirmedPassword;
        return showConfirmationPasswordError -> {
            Log.d("confirmPasswordError", showConfirmationPasswordError.toString());
            if(showConfirmationPasswordError == registrationPasswordInput.isErrorEnabled()){
                return;
            }
            registrationPasswordInput.setErrorEnabled(showConfirmationPasswordError);
            registrationPasswordInput.setError(showConfirmationPasswordError ? getString(R.string.error_invalid_confirmation_password): null);
        };
    }

    public final Observer<SingleEvent<Boolean>> emailRegistrationResponseObserver() {
        return emailRegistrationEvent -> {
            Toast emailRegistrationNotification = Toast.makeText(getContext(), getString(R.string.registration_email_sent), Toast.LENGTH_SHORT);
            emailRegistrationNotification.show();
        };
    }

    public final Observer<SingleEvent<Boolean>> accountRegistrationResponseObserver() {
        return accountRegistrationEvent -> {
            if (accountRegistrationEvent.peek() == true)
            {
                Toast accountRegistrationNotification = Toast.makeText(getContext(), getString(R.string.registration_account_created), Toast.LENGTH_SHORT);
                accountRegistrationNotification.show();
            }
            else
            {
                TextInputLayout registrationEmailInput = binding.email;
                registrationEmailInput.setErrorEnabled(true);
                registrationEmailInput.setError(getString(R.string.registration_account_failure));

            }
        };
    }
}