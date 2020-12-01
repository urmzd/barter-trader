package ca.dal.bartertrader.data.repository;

import android.util.Log;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Arrays;

import ca.dal.bartertrader.data.data_source.FirebaseAuthDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseFirestoreDataSource;
import ca.dal.bartertrader.data.model.FirebaseUserModel;
import ca.dal.bartertrader.domain.model.LoginModel;
import ca.dal.bartertrader.domain.model.RegistrationModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FirebaseUserRepository {
    private final FirebaseAuthDataSource firebaseAuthDataSource;
    private final FirebaseFirestoreDataSource firebaseFirestoreDataSource;

    public FirebaseUserRepository(FirebaseAuthDataSource firebaseAuthDataSource, FirebaseFirestoreDataSource firebaseFirestoreDataSource) {
        this.firebaseAuthDataSource = firebaseAuthDataSource;
        this.firebaseFirestoreDataSource = firebaseFirestoreDataSource;
    }

    public Single<AuthResult> login(LoginModel loginCredentials) {
        String email = loginCredentials.getEmail();
        String password = loginCredentials.getPassword();

        return firebaseAuthDataSource.signInWithEmailAndPassword(email, password).subscribeOn(Schedulers.io());
    }

    public Completable register(RegistrationModel registrationDetails) {
        String displayName = registrationDetails.getDisplayName();
        String email = registrationDetails.getEmail();
        String password = registrationDetails.getPassword();
        Boolean role = registrationDetails.isProvider();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .build();

        return firebaseAuthDataSource.createUserWithEmailAndPassword(email, password)
                .subscribeOn(Schedulers.io())
                .flatMapCompletable(authResult -> Completable.merge(Arrays.asList(
                        firebaseAuthDataSource.updateProfile(authResult.getUser(), profileUpdates),
                        firebaseAuthDataSource.sendEmailVerification(authResult.getUser()),
                        firebaseFirestoreDataSource.createUser(authResult.getUser().getUid(), role)
                )));
    }

    public Completable verifyEmailExists(String email) {
        return firebaseAuthDataSource.fetchSignInMethodsForEmail(email).subscribeOn(Schedulers.io());
    }

    public Completable sendPasswordReset(String email) {
        return firebaseAuthDataSource.sendPasswordResetEmail(email).subscribeOn(Schedulers.io());
    }

    public Completable switchRole() {

        return firebaseAuthDataSource.reloadUser().subscribeOn(Schedulers.io())
                .andThen(firebaseFirestoreDataSource.getUser(firebaseAuthDataSource.getUser().getUid()))
                .flatMapCompletable(documentSnapshot -> {
                    Log.d("documentSnap", documentSnapshot.getData().toString());
                    FirebaseUserModel user = documentSnapshot.toObject(FirebaseUserModel.class);
                    Log.d("documentSnapID", user.getAuthUid());
                    return firebaseFirestoreDataSource.switchRole(user);
                });
    }

    public Single<FirebaseUserModel> getUser() {

        return firebaseAuthDataSource.reloadUser().subscribeOn(Schedulers.io())
                .andThen(firebaseFirestoreDataSource.getUser(firebaseAuthDataSource.getUser().getUid()))
                .map(documentSnapshot -> {
                    FirebaseUserModel user = documentSnapshot.toObject(FirebaseUserModel.class);
                    user.setFirebaseUser(firebaseAuthDataSource.getUser());

                    return user;
                });
    }
}
