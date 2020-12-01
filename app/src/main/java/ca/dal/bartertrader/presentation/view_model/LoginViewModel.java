package ca.dal.bartertrader.presentation.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.AuthResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.dal.bartertrader.domain.model.LoginModel;
import ca.dal.bartertrader.domain.use_case.users.LoginUseCase;
import ca.dal.bartertrader.utils.FormValidatorTools;
import ca.dal.bartertrader.utils.functionals.Transformers;
import ca.dal.bartertrader.utils.handler.live_data.TransformedLiveData;
import ca.dal.bartertrader.utils.handler.live_data.event.LiveEvent;
import ca.dal.bartertrader.utils.handler.resource.Resource;
import ca.dal.bartertrader.utils.handler.resource.Status;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoginViewModel extends ViewModel {

    private final LoginUseCase loginUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public LoginViewModel(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public final MutableLiveData<String> email = new MutableLiveData<>();
    public final MutableLiveData<String> password = new MutableLiveData<>();

    private final MutableLiveData<Boolean> rememberMe = new MutableLiveData<>(false);

    private final LiveData<Boolean> emailIsValid = Transformations.map(email, FormValidatorTools::isEmailValid);
    private final LiveData<Boolean> passwordIsValid = Transformations.map(password, FormValidatorTools::isTextValid);

    private final List<LiveData<Boolean>> form = new ArrayList<>(Arrays.asList(emailIsValid, passwordIsValid));
    private final TransformedLiveData<Boolean, Boolean> formIsValid = new TransformedLiveData<>(form, Transformers::every);

    private final LiveEvent<Void> goToRegistrationLiveEvent = new LiveEvent<Void>();
    private final LiveEvent<Void> goToPasswordResetLiveEvent = new LiveEvent<Void>();

    private final LiveEvent<Resource<AuthResult>> loginActionEvent = new LiveEvent<>();
    private final LiveData<Status> loginStatus = Transformations.map(loginActionEvent, Resource::getStatus);

    public void login() {
        disposables.add(loginUseCase.execute(new LoginModel(email.getValue(), password.getValue(), rememberMe.getValue()))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> loginActionEvent.setValue(Resource.pending(null)))
                .subscribe(authResult -> loginActionEvent.setValue(Resource.fulfilled(authResult)),
                        throwable -> loginActionEvent.setValue((Resource.rejected(throwable)))));
    }

    public void goToRegistration() {
        goToRegistrationLiveEvent.call();
    }

    public void goToPasswordReset() {
        goToPasswordResetLiveEvent.call();
    }


    public LiveData<Boolean> getEmailIsValid() {
        return emailIsValid;
    }


    public LiveData<Boolean> getPasswordIsValid() {
        return passwordIsValid;
    }

    public TransformedLiveData<Boolean, Boolean> getFormIsValid() {
        return formIsValid;
    }

    public LiveEvent<Void> getGoToRegistrationLiveEvent() {
        return goToRegistrationLiveEvent;
    }

    public LiveEvent<Void> getGoToPasswordResetLiveEvent() {
        return goToPasswordResetLiveEvent;
    }

    public LiveEvent<Resource<AuthResult>> getLoginActionEvent() {
        return loginActionEvent;
    }

    public LiveData<Status> getLoginStatus() {
        return loginStatus;
    }
    
    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
