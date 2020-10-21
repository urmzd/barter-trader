package ca.dal.bartertrader;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInRepository {
    private static volatile LogInRepository instance;
    private LogInFirebase firebase;

    private LogInRepository(LogInFirebase firebase) {
        this.firebase = firebase;
    }

    public static LogInRepository getInstance(LogInFirebase firebase) {
        if (instance == null) {
            instance = new LogInRepository(firebase);
        }
        return instance;
    }

    public boolean task(FirebaseAuth mAuth, String email, String password) {
        return firebase.task(mAuth, email, password);
    }
}
