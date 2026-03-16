package ca.dal.bartertrader.domain.use_case.posts;

import com.google.firebase.firestore.QuerySnapshot;

import ca.dal.bartertrader.data.repository.FirebasePostsRepository;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import io.reactivex.rxjava3.core.Single;

public class GetPostsUseCase extends AbstractBaseUseCase<Void, Single<QuerySnapshot>> {

    private final FirebasePostsRepository firebasePostsRepository;

    public GetPostsUseCase(FirebasePostsRepository firebasePostsRepository) {
        this.firebasePostsRepository = firebasePostsRepository;
    }

    @Override
    public Single<QuerySnapshot> execute(Void request) {
        return firebasePostsRepository.getPosts();
    }
}
