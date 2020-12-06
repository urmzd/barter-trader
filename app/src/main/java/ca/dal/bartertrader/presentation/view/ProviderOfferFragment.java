package ca.dal.bartertrader.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.dal.bartertrader.R;
import ca.dal.bartertrader.adapter.ProviderOfferAdapter;
import ca.dal.bartertrader.data.repository.OfferListLiveData;
import ca.dal.bartertrader.databinding.FragmentProviderOffersBinding;
import ca.dal.bartertrader.di.view_model.ProviderOfferViewModelFactory;
import ca.dal.bartertrader.domain.model.OfferModel;
import ca.dal.bartertrader.presentation.view_model.ProviderOfferViewModel;
import ca.dal.bartertrader.utils.NavigationUtils;
import ca.dal.bartertrader.utils.listeners.OfferListener;

public class ProviderOfferFragment extends Fragment implements OfferListener {
    private final ProviderOfferViewModelFactory providerOfferViewModelFactory;
    private String TAG = "ProviderOfferFragment";
    private RecyclerView.OnScrollListener onScrollListener;
    private RecyclerView recyclerView;
    private ArrayList<OfferModel> offers = new ArrayList<>();
    private ProviderOfferAdapter offerAdapter;
    private FragmentProviderOffersBinding binding;
    private ProviderOfferViewModel viewModel;
    private boolean isScrolling;

    public ProviderOfferFragment(ProviderOfferViewModelFactory providerOfferViewModelFactory) {
        this.providerOfferViewModelFactory = providerOfferViewModelFactory;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(ProviderOfferFragment.this, providerOfferViewModelFactory).get(ProviderOfferViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_provider_offers, container, false);
        binding.setProviderOfferViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavigationUtils.setUpToolBar(getView(), binding.toolbar, R.id.providerHomeFragment);

        getOffers();
        initRecyclerView();

    }

    public void getOffers() {
        OfferListLiveData productListLiveData = viewModel.getProductListLiveData();

        if (productListLiveData != null) {
            binding.setIsLoading(true);
            productListLiveData.observe(getViewLifecycleOwner(), operation -> {
                switch (operation.getType()) {
                    case R.string.added:
                        OfferModel addedProduct = operation.getOffer();
                        addProduct(addedProduct);
                        offerAdapter.notifyItemInserted(offers.indexOf(addedProduct));
                        break;

                    case R.string.modified:
                        OfferModel modifiedProduct = operation.getOffer();
                        modifyProduct(modifiedProduct);
                        offerAdapter.notifyItemChanged(offers.indexOf(modifiedProduct));
                        break;
                }
                binding.setIsLoading(false);
            });
        }

    }

    private void addProduct(OfferModel addedProduct) {
        offers.add(addedProduct);
    }

    private void modifyProduct(OfferModel modifiedProduct) {
        for (int i = 0; i < offers.size(); i++) {
            OfferModel currentProduct = offers.get(i);
            if (currentProduct.getId().equals(modifiedProduct.getId())) {
                offers.remove(currentProduct);
                offers.add(i, modifiedProduct);
            }
        }
    }

    private void initRecyclerView() {
        recyclerView = requireView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        offerAdapter = new ProviderOfferAdapter(this, offers);
        recyclerView.setAdapter(offerAdapter);

    }

    @Override
    public void onAcceptDeclineClick(OfferModel offer, String status) {
        viewModel.setStatus(offer, status);
    }

    @Override
    public void onReviewClick(OfferModel offerModel) {

        Navigation.findNavController(requireView()).navigate(ProviderOfferFragmentDirections.actionProviderOffersFragmentToHandleReviewFragment());
    }

    @Override
    public void onPause() {
        super.onPause();
        offers = new ArrayList<>();
    }

}
