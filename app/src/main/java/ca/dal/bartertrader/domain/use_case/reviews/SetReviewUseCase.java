package ca.dal.bartertrader.domain.use_case.reviews;

import ca.dal.bartertrader.data.repository.FirebaseReviewRepository;
import ca.dal.bartertrader.domain.model.ReviewModel;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import io.reactivex.rxjava3.core.Completable;

public class SetReviewUseCase extends AbstractBaseUseCase<ReviewModel, Completable> {
    private final FirebaseReviewRepository firebaseReviewsRepository;

    public SetReviewUseCase(FirebaseReviewRepository firebaseReviewsRepository) {
        this.firebaseReviewsRepository = firebaseReviewsRepository;
    }

    @Override
    public Completable execute(ReviewModel review) {
        firebaseReviewsRepository.setOfferComplete(review.getOfferId());
        return firebaseReviewsRepository.setReview(review);
    }
}