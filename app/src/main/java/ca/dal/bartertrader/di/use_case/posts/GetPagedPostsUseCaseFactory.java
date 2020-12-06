package ca.dal.bartertrader.di.use_case.posts;

import ca.dal.bartertrader.data.repository.FirebasePostsRepository;
import ca.dal.bartertrader.di.Factory;
import ca.dal.bartertrader.domain.use_case.posts.GetPagedPostsUseCase;

public class GetPagedPostsUseCaseFactory implements Factory<GetPagedPostsUseCase> {
    private final FirebasePostsRepository firebasePostsRepository;

    public GetPagedPostsUseCaseFactory(FirebasePostsRepository firebasePostsRepository) {
        this.firebasePostsRepository = firebasePostsRepository;
    }

    @Override
    public GetPagedPostsUseCase create() {
        return new GetPagedPostsUseCase(firebasePostsRepository);
    }
}
