package ca.dal.bartertrader.domain.use_case.users;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;

import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import io.reactivex.rxjava3.core.Single;

public class GetUserUseCase extends AbstractBaseUseCase<Void, Single<FirebaseUser>> {

    private final FirebaseUserRepository firebaseUserRepository;

    public GetUserUseCase(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public Single<FirebaseUser> execute(Void input) {
        return firebaseUserRepository.getUser();
    }

    @Override
    public Task<QuerySnapshot> executeMyPost() {
        return null;
    }
}
