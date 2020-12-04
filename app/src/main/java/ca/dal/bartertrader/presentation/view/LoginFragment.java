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

import com.google.android.material.appbar.MaterialToolbar;

import ca.dal.bartertrader.R;
import ca.dal.bartertrader.databinding.FragmentLoginBinding;
import ca.dal.bartertrader.di.view_model.LoginViewModelFactory;
import ca.dal.bartertrader.presentation.view_model.LoginViewModel;
import ca.dal.bartertrader.utils.BindingUtils;
import ca.dal.bartertrader.utils.NavigationUtils;
import ca.dal.bartertrader.utils.handler.resource.Status;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;
    private final LoginViewModelFactory loginViewModelFactory;

    public LoginFragment(LoginViewModelFactory loginViewModelFactory) {
        this.loginViewModelFactory = loginViewModelFactory;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);

        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        NavigationUtils.setUpToolBar(getView(), (MaterialToolbar) binding.toolbar, R.id.loginFragment);

        viewModel.getGoToPasswordResetLiveEvent().observe(getViewLifecycleOwner(), __ -> {
            Navigation.findNavController(requireView()).navigate(LoginFragmentDirections.actionLoginFragmentToPasswordResetFragment());
        });

        viewModel.getGoToRegistrationLiveEvent().observe(getViewLifecycleOwner(), __ -> {
            Navigation.findNavController(requireView()).navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment());
        });

        viewModel.getEmailIsValid().observe(getViewLifecycleOwner(), validity -> {
            BindingUtils.setErrorOnTextInputLayout(binding.loginEmail, validity, getString(R.string.error_email_invalid));
        });

        viewModel.getPasswordIsValid().observe(getViewLifecycleOwner(), validity -> {
            BindingUtils.setErrorOnTextInputLayout(binding.loginPassword, validity, getString(R.string.error_password_empty));
        });

        viewModel.getLoginActionEvent().observe(getViewLifecycleOwner(), user -> {
            if (user.getStatus() == Status.FULFILLED) {

                if (user.getData().getFirebaseUser().isEmailVerified()) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("provider", user.getData().getProvider());
                    Navigation.findNavController(requireView()).navigate(R.id.user_nav_graph, bundle);
                    Toast.makeText(getContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getContext(), "Only verified accounts are allowed access. Please verify your email and try again.", Toast.LENGTH_LONG).show();
                return;
            }

            if (user.getStatus() == Status.REJECTED) {
                Toast.makeText(getContext(), user.getError().getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}
