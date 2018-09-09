package org.app.mongo;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RequestDataDataWrapperRepositoryImpl implements RequestDataWrapperRepository {

    private RequestDataWrapperMongoStore requestDataWrapperMongoStore;

    @Inject
    public RequestDataDataWrapperRepositoryImpl(RequestDataWrapperMongoStore requestDataWrapperMongoStore) {
        this.requestDataWrapperMongoStore = requestDataWrapperMongoStore;
    }

    @Override
    public RequestMetaData getRequestMetadataForRequestId(String requestId) {
        RequestDataWrapper requestDataWrapper = requestDataWrapperMongoStore.findByRequestId(requestId);

        if (requestDataWrapper == null)
            return null;

        return new RequestMetaData((long) requestDataWrapper.getCurrenciesData().size());
    }

    @Override
    public void deleteByRequestId(String requestId) {
        requestDataWrapperMongoStore.deleteByRequestId(requestId);
    }
}
