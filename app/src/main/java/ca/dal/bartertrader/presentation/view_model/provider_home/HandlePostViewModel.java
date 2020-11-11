package ca.dal.bartertrader.presentation.view_model.provider_home;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ca.dal.bartertrader.domain.use_case.posts.SetPostBaseUseCase;
import ca.dal.bartertrader.utils.FormValidatorTools;
import ca.dal.bartertrader.utils.functionals.Transformers;
import ca.dal.bartertrader.utils.handler.live_data.TransformedLiveData;
import ca.dal.bartertrader.utils.handler.live_data.event.LiveEvent;

public class HandlePostViewModel extends ViewModel {

    private final SetPostBaseUseCase setPostBaseUseCase;

    public HandlePostViewModel(SetPostBaseUseCase setPostBaseUseCase) {
        this.setPostBaseUseCase = setPostBaseUseCase;
    }

    public final MutableLiveData<String> title = new MutableLiveData<>();
    public final MutableLiveData<String> description = new MutableLiveData<>();
    private final MutableLiveData<Uri> image = new MutableLiveData<>();

    private final LiveData<Boolean> titleIsValid = Transformations.map(title, FormValidatorTools::isTextValid);
    private final LiveData<Boolean> imageIsValid = Transformations.map(image, Objects::nonNull);

    private final List<LiveData<Boolean>> form = new ArrayList<>(Arrays.asList(titleIsValid, imageIsValid));
    private final TransformedLiveData<Boolean, Boolean> formIsValid = new TransformedLiveData<>(form, Transformers::every);

    public LiveEvent<Void> getUploadImageFromCameraEvent() {
        return uploadImageFromCameraEvent;
    }

    public LiveEvent<Void> getUploadImageFromGalleryEvent() {
        return uploadImageFromGalleryEvent;
    }

    public void setImage(Uri image) {
        this.image.setValue(image);
    }

    private final LiveEvent<Void> uploadImageFromCameraEvent = new LiveEvent<>();
    private final LiveEvent<Void> uploadImageFromGalleryEvent = new LiveEvent<>();

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
}
