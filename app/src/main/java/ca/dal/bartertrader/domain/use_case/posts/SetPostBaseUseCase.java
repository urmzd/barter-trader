package ca.dal.bartertrader.domain.use_case.posts;

import ca.dal.bartertrader.data.repository.FirebasePostsRepository;
import ca.dal.bartertrader.domain.model.PostPOJO;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import io.reactivex.rxjava3.core.Completable;

public class SetPostBaseUseCase extends AbstractBaseUseCase<PostPOJO, Completable> {
    private final FirebasePostsRepository firebasePostsRepository;

    public SetPostBaseUseCase(FirebasePostsRepository firebasePostsRepository) {
        this.firebasePostsRepository = firebasePostsRepository;
    }

    @Override
    public Completable execute(PostPOJO input) {
        return firebasePostsRepository.setPost(input);
    }
}
