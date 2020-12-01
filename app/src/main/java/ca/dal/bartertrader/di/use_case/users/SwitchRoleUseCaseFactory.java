package ca.dal.bartertrader.di.use_case.users;

import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.di.Factory;
import ca.dal.bartertrader.domain.use_case.users.SwitchRoleUseCase;

public class SwitchRoleUseCaseFactory implements Factory<SwitchRoleUseCase> {
    private final FirebaseUserRepository firebaseUserRepository;

    public SwitchRoleUseCaseFactory(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public SwitchRoleUseCase create() {
        return new SwitchRoleUseCase(firebaseUserRepository);
    }
}
