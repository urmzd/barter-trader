package ca.dal.bartertrader.presentation.view.provider_home;

import android.app.ActionBar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.dal.bartertrader.databinding.FragmentProviderHomeBinding;
import ca.dal.bartertrader.di.view_model.provider_home.ProviderHomeViewModelFactory;
import ca.dal.bartertrader.domain.model.PostModel;
import ca.dal.bartertrader.presentation.adapter.PostListAdapter;
import ca.dal.bartertrader.presentation.view_model.provider_home.ProviderHomeViewModel;

public class ProviderHomeFragment extends Fragment {

    private final ProviderHomeViewModelFactory providerHomeViewModelFactory;
    private FragmentProviderHomeBinding binding;
    private ProviderHomeViewModel viewModel;
    private PostListAdapter postListAdapter;
    private ArrayList<PostModel> postModels;

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
        setUpPostListAdapter();
        //binding.searchView.setLayoutParams(new ActionBar.LayoutParams(Gravity.RIGHT));
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(postListAdapter != null)
                    postListAdapter.getFilter().filter(newText);
                return false;
            }
        });
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(postListAdapter != null)
                    postListAdapter.getFilter().filter(editable);
            }
        });

        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.addPost.setOnClickListener(v -> Navigation.findNavController(getView()).navigate(ProviderHomeFragmentDirections.actionProviderHomeFragmentToHandlePostFragment()));
        binding.sortPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postListAdapter.sortList();
                postListAdapter.notifyDataSetChanged();
            }
        });
    }

    public void setUpPostListAdapter() {
        viewModel.postItemList.observe(getActivity(),posList->{
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            postModels = new ArrayList<>();
            postModels.addAll(posList);
            postListAdapter = new PostListAdapter(getActivity(), posList);
            binding.rcyPostList.setLayoutManager(linearLayoutManager);
            binding.rcyPostList.setAdapter(postListAdapter);
            binding.rcyPostList.addItemDecoration(new DividerItemDecoration(getActivity(),
                    DividerItemDecoration.VERTICAL));
        });

    }
}
