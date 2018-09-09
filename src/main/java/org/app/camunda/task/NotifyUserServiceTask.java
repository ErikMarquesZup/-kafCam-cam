package org.app.camunda.task;

import org.app.common.util.EventParams;
import org.app.domain.event.RequestWaitForUserEvent;
import org.app.kafka.EventSender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class NotifyUserServiceTask implements JavaDelegate {

    private final static Logger log = LoggerFactory.getLogger(NotifyUserServiceTask.class);

    private EventSender eventSender;

    @Value("${notification.topic}")
    private String notificationTopic;

    @Inject
    public NotifyUserServiceTask(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    @Override
    public void execute(DelegateExecution execution) {

        log.info("Executing for process instance id {}", execution.getProcessInstanceId());

        String requestId = String.valueOf(execution.getVariable(EventParams.REQUEST_ID));
        String processInstanceId = execution.getProcessInstanceId();

        RequestWaitForUserEvent requestWaitForUserEvent = new RequestWaitForUserEvent(requestId, processInstanceId);

        eventSender.sendEvent(requestWaitForUserEvent, notificationTopic);
    }
}
