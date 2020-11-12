package ca.dal.bartertrader.data.repository;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Arrays;

import ca.dal.bartertrader.data.data_source.FirebaseAuthDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseFirestoreDataSource;
import ca.dal.bartertrader.domain.model.NewUserModel;
import ca.dal.bartertrader.domain.model.UserModel;
import ca.dal.bartertrader.utils.handler.live_data.event.LiveEvent;
import ca.dal.bartertrader.utils.handler.resource.Resource;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FirebaseUserRepository {
    private final FirebaseAuthDataSource firebaseAuthDataSource;
    private final FirebaseFirestoreDataSource firebaseFirestoreDataSource;

    private final LiveEvent<Resource<FirebaseUser>> loginResult = new LiveEvent<>();

    public FirebaseUserRepository(FirebaseAuthDataSource firebaseAuthDataSource, FirebaseFirestoreDataSource firebaseFirestoreDataSource) {
        this.firebaseAuthDataSource = firebaseAuthDataSource;
        this.firebaseFirestoreDataSource = firebaseFirestoreDataSource;
    }

    public Single<AuthResult> login(UserModel loginCredentials) {
        String email = loginCredentials.getEmail();
        String password = loginCredentials.getPassword();

        return firebaseAuthDataSource.signInWithEmailAndPassword(email, password).subscribeOn(Schedulers.io());
    }

    public Completable register(NewUserModel registrationDetails) {
        String displayName = registrationDetails.getDisplayName();
        String email = registrationDetails.getEmail();
        String password = registrationDetails.getPassword();
        Boolean role = registrationDetails.getRole();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .build();

        return firebaseAuthDataSource.createUserWithEmailAndPassword(email, password)
                .subscribeOn(Schedulers.io())
                .flatMapCompletable(authResult -> Completable.merge(Arrays.asList(
                        firebaseAuthDataSource.updateProfile(authResult.getUser(), profileUpdates),
                        firebaseAuthDataSource.sendEmailVerification(authResult.getUser()),
                        firebaseFirestoreDataSource.createNewUser(authResult.getUser().getUid(), role)
                )));
    }

    public Completable verifyEmailExists(String email) {
        return firebaseAuthDataSource.fetchSignInMethodsForEmail(email).subscribeOn(Schedulers.io());
    }

    public Single<FirebaseUser> getUser() {
        return null;
    }

    public Completable sendPasswordReset(String email) {
        return firebaseAuthDataSource.sendPasswordResetEmail(email).subscribeOn(Schedulers.io());
    }

    public Completable swapRoles() {

        /* return firebaseAuthDataSource.reloadUser().subscribeOn(Schedulers.io())

         */
        return null;
    }
}
