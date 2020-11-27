package ca.dal.bartertrader.presentation.view.provider_home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import ca.dal.bartertrader.databinding.FragmentProviderHomeBinding;
import ca.dal.bartertrader.di.view_model.provider_home.ProviderHomeViewModelFactory;
import ca.dal.bartertrader.presentation.view_model.provider_home.ProviderHomeViewModel;

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
        binding.addPost.setOnClickListener(v -> Navigation.findNavController(getView()).navigate(ProviderHomeFragmentDirections.actionProviderHomeFragmentToHandlePostFragment()));
    }
}
