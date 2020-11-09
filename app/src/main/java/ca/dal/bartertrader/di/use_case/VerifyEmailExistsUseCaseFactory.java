package ca.dal.bartertrader.di.use_case;

import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.di.Factory;
import ca.dal.bartertrader.domain.use_case.VerifyEmailExistsUseCase;

public class VerifyEmailExistsUseCaseFactory implements Factory<ca.dal.bartertrader.domain.use_case.VerifyEmailExistsUseCase> {
    private final FirebaseUserRepository firebaseUserRepository;

    public VerifyEmailExistsUseCaseFactory(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public ca.dal.bartertrader.domain.use_case.VerifyEmailExistsUseCase create() {
        return new ca.dal.bartertrader.domain.use_case.VerifyEmailExistsUseCase(firebaseUserRepository);
    }
}
