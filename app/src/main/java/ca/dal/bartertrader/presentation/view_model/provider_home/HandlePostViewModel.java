package ca.dal.bartertrader.presentation.view_model.provider_home;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ca.dal.bartertrader.domain.model.PostPOJO;
import ca.dal.bartertrader.domain.use_case.posts.SetPostBaseUseCase;
import ca.dal.bartertrader.utils.FormValidatorTools;
import ca.dal.bartertrader.utils.functionals.Transformers;
import ca.dal.bartertrader.utils.handler.live_data.TransformedLiveData;
import ca.dal.bartertrader.utils.handler.live_data.event.LiveEvent;
import ca.dal.bartertrader.utils.handler.resource.Resource;
import ca.dal.bartertrader.utils.handler.resource.Status;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class HandlePostViewModel extends ViewModel {

    private final SetPostBaseUseCase setPostBaseUseCase;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public HandlePostViewModel(SetPostBaseUseCase setPostBaseUseCase) {
        this.setPostBaseUseCase = setPostBaseUseCase;
    }

    public final MutableLiveData<String> title = new MutableLiveData<>();
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

    public void setPost() {
        PostPOJO newPost = new PostPOJO(image.getValue(), title.getValue(), description.getValue());
        compositeDisposable.add(
                setPostBaseUseCase.execute(newPost)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(__ -> postResults.setValue(Resource.pending(null)))
                        .subscribe(() -> postResults.setValue(Resource.fulfilled(null)), throwable -> postResults.setValue(Resource.rejected(throwable)))
        );
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

}
