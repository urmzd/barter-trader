package ca.dal.bartertrader.di.use_case.users;

import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.di.Factory;
import ca.dal.bartertrader.domain.use_case.users.RegisterUseCase;

public class RegisterUseCaseFactory implements Factory<RegisterUseCase> {
    private final FirebaseUserRepository firebaseUserRepository;

    public RegisterUseCaseFactory(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public RegisterUseCase create() {
        return new RegisterUseCase(firebaseUserRepository);
    }
}
