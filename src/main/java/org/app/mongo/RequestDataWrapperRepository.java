package org.app.mongo;

public interface RequestDataWrapperRepository {
    RequestMetaData getRequestMetadataForRequestId(String requestId);

    void deleteByRequestId(String requestId);
}
