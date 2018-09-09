package org.app.camunda.task;

import org.app.common.util.EventParams;
import org.app.domain.event.DataNotInCacheEvent;
import org.app.kafka.EventSender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RequestDataServiceTask implements JavaDelegate {

    private final static Logger log = LoggerFactory.getLogger(RequestDataServiceTask.class);

    private EventSender eventSender;

    @Value("${worker.topic}")
    private String workerTopic;

    @Inject
    public RequestDataServiceTask(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    @Override
    public void execute(DelegateExecution execution) {

        log.info("Executing for process instance id {}", execution.getProcessInstanceId());

        String requestId = String.valueOf(execution.getVariable(EventParams.REQUEST_ID));
        String currencySymbol = String.valueOf(execution.getVariable(EventParams.CURRENCY_SYMBOL));
        Long timeStamp = Long.valueOf(String.valueOf(execution.getVariable(EventParams.TIMESTAMP)));

        DataNotInCacheEvent dataNotInCacheEvent = new DataNotInCacheEvent(requestId, currencySymbol, timeStamp);

        eventSender.sendEvent(dataNotInCacheEvent, workerTopic);
    }
}
