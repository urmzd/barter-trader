package ca.dal.bartertrader;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInViewModel extends ViewModel {
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    private LogInRepository repository;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    LogInViewModel() {
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public void login() {
        mAuth.signInWithEmailAndPassword(email.getValue(), password.getValue()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    user.setValue(currentUser);
                }
                else {
                    user.setValue(null);
                }
            }
        });
    }

//    public void task(FirebaseAuth mAuth, String email, String password) {
//        boolean taskSuccess = repository.task(mAuth, email, password);
//        if (taskSuccess) {
//           result.setValue(new LogInResult(taskSuccess));
//        }
//        else {
//            result.setValue(new LogInResult("Invalid user name or password"));
//        }
//    }
}
