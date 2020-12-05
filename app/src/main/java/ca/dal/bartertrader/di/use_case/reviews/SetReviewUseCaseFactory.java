package ca.dal.bartertrader.di.use_case.reviews;

import ca.dal.bartertrader.data.repository.FirebaseReviewRepository;
import ca.dal.bartertrader.di.Factory;
import ca.dal.bartertrader.domain.use_case.reviews.SetReviewUseCase;

public class SetReviewUseCaseFactory implements Factory<SetReviewUseCase> {
    private final FirebaseReviewRepository firebaseReviewRepository;

    public SetReviewUseCaseFactory(FirebaseReviewRepository firebaseReviewRepository) {
        this.firebaseReviewRepository = firebaseReviewRepository;
    }

    @Override
    public SetReviewUseCase create() {
        return new SetReviewUseCase(firebaseReviewRepository);
    }
}