package ca.dal.bartertrader.domain.use_case.abstracts;

import androidx.lifecycle.LiveData;

import ca.dal.bartertrader.utils.handler.resource.Resource;

public abstract class AbstractBaseUseCase<RequestT, ResponseT> {
    public abstract ResponseT execute(RequestT request);
}
