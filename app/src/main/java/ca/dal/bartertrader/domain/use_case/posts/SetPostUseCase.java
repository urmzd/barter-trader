package ca.dal.bartertrader.domain.use_case.posts;

import ca.dal.bartertrader.data.repository.FirebasePostsRepository;
import ca.dal.bartertrader.domain.model.PostModel;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import io.reactivex.rxjava3.core.Completable;

public class SetPostUseCase extends AbstractBaseUseCase<PostModel, Completable> {
    private final FirebasePostsRepository firebasePostsRepository;

    public SetPostUseCase(FirebasePostsRepository firebasePostsRepository) {
        this.firebasePostsRepository = firebasePostsRepository;
    }

    @Override
    public Completable execute(PostModel input) {
        return firebasePostsRepository.setPost(input);
    }
}
