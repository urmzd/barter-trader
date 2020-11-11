package ca.dal.bartertrader.di.use_case.users;

import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.di.Factory;
import ca.dal.bartertrader.domain.use_case.users.LoginUseCase;

public class LoginUseCaseFactory implements Factory<LoginUseCase> {
    private final FirebaseUserRepository firebaseUserRepository;

    public LoginUseCaseFactory(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public LoginUseCase create() {
        return new LoginUseCase(firebaseUserRepository);
    }
}
