package ca.dal.bartertrader.registration;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import ca.dal.bartertrader.MainActivity;
import ca.dal.bartertrader.SingleEvent;


public class RegistrationViewModel extends ViewModel {
    private FirebaseAuth firebaseAuth;
    public RegistrationViewModel(/**UserRepository userRepository**/) {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> firstName = new MutableLiveData<>();
    private final MutableLiveData<String> lastName = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<String> confirmationPassword = new MutableLiveData<>();
    private final MutableLiveData<Boolean> roleIsProvider = new MutableLiveData<>();

    private final LiveData<Boolean> showEmailError = Transformations.map(email, email ->
    {
        if (TextUtils.isEmpty(email) || email == null) {
            return false;
        }
        return !isEmailValid(email);
    });

    private final LiveData<Boolean> showPasswordError = Transformations.map(password, password ->
    {
        if (TextUtils.isEmpty(password) || password == null) {
            return false;
        }
        return !isPasswordValid(password);
    });

    private final LiveData<Boolean> showConfirmationPasswordError = Transformations.map(confirmationPassword, confirmationPassword ->
    {
        if (TextUtils.isEmpty(confirmationPassword) || confirmationPassword == null) {
            return false;
        }
        return !isConfirmationPasswordValid(confirmationPassword);
    });

    private final MutableLiveData<SingleEvent<Boolean>> sendRegistrationEmailEvent = new MutableLiveData<>();
    private final MutableLiveData<SingleEvent<Boolean>> accountRegistrationEvent = new MutableLiveData<>();

    private final LiveData<Boolean> enabledRegistration =
            Transformations.map(email, this::isEmailValid);

    private boolean validateRegistration(String email, String username, String password) {
        boolean validRegistration = isEmailValid(email)         &&
                                    isUserNameValid(username)   &&
                                    isPasswordValid(password)   &&
                                    !isEmailRegistered(email);
        return validRegistration;
    };

    // Validates a potential email, does not check if it is already registered.
    private boolean isEmailValid(String email) {
        boolean emailPatternCheck = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (!email.isEmpty() && emailPatternCheck)
        {
            return true;
        }
        return false;
    };

    // Validates the username given for registration.
    private boolean isUserNameValid(String username) {
        return !username.isEmpty();
    };

    // Validates the password given for registration.
    private boolean isPasswordValid(String password) {
        return !password.isEmpty() && password.length() >= 6;
    };

    private boolean isConfirmationPasswordValid(String confirmationPassword)
    {
        return !confirmationPassword.isEmpty() && getPassword().getValue().equals(confirmationPassword);
    }
    // Checks firebase to see if this email already belongs to a user.
    private boolean isEmailRegistered(String email) {
        boolean exists = true;
        //exists = userRepository.emailExists(email);
        return exists;
    }

    public MutableLiveData<String> getEmail() {
        return this.email;
    }

    public MutableLiveData<String> getFirstName() {
        return this.firstName;
    }

    public MutableLiveData<String> getLastName() {
        return this.lastName;
    }

    public MutableLiveData<String> getPassword() {
        return this.password;
    }

    public MutableLiveData<String> getConfirmationPassword() {
        return confirmationPassword;
    }

    public MutableLiveData<Boolean> getRoleIsProvider() {
        return this.roleIsProvider;
    }

    public LiveData<Boolean> getShowEmailError() { return this.showEmailError; }
    public LiveData<Boolean> getShowPasswordError() { return this.showPasswordError; }
    public LiveData<Boolean> getShowConfirmationPasswordError() { return this.showConfirmationPasswordError; }

    public LiveData<Boolean> getEnabledRegistration() { return this.enabledRegistration; }

    public LiveData<SingleEvent<Boolean>> getSendRegistrationEmailEvent() {
        return this.sendRegistrationEmailEvent;
    }

    public MutableLiveData<SingleEvent<Boolean>> getAccountRegistrationEvent() {
        return accountRegistrationEvent;
    }

    @BindingAdapter("email")
    public void setEmail(TextInputLayout view, String currentEmail)
    {
        this.email.setValue(currentEmail);
    }

    @BindingAdapter("firstName")
    public void setFirstName(TextInputLayout view, String firstName)
    {
        this.firstName.setValue(firstName);
    }

    @BindingAdapter("lastName")
    public void setLastName(TextInputLayout view, String lastName)
    {
        this.lastName.setValue(lastName);
    }

    @BindingAdapter("password")
    public void setPassword(TextInputLayout view, String password)
    {
        this.password.setValue(password);
    }

    @BindingAdapter("confirmationPassword")
    public void setConfirmationPassword(TextInputLayout view, String confirmationPassword)
    {
        this.confirmationPassword.setValue(confirmationPassword);
    }

    @BindingAdapter("roleIsProvider")
    public void setRoleIsProvider(TextInputLayout view, boolean roleIsProvider)
    {
        this.roleIsProvider.setValue(roleIsProvider);
    }

    public void sendRegistrationEmailEvent() {
        firebaseAuth.createUserWithEmailAndPassword(this.getEmail().getValue(), this.getPassword().getValue())
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("createNewAccount:","Success");
                            sendRegistrationEmailEvent.setValue(new SingleEvent<>(true));
                            createDataCollection(task.getResult().getUser().getUid());
                        }
                        else {
                            Log.d("createNewAccount:","Failure");
                            accountRegistrationEvent.setValue(new SingleEvent<>(false));
                        }
                    }
                });

    }

    private void createDataCollection(String uid)
    {
        HashMap<String, Boolean> user = new HashMap<>();
        user.put("provider", getRoleIsProvider().getValue());
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("createFirestoreData:","Success");
                    accountRegistrationEvent.setValue(new SingleEvent<>(true));
                }
                else {
                    Log.d("createFirestoreData:","Failure");
                    accountRegistrationEvent.setValue(new SingleEvent<>(false));
                }
            }
        });
    }
}
