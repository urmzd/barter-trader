package ca.dal.bartertrader.di.use_case.posts;

import ca.dal.bartertrader.data.repository.FirebasePostsRepository;
import ca.dal.bartertrader.di.Factory;
import ca.dal.bartertrader.domain.use_case.posts.GetPostsUseCase;

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
