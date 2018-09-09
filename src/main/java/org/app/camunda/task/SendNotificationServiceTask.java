package org.app.camunda.task;

import org.app.common.util.EventParams;
import org.app.domain.event.CannotCompleteRecommendedEvent;
import org.app.domain.event.CompleteRecommendedRequestEvent;
import org.app.domain.event.DomainEvent;
import org.app.kafka.EventSender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SendNotificationServiceTask implements JavaDelegate {

    private final static Logger log = LoggerFactory.getLogger(SendNotificationServiceTask.class);

    private EventSender eventSender;

    @Inject
    public SendNotificationServiceTask(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    @Value("${notification.topic}")
    private String notificationTopic;

    @Override
    public void execute(DelegateExecution execution) {

        log.info("Executing for process instance id {}", execution.getProcessInstanceId());

        String requestId = String.valueOf(execution.getVariable(EventParams.REQUEST_ID));
        //TODO Może coś lepszego niż cast?
        boolean isDataValid = (boolean) execution.getVariable(EventParams.DATA_VALID);

        DomainEvent notificationEvent;

        if (isDataValid) {
            notificationEvent = new CompleteRecommendedRequestEvent(requestId);
        } else {
            notificationEvent = new CannotCompleteRecommendedEvent(requestId);
        }

        eventSender.sendEvent(notificationEvent, notificationTopic);
    }
}
