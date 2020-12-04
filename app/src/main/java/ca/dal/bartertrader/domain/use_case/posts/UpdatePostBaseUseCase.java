package ca.dal.bartertrader.domain.use_case.posts;

import android.util.Pair;

import ca.dal.bartertrader.data.repository.FirebasePostsRepository;
import ca.dal.bartertrader.domain.model.PostModel;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import io.reactivex.rxjava3.core.Completable;

public class UpdatePostBaseUseCase extends AbstractBaseUseCase<Pair<PostModel, String>, Completable> {
    private final FirebasePostsRepository firebasePostsRepository;

    public UpdatePostBaseUseCase(FirebasePostsRepository firebasePostsRepository) {
        this.firebasePostsRepository = firebasePostsRepository;
    }

    @Override
    public Completable execute(Pair<PostModel, String> input) {
        return firebasePostsRepository.updatePost(input.first, input.second);
    }
}
