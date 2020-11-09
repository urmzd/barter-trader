package ca.dal.bartertrader.presentation.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import ca.dal.bartertrader.domain.use_case.ResetPasswordUseCase;
import ca.dal.bartertrader.utils.FormValidatorTools;
import ca.dal.bartertrader.utils.handler.live_data.event.LiveEvent;
import ca.dal.bartertrader.utils.handler.resource.Resource;
import ca.dal.bartertrader.utils.handler.resource.Status;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PasswordResetViewModel extends ViewModel {

    private final ResetPasswordUseCase resetPasswordUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public PasswordResetViewModel(ResetPasswordUseCase resetPasswordUseCase) {
        this.resetPasswordUseCase = resetPasswordUseCase;
    }

    public final MutableLiveData<String> email = new MutableLiveData<>();
    private final LiveData<Boolean> emailIsValid = Transformations.map(email, FormValidatorTools::isEmailValid);

    private final LiveEvent<Resource<Void>> passwordResetResult = new LiveEvent<>();
    private final LiveData<Status> passwordResetStatus = Transformations.map(passwordResetResult, Resource::getStatus);

    private final LiveEvent<Void> goToLoginEvent = new LiveEvent<>();

    public void goToLogin() {
        goToLoginEvent.call();
    }

    public void sendPasswordReset() {
        disposables.add(resetPasswordUseCase.execute(email.getValue())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(__ -> passwordResetResult.setValue(Resource.pending(null)))
                .subscribe(
                        () -> passwordResetResult.setValue(Resource.fulfilled(null)),
                        throwable -> passwordResetResult.setValue(Resource.rejected(throwable))
                )
        );
    }

    public LiveEvent<Resource<Void>> getPasswordResetResult() {
        return passwordResetResult;
    }

    public LiveData<Boolean> getEmailIsValid() {
        return emailIsValid;
    }

    public LiveEvent<Void> getGoToLoginEvent() {
        return goToLoginEvent;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LiveData<Status> getPasswordResetStatus() {
        return passwordResetStatus;
    }
}
