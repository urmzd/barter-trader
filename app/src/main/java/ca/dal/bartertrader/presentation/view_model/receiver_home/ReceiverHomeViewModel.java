package ca.dal.bartertrader.presentation.view_model.receiver_home;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import ca.dal.bartertrader.data.model.FirebasePostModel;
import ca.dal.bartertrader.domain.use_case.posts.GetPagedPostsUseCase;
import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class ReceiverHomeViewModel extends ViewModel {

    private final GetPagedPostsUseCase getPagedPostsUseCase;


    public ReceiverHomeViewModel(GetPagedPostsUseCase getPagedPostsUseCase) {
        this.getPagedPostsUseCase = getPagedPostsUseCase;
    }

    public Flowable<PagingData<FirebasePostModel>> retrievePosts(String query) {
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        return PagingRx.cachedIn(getPagedPostsUseCase.execute(query), viewModelScope);
    }


}
