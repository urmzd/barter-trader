package ca.dal.bartertrader.domain.use_case;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import io.reactivex.rxjava3.core.Completable;

public class VerifyEmailExistsUseCase extends AbstractBaseUseCase<String, Completable> {

    private final FirebaseUserRepository firebaseUserRepository;

    public VerifyEmailExistsUseCase(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public Completable execute(String email) {
        return firebaseUserRepository.verifyEmailExists(email);
    }

    @Override
    public Task<QuerySnapshot> executeMyPost() {
        return null;
    }
}
