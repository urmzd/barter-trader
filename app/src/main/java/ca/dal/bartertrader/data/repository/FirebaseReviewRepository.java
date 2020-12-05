package ca.dal.bartertrader.data.repository;

import ca.dal.bartertrader.data.data_source.FirebaseAuthDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseFirestoreDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseStorageDataSource;
import ca.dal.bartertrader.domain.model.ReviewModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FirebaseReviewRepository {
    private final FirebaseFirestoreDataSource firebaseFirestoreDataSource;
    private final FirebaseStorageDataSource firebaseStorageDataSource;
    private final FirebaseAuthDataSource firebaseAuthDataSource;

    public FirebaseReviewRepository(FirebaseFirestoreDataSource firebaseFirestoreDataSource, FirebaseStorageDataSource firebaseStorageDataSource, FirebaseAuthDataSource firebaseAuthDataSource) {
        this.firebaseFirestoreDataSource = firebaseFirestoreDataSource;
        this.firebaseStorageDataSource = firebaseStorageDataSource;
        this.firebaseAuthDataSource = firebaseAuthDataSource;
    }

    public Completable setReview(ReviewModel reviewModel) {

        return firebaseAuthDataSource.reloadUser()
                .subscribeOn(Schedulers.io())
                .andThen(Completable.defer(() -> firebaseFirestoreDataSource.addNewReview(
                        reviewModel, firebaseAuthDataSource.getUser().getUid()))
                );
    }

    public void setOfferComplete(String offerId) {
        firebaseFirestoreDataSource.setOfferComplete(offerId);
    }

}
