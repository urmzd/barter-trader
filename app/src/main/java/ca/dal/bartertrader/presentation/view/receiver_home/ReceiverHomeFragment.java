package ca.dal.bartertrader.presentation.view.receiver_home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.RecyclerView;

import ca.dal.bartertrader.R;
import ca.dal.bartertrader.databinding.FragmentReceiverHomeBinding;
import ca.dal.bartertrader.di.view_model.receiver_home.ReceiverHomeViewModelFactory;
import ca.dal.bartertrader.presentation.view_model.receiver_home.ReceiverHomeViewModel;
import ca.dal.bartertrader.utils.NavigationUtils;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ReceiverHomeFragment extends Fragment {

    private final ReceiverHomeViewModelFactory viewModelFactory;
    private ReceiverHomeViewModel viewModel;
    private FragmentReceiverHomeBinding binding;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

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
        NavigationUtils.setUpToolBar(getView(), binding.toolbar, R.id.receiverHomeFragment);

        ReceiverHomeAdapter pagingAdapter = new ReceiverHomeAdapter(new FirebasePostModelComparator());

        setUpAdapter(pagingAdapter);
    }

    private void setUpAdapter(ReceiverHomeAdapter pagingAdapter) {
        RecyclerView recyclerView = (RecyclerView) binding.recyclerView;
        recyclerView.setAdapter(pagingAdapter);

        compositeDisposable.add(viewModel.retrievePosts(null).subscribe(
                firebasePostModelPagingData -> pagingAdapter.submitData(getLifecycle(), firebasePostModelPagingData)
        ));

        pagingAdapter.addLoadStateListener(combinedLoadStates -> {
            binding.progressBar.setVisibility(combinedLoadStates.getRefresh() instanceof LoadState.Loading ? View.VISIBLE : View.GONE);
            return null;
        });

        SearchView searchView = (SearchView) ((Toolbar) binding.toolbar).getMenu().findItem(R.id.receiver_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.retrievePosts(query);
                compositeDisposable.add(viewModel.retrievePosts(query).subscribe(
                        firebasePostModelPagingData -> pagingAdapter.submitData(getLifecycle(), firebasePostModelPagingData)
                ));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.dispose();
    }
}
