package ca.dal.bartertrader.presentation.view_model.receiver_home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import ca.dal.bartertrader.data.model.FirebasePostModel;
import ca.dal.bartertrader.domain.model.ReceiverPostQuery;
import ca.dal.bartertrader.domain.use_case.posts.GetPagedPostsUseCase;
import ca.dal.bartertrader.domain.use_case.users.SwitchRoleUseCase;
import ca.dal.bartertrader.utils.handler.live_data.event.LiveEvent;
import ca.dal.bartertrader.utils.handler.resource.Resource;
import ca.dal.bartertrader.utils.handler.resource.Status;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import kotlinx.coroutines.CoroutineScope;

public class ReceiverHomeViewModel extends ViewModel {

    CompositeDisposable disposables = new CompositeDisposable();

    private final GetPagedPostsUseCase getPagedPostsUseCase;
    private final SwitchRoleUseCase switchRoleUseCase;

    public ReceiverHomeViewModel(GetPagedPostsUseCase getPagedPostsUseCase, SwitchRoleUseCase switchRoleUseCase) {
        this.getPagedPostsUseCase = getPagedPostsUseCase;
        this.switchRoleUseCase = switchRoleUseCase;
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

    private final LiveEvent<Resource<Void>> switchRoleResults = new LiveEvent<>();
    private final LiveData<Status> switchRoleStatus = Transformations.map(switchRoleResults, Resource::getStatus);

    public LiveData<Status> getSwitchRoleStatus() {
        return switchRoleStatus;
    }

    public void switchRole() {
        disposables.add(
                switchRoleUseCase.execute(null).observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(__ -> switchRoleResults.setValue(Resource.pending(null)))
                        .subscribe(() -> switchRoleResults.setValue(Resource.fulfilled(null)), throwable -> switchRoleResults.setValue(Resource.rejected(throwable)))
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
