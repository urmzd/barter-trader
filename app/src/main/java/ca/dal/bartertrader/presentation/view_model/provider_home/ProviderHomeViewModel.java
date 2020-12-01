package ca.dal.bartertrader.presentation.view_model.provider_home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import ca.dal.bartertrader.domain.use_case.posts.GetPostsUseCase;
import ca.dal.bartertrader.domain.use_case.users.SwitchRoleUseCase;
import ca.dal.bartertrader.utils.handler.live_data.event.LiveEvent;
import ca.dal.bartertrader.utils.handler.resource.Resource;
import ca.dal.bartertrader.utils.handler.resource.Status;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class ProviderHomeViewModel extends ViewModel {

    private final GetPostsUseCase getPostsUseCase;
    private final SwitchRoleUseCase switchRoleUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public ProviderHomeViewModel(GetPostsUseCase getPostsUseCase, SwitchRoleUseCase switchRoleUseCase) {
        this.getPostsUseCase = getPostsUseCase;
        this.switchRoleUseCase = switchRoleUseCase;
    }

    private final LiveEvent<Resource<Void>> switchRoleResults = new LiveEvent<>();
    private final LiveData<Status> switchRoleStatus = Transformations.map(switchRoleResults, Resource::getStatus);

    private final LiveEvent<Void> addPostEvent = new LiveEvent<>();

    public LiveEvent<Resource<Void>> getSwitchRoleResults() {
        return switchRoleResults;
    }

    public LiveData<Status> getSwitchRoleStatus() {
        return switchRoleStatus;
    }

    public LiveEvent<Void> getAddPostEvent() {
        return addPostEvent;
    }

    public void switchRole() {
        disposables.add(
                switchRoleUseCase.execute(null).observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(__ -> switchRoleResults.setValue(Resource.pending(null)))
                        .subscribe(() -> switchRoleResults.setValue(Resource.fulfilled(null)), throwable -> switchRoleResults.setValue(Resource.rejected(throwable)))
        );
    }

    public void addPost() {
        addPostEvent.call();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

}
