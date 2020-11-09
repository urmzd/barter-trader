package ca.dal.bartertrader.domain.use_case;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;

import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import ca.dal.bartertrader.utils.handler.resource.Resource;

public class GetUserUseCase extends AbstractBaseUseCase<Void, LiveData<Resource<FirebaseUser>>> {

    private final FirebaseUserRepository firebaseUserRepository;

    public GetUserUseCase(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public LiveData<Resource<FirebaseUser>> execute(Void input) {
        return firebaseUserRepository.getUser();
    }
}
