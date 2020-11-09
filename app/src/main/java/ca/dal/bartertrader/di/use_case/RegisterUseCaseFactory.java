package ca.dal.bartertrader.di.use_case;

import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.di.Factory;
import ca.dal.bartertrader.domain.use_case.RegisterUseUseCase;

public class RegisterUseCaseFactory implements Factory<RegisterUseUseCase> {
    private final FirebaseUserRepository firebaseUserRepository;

    public RegisterUseCaseFactory(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public RegisterUseUseCase create() {
        return new RegisterUseUseCase(firebaseUserRepository);
    }
}
