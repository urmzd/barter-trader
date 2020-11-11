package ca.dal.bartertrader.domain.use_case.users;

import com.google.firebase.auth.AuthResult;

import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.domain.model.LoginPOJO;
import ca.dal.bartertrader.domain.use_case.abstracts.AbstractBaseUseCase;
import io.reactivex.rxjava3.core.Single;

public class LoginUseCase extends AbstractBaseUseCase<LoginPOJO, Single<AuthResult>> {

    private final FirebaseUserRepository firebaseUserRepository;

    public LoginUseCase(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public Single<AuthResult> execute(LoginPOJO credentials) {
        return firebaseUserRepository.login(credentials);
    }
}
