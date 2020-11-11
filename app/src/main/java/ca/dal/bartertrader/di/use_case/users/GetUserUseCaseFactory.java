package ca.dal.bartertrader.di.use_case.users;

import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.di.Factory;

public class GetUserUseCaseFactory implements Factory<GetUserUseCaseFactory> {
    private final FirebaseUserRepository firebaseUserRepository;

    public GetUserUseCaseFactory(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public GetUserUseCaseFactory create() {
        return new GetUserUseCaseFactory(firebaseUserRepository);
    }
}
