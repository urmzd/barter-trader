package ca.dal.bartertrader.di.use_case;

import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.di.Factory;
import ca.dal.bartertrader.domain.use_case.LoginUserUseCase;

public class LoginUseCaseFactory implements Factory<LoginUserUseCase> {
    private final FirebaseUserRepository firebaseUserRepository;

    public LoginUseCaseFactory(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public LoginUserUseCase create() {
        return new LoginUserUseCase(firebaseUserRepository);
    }
}
