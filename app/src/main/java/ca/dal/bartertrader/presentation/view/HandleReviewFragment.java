package ca.dal.bartertrader.presentation.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import ca.dal.bartertrader.databinding.FragmentProviderHandleReviewBinding;
import ca.dal.bartertrader.di.view_model.HandleReviewViewModelFactory;
import ca.dal.bartertrader.domain.model.OfferModel;
import ca.dal.bartertrader.presentation.view_model.HandleReviewViewModel;
import ca.dal.bartertrader.utils.handler.resource.Status;

public class HandleReviewFragment extends Fragment {
    private FragmentProviderHandleReviewBinding binding;
    private HandleReviewViewModel viewModel;
    private final HandleReviewViewModelFactory handleReviewViewModelFactory;

    public HandleReviewFragment(HandleReviewViewModelFactory handleReviewViewModelFactory) {
        this.handleReviewViewModelFactory = handleReviewViewModelFactory;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, handleReviewViewModelFactory).get(HandleReviewViewModel.class);

        viewModel.setOffer((OfferModel) getArguments().getSerializable("offerModel"));

        binding = FragmentProviderHandleReviewBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(getViewLifecycleOwner());

        binding.setViewModel(viewModel);

        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        viewModel.getReviewResults().observe(getViewLifecycleOwner(), result -> {
            Status resultStatus = result.getStatus();

            if (resultStatus == Status.FULFILLED) {
                Toast.makeText(getContext(), "Review successfully created!", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).popBackStack();
                return;
            }

            if (resultStatus == Status.REJECTED) {
                Toast.makeText(getContext(), result.getError().getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }




}
