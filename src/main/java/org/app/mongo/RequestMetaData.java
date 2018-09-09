package org.app.mongo;

public class RequestMetaData {
    private Long documentCount;

    public RequestMetaData(Long documentCount) {
        this.documentCount = documentCount;
    }

    public Long getDocumentCount() {
        return documentCount;
    }
}
