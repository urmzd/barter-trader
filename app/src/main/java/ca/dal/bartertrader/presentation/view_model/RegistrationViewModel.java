package ca.dal.bartertrader.presentation.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ca.dal.bartertrader.domain.model.RegistrationPOJO;
import ca.dal.bartertrader.domain.use_case.VerifyEmailExistsUseCase;
import ca.dal.bartertrader.domain.use_case.users.RegisterUseCase;
import ca.dal.bartertrader.utils.FormValidatorTools;
import ca.dal.bartertrader.utils.functionals.Transformers;
import ca.dal.bartertrader.utils.handler.live_data.TransformedLiveData;
import ca.dal.bartertrader.utils.handler.live_data.event.LiveEvent;
import ca.dal.bartertrader.utils.handler.resource.Resource;
import ca.dal.bartertrader.utils.handler.resource.Status;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class RegistrationViewModel extends ViewModel {
    private final RegisterUseCase registerUserUseCase;
    private final VerifyEmailExistsUseCase verifyEmailExistsUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public RegistrationViewModel(RegisterUseCase registerUserUseCase, VerifyEmailExistsUseCase verifyEmailExistsUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.verifyEmailExistsUseCase = verifyEmailExistsUseCase;
    }

    public final MutableLiveData<String> firstName = new MutableLiveData<>();
    public final MutableLiveData<String> lastName = new MutableLiveData<>();
    public final MutableLiveData<String> email = new MutableLiveData<>();
    public final MutableLiveData<String> password = new MutableLiveData<>();
    public final MutableLiveData<String> confirmedPassword = new MutableLiveData<>();

    private final MutableLiveData<Boolean> role = new MutableLiveData<>();

    private final LiveData<Boolean> firstNameIsValid = Transformations.map(firstName, FormValidatorTools::isNameValid);
    private final LiveData<Boolean> lastNameIsValid = Transformations.map(lastName, FormValidatorTools::isNameValid);
    private final LiveData<Boolean> emailIsValid = Transformations.map(email, FormValidatorTools::isEmailValid);
    private final LiveData<Boolean> passwordIsValid = Transformations.map(password, FormValidatorTools::isPasswordStrong);
    private final LiveData<Boolean> confirmedPasswordIsValid = Transformations.map(confirmedPassword,
            confirmedPassword -> FormValidatorTools.doPasswordsMatch(password.getValue(), confirmedPassword)
    );
    private final LiveData<Boolean> roleIsValid = Transformations.map(role, Objects::nonNull);

    private final List<LiveData<Boolean>> formFields = new ArrayList<>(Arrays.asList(firstNameIsValid, lastNameIsValid, emailIsValid, passwordIsValid, confirmedPasswordIsValid, roleIsValid));
    private final TransformedLiveData<Boolean, Boolean> formIsValid = new TransformedLiveData<>(formFields, Transformers::every);

    private final LiveEvent<Resource<Void>> registrationResult = new LiveEvent<>();
    private final LiveData<Status> registrationStatus = Transformations.map(registrationResult, Resource::getStatus);

    private final LiveEvent<Void> goToLoginLiveEvent = new LiveEvent<>();

    public void register() {
        RegistrationPOJO user = new RegistrationPOJO(firstName.getValue(), lastName.getValue(), email.getValue(), password.getValue(), role.getValue());

        disposables.add(registerUserUseCase.execute(user)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> registrationResult.setValue(Resource.pending(null)))
                .subscribe(
                        () -> registrationResult.setValue(Resource.fulfilled(null)),
                        throwable -> registrationResult.setValue(Resource.rejected(throwable))
                ));
    }

    public void goToLogin() {
        goToLoginLiveEvent.call();
    }

    public void selectRole(Boolean choice) {
        role.setValue(choice);
    }

    public LiveEvent<Resource<Void>> getRegistrationResult() {
        return registrationResult;
    }

    public LiveData<Status> getRegistrationStatus() {
        return registrationStatus;
    }

    public LiveData<Boolean> getFirstNameIsValid() {
        return firstNameIsValid;
    }

    public LiveData<Boolean> getLastNameIsValid() {
        return lastNameIsValid;
    }

    public LiveData<Boolean> getEmailIsValid() {
        return emailIsValid;
    }

    public LiveData<Boolean> getPasswordIsValid() {
        return passwordIsValid;
    }

    public LiveData<Boolean> getConfirmedPasswordIsValid() {
        return confirmedPasswordIsValid;
    }

    public TransformedLiveData<Boolean, Boolean> getFormIsValid() {
        return formIsValid;
    }

    public LiveEvent<Void> getGoToLoginLiveEvent() {
        return goToLoginLiveEvent;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
