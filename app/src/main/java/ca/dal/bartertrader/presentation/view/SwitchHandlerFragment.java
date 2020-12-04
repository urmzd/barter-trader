package ca.dal.bartertrader.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import ca.dal.bartertrader.R;

public class SwitchHandlerFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean provider = getArguments().getBoolean("provider");

        if (provider) {
            NavHostFragment.findNavController(this).navigate(SwitchHandlerFragmentDirections.actionSwitchHandlerToProviderHomeFragment());
        } else {
            NavHostFragment.findNavController(this).navigate(SwitchHandlerFragmentDirections.actionSwitchHandlerToReceiverHomeFragment());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_switch_handler, container, false);
    }
}
