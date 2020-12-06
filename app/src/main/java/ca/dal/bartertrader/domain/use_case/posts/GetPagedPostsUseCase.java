package ca.dal.bartertrader.domain.use_case.posts;

import androidx.paging.PagingData;

import ca.dal.bartertrader.data.model.FirebasePostModel;
import ca.dal.bartertrader.data.repository.FirebasePostsRepository;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import io.reactivex.rxjava3.core.Flowable;

public class GetPagedPostsUseCase extends AbstractBaseUseCase<String, Flowable<PagingData<FirebasePostModel>>> {

    private final FirebasePostsRepository firebasePostsRepository;

    public GetPagedPostsUseCase(FirebasePostsRepository firebasePostsRepository) {
        this.firebasePostsRepository = firebasePostsRepository;

    }

    @Override
    public Flowable<PagingData<FirebasePostModel>> execute(String request) {
        return firebasePostsRepository.getPagedPost(request);
    }
}
