package ca.dal.bartertrader.presentation.view.provider_home;

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

import ca.dal.bartertrader.databinding.FragmentProviderHomeBinding;
import ca.dal.bartertrader.di.view_model.provider_home.ProviderHomeViewModelFactory;
import ca.dal.bartertrader.presentation.view_model.provider_home.ProviderHomeViewModel;
import ca.dal.bartertrader.utils.handler.resource.Status;

public class ProviderHomeFragment extends Fragment {

    private final ProviderHomeViewModelFactory providerHomeViewModelFactory;
    private FragmentProviderHomeBinding binding;
    private ProviderHomeViewModel viewModel;

    public ProviderHomeFragment(ProviderHomeViewModelFactory providerHomeViewModelFactory) {
        this.providerHomeViewModelFactory = providerHomeViewModelFactory;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProviderHomeBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);

        viewModel = new ViewModelProvider(this, providerHomeViewModelFactory).get(ProviderHomeViewModel.class);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel.getAddPostEvent().observe(getViewLifecycleOwner(), __-> {
            Navigation.findNavController(requireView()).navigate(ProviderHomeFragmentDirections.actionProviderHomeFragmentToHandlePostFragment());
        });

        viewModel.getSwitchRoleResults().observe(getViewLifecycleOwner(), result -> {
            Status resultStatus = result.getStatus();

            if (resultStatus == Status.FULFILLED) {
                Toast.makeText(getContext(), "SWITCHED!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (resultStatus == Status.REJECTED) {
                Toast.makeText(getContext(), result.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
