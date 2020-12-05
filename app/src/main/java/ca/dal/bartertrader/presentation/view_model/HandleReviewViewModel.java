package ca.dal.bartertrader.presentation.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import ca.dal.bartertrader.domain.model.OfferModel;
import ca.dal.bartertrader.domain.model.OfferStatus;
import ca.dal.bartertrader.domain.model.ReviewModel;
import ca.dal.bartertrader.domain.use_case.reviews.SetReviewUseCase;
import ca.dal.bartertrader.utils.handler.live_data.event.LiveEvent;
import ca.dal.bartertrader.utils.handler.resource.Resource;
import ca.dal.bartertrader.utils.handler.resource.Status;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class HandleReviewViewModel extends ViewModel {
    public final MutableLiveData<String> review = new MutableLiveData<>();
    public final MutableLiveData<Float> rating = new MutableLiveData<>();
    private final SetReviewUseCase setReviewUseCase;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final LiveEvent<Resource<Void>> reviewResults = new LiveEvent<>();
    private final LiveData<Status> reviewStatus = Transformations.map(reviewResults, Resource::getStatus);
    private OfferModel offerModel;

    public HandleReviewViewModel(SetReviewUseCase setReviewUseCase) {
        this.setReviewUseCase = setReviewUseCase;
    }

    public void setReview() {
        offerModel.setStatus(OfferStatus.valueOf("COMPLETE"));
        ReviewModel reviewModel = new ReviewModel(
                offerModel.getReceiverPost().getAuthUid(),
                offerModel.getId(), review.getValue(), rating.getValue(),
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName()
        );
        compositeDisposable.add(
                setReviewUseCase.execute(reviewModel)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(__ -> reviewResults.setValue(Resource.pending(null)))
                        .subscribe(() -> reviewResults.setValue(Resource.fulfilled(null)), throwable -> reviewResults.setValue(Resource.rejected(throwable)))
        );
    }

    public void setOffer(OfferModel offerModel) {
        this.offerModel = offerModel;
    }

    public LiveData<Status> getReviewStatus() {
        return reviewStatus;
    }

    public LiveEvent<Resource<Void>> getReviewResults() {
        return reviewResults;
    }
}
