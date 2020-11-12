package ca.dal.bartertrader.domain.use_case.abstracts;

public abstract class AbstractBaseUseCase<RequestT, ResourceT> {
    public abstract ResourceT execute(RequestT request);
}
