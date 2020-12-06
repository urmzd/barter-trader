package ca.dal.bartertrader.presentation.view_model.provider_home;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import ca.dal.bartertrader.domain.model.PostModel;
import ca.dal.bartertrader.domain.use_case.posts.GetPostsUseCase;
import ca.dal.bartertrader.domain.use_case.users.SwitchRoleUseCase;
import ca.dal.bartertrader.utils.handler.live_data.event.LiveEvent;
import ca.dal.bartertrader.utils.handler.resource.Resource;
import ca.dal.bartertrader.utils.handler.resource.Status;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ProviderHomeViewModel extends ViewModel {

    private final GetPostsUseCase getPostsUseCase;
    private final SwitchRoleUseCase switchRoleUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public ProviderHomeViewModel(GetPostsUseCase getPostsUseCase, SwitchRoleUseCase switchRoleUseCase) {
        this.getPostsUseCase = getPostsUseCase;
        this.switchRoleUseCase = switchRoleUseCase;

        getPostsUseCase.execute(null).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    document.getId();
                    PostModel postModel = document.toObject(PostModel.class);
                    postModel.setImage(Uri.parse("https://firebasestorage.googleapis.com/v0/b/barter-trader-6ca98.appspot.com/o/posts%2F"+document.getId()+".jpg"));
                    postModel.setImageName(document.getId());
                    postModels.add(postModel/*document.toObject(PostModel.class)*/);
                }
                postItemList.setValue(postModels);
            }
        });
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

    private ArrayList<PostModel> postModels = new ArrayList<>();
    public MutableLiveData<ArrayList<PostModel>> postItemList = new MutableLiveData<>();

}
