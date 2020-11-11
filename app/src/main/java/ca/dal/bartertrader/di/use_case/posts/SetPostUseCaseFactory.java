package ca.dal.bartertrader.di.use_case.posts;

import ca.dal.bartertrader.data.repository.FirebasePostsRepository;
import ca.dal.bartertrader.di.Factory;
import ca.dal.bartertrader.domain.use_case.posts.SetPostBaseUseCase;

public class SetPostUseCaseFactory implements Factory<SetPostBaseUseCase> {
    private final FirebasePostsRepository firebasePostsRepository;

    public SetPostUseCaseFactory(FirebasePostsRepository firebasePostsRepository) {
        this.firebasePostsRepository = firebasePostsRepository;
    }

    @Override
    public SetPostBaseUseCase create() {
        return new SetPostBaseUseCase(firebasePostsRepository);
    }
}
