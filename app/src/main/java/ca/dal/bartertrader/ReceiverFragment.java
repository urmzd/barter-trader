package ca.dal.bartertrader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

public class ReceiverFragment extends Fragment {
    private SwitchRolesViewModel switchRolesViewModel;
    private TextView welcomeText;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_receiver, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switchRolesViewModel = new ViewModelProvider(this).get(SwitchRolesViewModel.class);

        welcomeText = view.findViewById(R.id.welcome_message);
        Button switchRoleButton = view.findViewById(R.id.switch_role_button);


        switchRoleButton.setOnClickListener(v -> {
                Toast.makeText(getActivity(), "Switching Role to Provider",
                        Toast.LENGTH_SHORT).show();
                switchRolesViewModel.switchRoles();
        });

        switchRolesViewModel.getAuthUser().observe(getViewLifecycleOwner(), authUser ->
                welcomeText.setText(authUser.getDisplayName())
        );

        switchRolesViewModel.getFirestoreUser().observe(getViewLifecycleOwner(), firestoreUser -> {
            if (firestoreUser.provider) {
                Navigation.findNavController(getView())
                        .navigate(ReceiverFragmentDirections.actionReceiverFragmentToProviderFragment());
            }

        });
    }
}


