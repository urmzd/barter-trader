package ca.dal.bartertrader.presentation.view_model.receiver_home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import ca.dal.bartertrader.data.model.FirebasePostModel;
import ca.dal.bartertrader.domain.model.ReceiverPostQuery;
import ca.dal.bartertrader.domain.use_case.posts.GetPagedPostsUseCase;
import ca.dal.bartertrader.utils.handler.live_data.event.LiveEvent;
import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class ReceiverHomeViewModel extends ViewModel {

    private final GetPagedPostsUseCase getPagedPostsUseCase;


    public ReceiverHomeViewModel(GetPagedPostsUseCase getPagedPostsUseCase) {
        this.getPagedPostsUseCase = getPagedPostsUseCase;
    }

    private final MutableLiveData<Boolean> sortBy = new MutableLiveData<>();
    private final MutableLiveData<String> fieldFieldBy = new MutableLiveData<>();
    private final LiveEvent<Void> refresh = new LiveEvent<>();

    public Flowable<PagingData<FirebasePostModel>> retrievePosts(String query) {
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        ReceiverPostQuery receiverPostQuery = new ReceiverPostQuery(sortBy.getValue(), fieldFieldBy.getValue(), query);
        return PagingRx.cachedIn(getPagedPostsUseCase.execute(receiverPostQuery), viewModelScope);
    }

    public LiveEvent<Void> getRefresh() {
        return refresh;
    }

    public void triggerRefresh() {
        refresh.call();
    }

    public void setSortBy(Boolean ascend) {
        sortBy.setValue(ascend);
    }

    public void setFilterFieldBy(String field) {
        fieldFieldBy.setValue(field);
    }

}
