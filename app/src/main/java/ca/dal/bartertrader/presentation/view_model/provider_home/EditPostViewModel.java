package ca.dal.bartertrader.presentation.view_model.provider_home;

import android.net.Uri;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ca.dal.bartertrader.domain.model.PostModel;
import ca.dal.bartertrader.domain.use_case.posts.SetPostBaseUseCase;
import ca.dal.bartertrader.domain.use_case.posts.UpdatePostBaseUseCase;
import ca.dal.bartertrader.utils.FormValidatorTools;
import ca.dal.bartertrader.utils.LocationServiceManager;
import ca.dal.bartertrader.utils.functionals.Transformers;
import ca.dal.bartertrader.utils.handler.live_data.TransformedLiveData;
import ca.dal.bartertrader.utils.handler.live_data.event.LiveEvent;
import ca.dal.bartertrader.utils.handler.resource.Resource;
import ca.dal.bartertrader.utils.handler.resource.Status;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class EditPostViewModel extends ViewModel {

    private final UpdatePostBaseUseCase updatePostBaseUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private String postUid;
    private double lat;
    private double lon;

    public EditPostViewModel(UpdatePostBaseUseCase updatePostBaseUseCase) {
        this.updatePostBaseUseCase = updatePostBaseUseCase;
    }

    public final MutableLiveData<String> title = new MutableLiveData<>();
    public final MutableLiveData<String> location = new MutableLiveData<>();
    public final MutableLiveData<String> description = new MutableLiveData<>();
    public final MutableLiveData<Uri> image = new MutableLiveData<>();

    private final LiveData<Boolean> titleIsValid = Transformations.map(title, FormValidatorTools::isTextValid);
    private final LiveData<Boolean> imageIsValid = Transformations.map(image, Objects::nonNull);

    private final List<LiveData<Boolean>> form = new ArrayList<>(Arrays.asList(titleIsValid, imageIsValid));
    private final TransformedLiveData<Boolean, Boolean> formIsValid = new TransformedLiveData<>(form, Transformers::every);

    private final LiveEvent<Void> uploadImageFromCameraEvent = new LiveEvent<>();
    private final LiveEvent<Void> uploadImageFromGalleryEvent = new LiveEvent<>();

    private final LiveEvent<Resource<Void>> postResults = new LiveEvent<>();

    private final LiveData<Status> postStatus = Transformations.map(postResults, Resource::getStatus);

    public void setImage(Uri imageUri) {
        image.setValue(imageUri);
    }

    public void setLocation(String city, String province)
    {
        location.setValue(city + ", " + province);
    }

    // Storing the original post's uid, needed for updating firebase
    public void setPostUid(String postUid)
    {
        this.postUid = postUid;
    }

    public void updatePost() {
        PostModel newPostModel = new PostModel(image.getValue(), title.getValue(), description.getValue(), lat, lon);
        Pair<PostModel,String> execInput = Pair.create(newPostModel, postUid);
        disposables.add(
                updatePostBaseUseCase.execute(execInput)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(__ -> postResults.setValue(Resource.pending(null)))
                        .subscribe(() -> postResults.setValue(Resource.fulfilled(null)), throwable -> postResults.setValue(Resource.rejected(throwable)))
        );
    }

    public void setExistingPostData(String existingTitle, String existingDescription, Uri imageUri, double lat, double lon)
    {
        title.setValue(existingTitle);
        description.setValue(existingDescription);
        image.setValue(imageUri);
        this.lat = lat;
        this.lon = lon;

        LocationServiceManager lsm = LocationServiceManager.getInstance();
        if (lsm != null)
        {
            setLocation(lsm.getCityFromCoords(lon, lat), lsm.getProvinceFromCoords(lon, lat));
        }
    }

    public LiveEvent<Void> getUploadImageFromCameraEvent() {
        return uploadImageFromCameraEvent;
    }

    public LiveEvent<Void> getUploadImageFromGalleryEvent() {
        return uploadImageFromGalleryEvent;
    }

    public LiveEvent<Resource<Void>> getPostResults() {
        return postResults;
    }

    public void takePicture() {
        uploadImageFromCameraEvent.call();
    }

    public void pickPicture() {
        uploadImageFromGalleryEvent.call();
    }

    public TransformedLiveData<Boolean, Boolean> getFormIsValid() {
        return formIsValid;
    }

    public LiveData<Boolean> getTitleIsValid() {
        return titleIsValid;
    }

    public LiveData<Boolean> getImageIsValid() {
        return imageIsValid;
    }

    public LiveData<Status> getPostStatus() {
        return postStatus;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
