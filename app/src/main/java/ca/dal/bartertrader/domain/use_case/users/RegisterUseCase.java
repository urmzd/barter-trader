package ca.dal.bartertrader.domain.use_case.users;

import androidx.lifecycle.LiveData;

import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.domain.model.RegistrationPOJO;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import ca.dal.bartertrader.utils.handler.resource.Resource;
import io.reactivex.rxjava3.core.Completable;

public class RegisterUseCase extends AbstractBaseUseCase<RegistrationPOJO, Completable> {

    private final FirebaseUserRepository firebaseUserRepository;

    public RegisterUseCase(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public Completable execute(RegistrationPOJO information) {
        return firebaseUserRepository.register(information);
    }
}