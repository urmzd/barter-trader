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

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import ca.dal.bartertrader.R;
import ca.dal.bartertrader.databinding.FragmentPasswordResetBinding;
import ca.dal.bartertrader.di.view_model.PasswordResetViewModelFactory;
import ca.dal.bartertrader.presentation.view_model.PasswordResetViewModel;
import ca.dal.bartertrader.utils.BindingUtils;
import ca.dal.bartertrader.utils.NavigationUtils;
import ca.dal.bartertrader.utils.handler.resource.Status;

public class PasswordResetFragment extends Fragment {

    private FragmentPasswordResetBinding binding;
    private final PasswordResetViewModelFactory passwordResetViewModelFactory;
    private PasswordResetViewModel viewModel;

    public PasswordResetFragment(PasswordResetViewModelFactory passwordResetViewModelFactory) {
        this.passwordResetViewModelFactory = passwordResetViewModelFactory;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, passwordResetViewModelFactory).get(PasswordResetViewModel.class);

        binding = FragmentPasswordResetBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);

        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        NavigationUtils.setUpToolBar(getView(), (MaterialToolbar) binding.toolbar, R.id.loginFragment);

        viewModel.getEmailIsValid().observe(getViewLifecycleOwner(), isValid -> {
            BindingUtils.setErrorOnTextInputLayout(binding.passwordResetEmail, isValid, getString(R.string.error_email_invalid));
        });

        viewModel.getPasswordResetResult().observe(getViewLifecycleOwner(), result -> {
            Status status = result.getStatus();
            Toast toastToShow = Toast.makeText(getContext(), "Email successfully sent!", Toast.LENGTH_SHORT);

            if (status == Status.FULFILLED) {
                toastToShow.show();
                return;
            }

            if (status == Status.REJECTED) {
                if (result.getError() instanceof FirebaseAuthInvalidUserException) {
                    toastToShow.show();
                    return;
                }

                Toast.makeText(getContext(), result.getError().getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}
