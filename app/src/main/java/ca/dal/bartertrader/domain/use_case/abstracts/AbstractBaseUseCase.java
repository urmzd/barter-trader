package ca.dal.bartertrader.domain.use_case.abstracts;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public abstract class AbstractBaseUseCase<RequestT, ResourceT> {
    public abstract ResourceT execute(RequestT request);
}
