package ca.dal.bartertrader.di.use_case;

import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.di.Factory;
import ca.dal.bartertrader.domain.use_case.ResetPasswordUseCase;

public class ResetPasswordUseCaseFactory implements Factory<ResetPasswordUseCase> {
    private final FirebaseUserRepository firebaseUserRepository;

    public ResetPasswordUseCaseFactory(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public ResetPasswordUseCase create() {
        return new ResetPasswordUseCase(firebaseUserRepository);
    }
}
