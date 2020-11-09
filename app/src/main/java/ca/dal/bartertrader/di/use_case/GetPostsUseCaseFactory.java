package ca.dal.bartertrader.di.use_case;

import ca.dal.bartertrader.data.repository.FirebasePostsRepository;
import ca.dal.bartertrader.di.Factory;
import ca.dal.bartertrader.domain.use_case.GetPostsUseCase;

public class GetPostsUseCaseFactory implements Factory<GetPostsUseCase> {
    private final FirebasePostsRepository firebasePostsRepository;

    public GetPostsUseCaseFactory(FirebasePostsRepository firebasePostsRepository) {
        this.firebasePostsRepository = firebasePostsRepository;
    }

    @Override
    public GetPostsUseCase create() {
        return new GetPostsUseCase(firebasePostsRepository);
    }
}
