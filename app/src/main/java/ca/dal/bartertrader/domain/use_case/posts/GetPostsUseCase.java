package ca.dal.bartertrader.domain.use_case.posts;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import ca.dal.bartertrader.data.repository.FirebasePostsRepository;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;

public class GetPostsUseCase extends AbstractBaseUseCase<Void, Task<QuerySnapshot>> {

    private final FirebasePostsRepository firebasePostsRepository;

    public GetPostsUseCase(FirebasePostsRepository firebasePostsRepository) {
        this.firebasePostsRepository = firebasePostsRepository;
    }

    @Override
    public Task<QuerySnapshot> execute(Void request) {
        return firebasePostsRepository.getPosts();
    }
}
