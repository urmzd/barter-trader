package ca.dal.bartertrader;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInFragment extends Fragment {
    public String email;
    public String password;
    private FirebaseAuth mAuth;
    private AlertDialog message;
    private static final String TAG = "LogIn";

    private LogInViewModel logInModel;

    EditText emailField, passwordField;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_in_log, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        logInModel = new ViewModelProvider(this, new LogInViewModelFactory()).get(LogInViewModel.class);
        logInModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                //Navigation.findNavController(getView())
                       //  .navigate(LogInFragmentDirections.actionLogInFragmentToSignUpFragment());
            }

            else {
               logInFailure();
            }
        });

        emailField = (EditText) getView().findViewById(R.id.emailField);
        passwordField = (EditText) getView().findViewById(R.id.passwordField);

        //FirebaseUser currentUser = mAuth.getCurrentUser();

        view.findViewById(R.id.resetPassBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView())
                        .navigate(LogInFragmentDirections.actionLogInFragmentToPasswordResetFragment());
            }
        });

        view.findViewById(R.id.signUpBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(getView())
                        //.navigate(LogInFragmentDirections.actionLogInFragmentToSignUpFragment());
            }
        });

        view.findViewById(R.id.logInBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailField.getText().toString();
                password = passwordField.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    logInModel.setEmail(email);
                    logInModel.setPassword(password);

                    logInModel.login();
                }

                else {
                    logInFailure();
                }

                /*mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    message.setTitle("Success");
                                    message.setMessage("Log in was successful");
                                }

                                else {
                                    Log.d(TAG, "signInWithEmail:failure", task.getException());
                                    message.setTitle("Failure");
                                    message.setMessage("Invalid email or password");
                                }
                                message.show();
                            }
                        });*/
            }
        });
    }

    private void logInSuccess() {
        message = new AlertDialog.Builder(getContext()).create();
        message.setTitle("Success");
        message.setMessage("Log In Success");
        message.show();
    }

    private void logInFailure() {
        message = new AlertDialog.Builder(getContext()).create();
        message.setTitle("Error");
        message.setMessage("Invalid email or password");
        message.show();
    }
}
