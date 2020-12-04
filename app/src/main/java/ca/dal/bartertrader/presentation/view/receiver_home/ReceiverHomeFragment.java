package ca.dal.bartertrader.presentation.view.receiver_home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;

import ca.dal.bartertrader.R;
import ca.dal.bartertrader.databinding.FragmentReceiverHomeBinding;
import ca.dal.bartertrader.di.view_model.receiver_home.ReceiverHomeViewModelFactory;
import ca.dal.bartertrader.presentation.view_model.receiver_home.ReceiverHomeViewModel;
import ca.dal.bartertrader.utils.NavigationUtils;

public class ReceiverHomeFragment extends Fragment {

    private final ReceiverHomeViewModelFactory viewModelFactory;
    private ReceiverHomeViewModel viewModel;
    private FragmentReceiverHomeBinding binding;

    public ReceiverHomeFragment(ReceiverHomeViewModelFactory viewModelFactory) {
        this.viewModelFactory = viewModelFactory;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ReceiverHomeViewModel.class);

        binding = FragmentReceiverHomeBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);

        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        NavigationUtils.setUpToolBar(getView(), (MaterialToolbar) binding.toolbar, R.id.receiverHomeFragment);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.receiver_home_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.receiver_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });


    }
}
