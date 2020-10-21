package ca.dal.bartertrader;

import android.text.TextUtils;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class PasswordResetViewModel extends ViewModel {

    public final MutableLiveData<String> email = new MutableLiveData<>();

    private final LiveData<Boolean> submitEnabled = Transformations.map(email, ValidationUtils::emailIsValid);
    private final LiveData<Boolean> errorEnabled = Transformations.map(email, email -> {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        return !ValidationUtils.emailIsValid(email);
    });

    private final MutableLiveData<Event<Actions, ?>> eventHandler = new MutableLiveData<>();
    private final MutableLiveData<Boolean> progressBarVisibility = new MutableLiveData<>();

    private final FirebaseAuth firebaseAuth;

    public PasswordResetViewModel() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public LiveData<Event<Actions, ?>> getEventHandler() {
        return this.eventHandler;
    }

    public MutableLiveData<Boolean> getProgressBarVisibility() {
        return progressBarVisibility;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    @InverseBindingAdapter(attribute = "email")
    public MutableLiveData<String> getEmail(TextInputEditText editText) {
        return this.email;
    }

    @BindingAdapter("email")
    public void setEmail(TextInputEditText editText, String email) {
        if (!editText.getText().toString().equals(email)) {
            this.email.setValue(email);
        }
    }

    public LiveData<Boolean> getSubmitEnabled() {
        return submitEnabled;
    }

    public LiveData<Boolean> getErrorEnabled() {
        return errorEnabled;
    }

    public void resetPasswordResetEmail(@NonNull String email) {

        progressBarVisibility.setValue(true);
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful() || (!task.isSuccessful()) && task.getException() instanceof FirebaseAuthInvalidUserException) {
                eventHandler.postValue(new Event<>(Actions.TOAST, new Pair(true, "An email has been sent.")));
            } else {
                eventHandler.postValue(new Event<>(Actions.TOAST, new Pair(false, String.format("Email could not be sent %s", task.getException().toString()))));
            }
            progressBarVisibility.setValue(false);
        });


    }

    public void goBackInStack() {
        eventHandler.postValue(new Event<Actions, Boolean>(Actions.NAVIGATION, true));
    }
}

