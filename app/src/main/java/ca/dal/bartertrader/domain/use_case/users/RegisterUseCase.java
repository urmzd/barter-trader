package ca.dal.bartertrader.domain.use_case.users;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.domain.model.NewUserModel;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import io.reactivex.rxjava3.core.Completable;

public class RegisterUseCase extends AbstractBaseUseCase<NewUserModel, Completable> {

    private final FirebaseUserRepository firebaseUserRepository;

    public RegisterUseCase(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public Completable execute(NewUserModel information) {
        return firebaseUserRepository.register(information);
    }

    @Override
    public Task<QuerySnapshot> executeMyPost() {
        return null;
    }
}