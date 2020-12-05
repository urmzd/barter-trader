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
import androidx.navigation.fragment.NavHostFragment;

import ca.dal.bartertrader.BarterTradeApplication;
import ca.dal.bartertrader.R;
import ca.dal.bartertrader.data.data_source.FirebaseAuthDataSource;
import ca.dal.bartertrader.data.model.FirebaseUserModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.DisposableContainer;

public class SwitchHandlerFragment extends Fragment {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Single<FirebaseUserModel> authDataSource = ((BarterTradeApplication) getActivity().getApplication()).injector.getUser();

        Log.d("URMZD","WE GOT HERE");
        compositeDisposable.add(authDataSource.observeOn(AndroidSchedulers.mainThread()).subscribe((firebaseUserModel, throwable) -> {
            Log.d("URMZD", String.valueOf(firebaseUserModel.getProvider()));
            if (firebaseUserModel != null) {
                boolean provider = firebaseUserModel.getProvider();

                if (provider) {
                    NavHostFragment.findNavController(this).navigate(SwitchHandlerFragmentDirections.actionSwitchHandlerToProviderHomeFragment());
                } else {
                    NavHostFragment.findNavController(this).navigate(SwitchHandlerFragmentDirections.actionSwitchHandlerToReceiverHomeFragment());
                }
            } else {
                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(this).popBackStack(R.id.profileFragment, true);
            }
        }));


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_switch_handler, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
    }
}
