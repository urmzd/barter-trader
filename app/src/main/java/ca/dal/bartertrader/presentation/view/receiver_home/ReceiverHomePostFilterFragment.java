package ca.dal.bartertrader.presentation.view.receiver_home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.ChipGroup;

import ca.dal.bartertrader.R;
import ca.dal.bartertrader.di.view_model.receiver_home.ReceiverHomeViewModelFactory;
import ca.dal.bartertrader.presentation.view_model.receiver_home.ReceiverHomeViewModel;

public class ReceiverHomePostFilterFragment extends DialogFragment {

    private final ReceiverHomeViewModelFactory receiverHomeViewModelFactory;
    private ReceiverHomeViewModel receiverHomeViewModel;

    public ReceiverHomePostFilterFragment(ReceiverHomeViewModelFactory receiverHomeViewModelFactory) {
        this.receiverHomeViewModelFactory = receiverHomeViewModelFactory;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        receiverHomeViewModel = new ViewModelProvider(this, receiverHomeViewModelFactory).get(ReceiverHomeViewModel.class);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.dailog_filter_posts, null);
        ChipGroup chipGroup = view.findViewById(R.id.filter_sort_chips);

        alertDialog.setView(view).setPositiveButton("APPLY", (dialog, which) -> {
            Boolean isAscending = chipGroup.getCheckedChipId() == R.id.sort_ascending;
            receiverHomeViewModel.setSortBy(isAscending);

            Boolean isTitle = chipGroup.getCheckedChipId() == R.id.filter_field_title;
            if (isTitle) {
                receiverHomeViewModel.setFilterFieldBy("title");
            } else {
                receiverHomeViewModel.setFilterFieldBy("description");
            }

            receiverHomeViewModel.triggerRefresh();
        }).setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());

        return alertDialog.create();
    }
}
