package ca.dal.bartertrader;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInFirebase {
    static boolean success;
    private LogInResult logInResult;
    public boolean task(final FirebaseAuth mAuth, String email, String password) {
        LoggedInUser fake = new LoggedInUser("nicholasmcphee45@gmail.com", "a4330317");
        boolean emailValid = emailValidation(email);
//        if (emailValid && email.equals(fake.getEmail()) && password.equals(fake.getPassword())) {
//            success = true;
//            logInResult = new LogInResult(success);
//        }
//
//        else {
//            success = false;
//        }
//
//        return success;
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user.isEmailVerified()) {

                            }
                            success = true;
                            logInResult = new LogInResult(success);
                        }

                        else {
                            logInResult = new LogInResult("Invalid user name or password");
                            success = false;
                        }
                    }
                });
        return success;
    }

    public static boolean emailValidation(String email) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9_.-]+@+[a-z]+\\.+[a-z]*");
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
