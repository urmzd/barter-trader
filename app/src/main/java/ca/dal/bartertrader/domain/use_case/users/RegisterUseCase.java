package ca.dal.bartertrader.domain.use_case.users;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.domain.model.RegistrationModel;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import io.reactivex.rxjava3.core.Completable;

public class RegisterUseCase extends AbstractBaseUseCase<RegistrationModel, Completable> {

    private final FirebaseUserRepository firebaseUserRepository;

    public RegisterUseCase(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public Completable execute(RegistrationModel information) {
        return firebaseUserRepository.register(information);
    }
}