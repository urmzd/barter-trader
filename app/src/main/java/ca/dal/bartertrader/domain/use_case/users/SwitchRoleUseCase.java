package ca.dal.bartertrader.domain.use_case.users;

import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import io.reactivex.rxjava3.core.Completable;

public class SwitchRoleUseCase extends AbstractBaseUseCase<Void, Completable> {
    private final FirebaseUserRepository firebaseUserRepository;

    public SwitchRoleUseCase(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public Completable execute(Void request) {
        return firebaseUserRepository.switchRole();
    }
}
