package ca.dal.bartertrader.domain.use_case;

import androidx.lifecycle.LiveData;

import ca.dal.bartertrader.data.repository.FirebasePostsRepository;
import ca.dal.bartertrader.domain.model.PostPOJO;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import ca.dal.bartertrader.utils.handler.resource.Resource;

public class SetPostBaseUseCase extends AbstractBaseUseCase<PostPOJO, LiveData<Resource<Void>>> {
    private final FirebasePostsRepository firebasePostsRepository;

    public SetPostBaseUseCase(FirebasePostsRepository firebasePostsRepository) {
        this.firebasePostsRepository = firebasePostsRepository;
    }

    @Override
    public LiveData<Resource<Void>> execute(PostPOJO input) {
        return firebasePostsRepository.setPost(input);
    }
}
