package ca.dal.bartertrader.domain.use_case;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import io.reactivex.rxjava3.core.Completable;

public class ResetPasswordUseCase extends AbstractBaseUseCase<String, Completable> {

    private final FirebaseUserRepository firebaseUserRepository;

    public ResetPasswordUseCase(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public Completable execute(String email) {
        return firebaseUserRepository.sendPasswordReset(email);
    }
}
