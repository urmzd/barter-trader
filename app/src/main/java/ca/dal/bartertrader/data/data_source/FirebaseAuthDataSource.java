package ca.dal.bartertrader.data.data_source;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import ca.dal.bartertrader.utils.handler.async.CompletableTaskHandler;
import ca.dal.bartertrader.utils.handler.async.SingleTaskHandler;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirebaseAuthDataSource {
    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthDataSource(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public Single<AuthResult> signInWithEmailAndPassword(String email, String password) {
        return Single.create(emitter -> SingleTaskHandler.assign(emitter, firebaseAuth.signInWithEmailAndPassword(email, password)));
    }

    public Single<AuthResult> createUserWithEmailAndPassword(String email, String password) {
        return Single.create(emitter -> SingleTaskHandler.assign(emitter, firebaseAuth.createUserWithEmailAndPassword(email, password)));
    }

    public Completable updateProfile(FirebaseUser user, UserProfileChangeRequest profileUpdates) {
        return Completable.create(emitter -> CompletableTaskHandler.assign(emitter, user.updateProfile(profileUpdates)));
    }

    public Completable fetchSignInMethodsForEmail(String email) {
        return Completable.create(emitter -> CompletableTaskHandler.assign(emitter, firebaseAuth.fetchSignInMethodsForEmail(email)));
    }

    public Completable sendEmailVerification(FirebaseUser user) {
        return Completable.create(emitter -> CompletableTaskHandler.assign(emitter, user.sendEmailVerification()));
    }

    public Completable sendPasswordResetEmail(String email) {
        return Completable.create(emitter -> CompletableTaskHandler.assign(emitter, firebaseAuth.sendPasswordResetEmail(email)));
    }


}
