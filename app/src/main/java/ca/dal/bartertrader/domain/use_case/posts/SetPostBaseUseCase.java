package ca.dal.bartertrader.domain.use_case.posts;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import ca.dal.bartertrader.data.repository.FirebasePostsRepository;
import ca.dal.bartertrader.domain.model.PostModel;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import io.reactivex.rxjava3.core.Completable;

public class SetPostBaseUseCase extends AbstractBaseUseCase<PostModel, Completable> {
    private final FirebasePostsRepository firebasePostsRepository;

    public SetPostBaseUseCase(FirebasePostsRepository firebasePostsRepository) {
        this.firebasePostsRepository = firebasePostsRepository;
    }

    @Override
    public Completable execute(PostModel input) {
        return firebasePostsRepository.setPost(input);
    }

    @Override
    public Task<QuerySnapshot> executeMyPost() {
        return null;
    }
}
