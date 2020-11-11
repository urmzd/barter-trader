package ca.dal.bartertrader.domain.use_case.posts;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import ca.dal.bartertrader.data.repository.FirebasePostsRepository;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import ca.dal.bartertrader.utils.handler.resource.Resource;

public class GetPostsUseCase extends AbstractBaseUseCase<Integer, LiveData<Resource<QueryDocumentSnapshot>>> {

    private final FirebasePostsRepository firebasePostsRepository;

    public GetPostsUseCase(FirebasePostsRepository firebasePostsRepository) {
        this.firebasePostsRepository = firebasePostsRepository;
    }

    @Override
    public LiveData<Resource<QueryDocumentSnapshot>> execute(Integer maxNumberOfPosts) {
        return firebasePostsRepository.getPosts(maxNumberOfPosts);
    }
}
