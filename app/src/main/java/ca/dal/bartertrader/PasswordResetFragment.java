package ca.dal.bartertrader;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import ca.dal.bartertrader.databinding.FragmentPasswordResetBinding;

public class PasswordResetFragment extends Fragment {

    private FragmentPasswordResetBinding binding;
    private PasswordResetViewModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_password_reset, container, false);
        model = new ViewModelProvider(this).get(PasswordResetViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setModel(model);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.getModel().getErrorEnabled().observe(getViewLifecycleOwner(), errorState -> {
            Boolean currentErrorState = binding.inputLayoutEmail.isErrorEnabled();
            if (currentErrorState == errorState) {
                return;
            }
            String errorMessage = errorState ? getString(R.string.error_email) : null;
            binding.inputLayoutEmail.setError(errorMessage);
        });

        binding.getModel().getEventHandler().observe(getViewLifecycleOwner(), new Observer<Event<Actions, ?>>() {
            @Override
            public void onChanged(Event<Actions, ?> actionsEvent) {
                Pair<Actions, ?> event = actionsEvent.handle();
                if (event.first == Actions.TOAST) {

                    Pair<Boolean, String> payload = (Pair<Boolean, String>) event.second;
                    Toast.makeText(getContext(), payload.second.toString(), Toast.LENGTH_LONG).show();
                    if (payload.first) {
                        binding.inputLayoutEmail.setError(null);
                        binding.inputLayoutEmail.setErrorEnabled(false);
                        binding.inputLayoutEmail.getEditText().setText("");
                    } else {
                        // TODO: Add some other methods for errors.
                    }
                }

                if (event.first == Actions.NAVIGATION) {
                    Navigation.findNavController(getView()).navigate(PasswordResetFragmentDirections.actionPasswordResetFragmentToLogInFragment());
                }
            }
        });
    }
}
