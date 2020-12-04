package ca.dal.bartertrader.presentation.view_model.receiver_home;

import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import ca.dal.bartertrader.domain.use_case.posts.GetPostsUseCase;
import ca.dal.bartertrader.utils.handler.live_data.event.LiveEvent;
import ca.dal.bartertrader.utils.handler.resource.Resource;
import io.reactivex.rxjava3.core.Single;

public class ReceiverHomeViewModel extends ViewModel {

    private final GetPostsUseCase getPostsUseCase;


    public ReceiverHomeViewModel(GetPostsUseCase getPostsUseCase) {
        this.getPostsUseCase = getPostsUseCase;
    }

    private final LiveEvent<Single<QuerySnapshot>> querySnapshots = new LiveEvent<>();
    private final LiveEvent<Single<List<byte[]>>> imageSnapshots = new LiveEvent<>();

    public void retrievePosts() {
    }


}
