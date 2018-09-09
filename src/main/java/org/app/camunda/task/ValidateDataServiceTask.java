package org.app.camunda.task;

import org.app.common.util.EventParams;
import org.app.mongo.RequestDataWrapperRepository;
import org.app.mongo.RequestMetaData;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;

@Named
public class ValidateDataServiceTask implements JavaDelegate {

    private final static Logger log = LoggerFactory.getLogger(ValidateDataServiceTask.class);

    private RequestDataWrapperRepository requestDataWrapperRepository;

    @Inject
    public ValidateDataServiceTask(RequestDataWrapperRepository requestDataWrapperRepository) {
        this.requestDataWrapperRepository = requestDataWrapperRepository;
    }

    @Override
    public void execute(DelegateExecution execution) {

        log.info("Executing for process instance id {}", execution.getProcessInstanceId());

        BigDecimal expectedCurrencyQuantity = (BigDecimal) execution.getVariable(EventParams.CURRENCY_QUANTITY);
        String requestId = String.valueOf(execution.getVariable(EventParams.REQUEST_ID));

        RequestMetaData metaDataForRequestId = requestDataWrapperRepository.getRequestMetadataForRequestId(requestId);

        if (metaDataForRequestId == null) {
            execution.setVariable(EventParams.DATA_VALID, false);
            return;
        }

        BigDecimal documentCount = BigDecimal.valueOf(metaDataForRequestId.getDocumentCount());

        if (expectedCurrencyQuantity.compareTo(documentCount) <= 0) {
            execution.setVariable(EventParams.DATA_VALID, true);
        } else {
            execution.setVariable(EventParams.DATA_VALID, false);
        }
    }
}
