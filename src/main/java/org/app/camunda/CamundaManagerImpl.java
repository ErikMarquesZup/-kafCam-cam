package org.app.camunda;

import org.app.common.util.EventParams;
import org.app.domain.event.RecommendationResponseEvent;
import org.app.domain.event.RecommendedRequestEvent;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Named
public class CamundaManagerImpl implements CamundaManager {

    private final static Logger log = LoggerFactory.getLogger(CamundaManagerImpl.class);

    private RuntimeService runtimeService;
    private TaskService taskService;

    @Value("${camunda.process.key}")
    private String processKey;

    @Inject
    public CamundaManagerImpl(RuntimeService runtimeService, TaskService taskService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    @Override
    public void startProcessForEvent(RecommendedRequestEvent recommendedRequestEvent) {

        log.info("Starting process for currency {}, request : {}",
                recommendedRequestEvent.getCurrencySymbol(),
                recommendedRequestEvent.getRequestId());

        Map<String, Object> variables = new HashMap<>();

        variables.put(EventParams.REQUEST_ID, recommendedRequestEvent.getRequestId());
        variables.put(EventParams.CURRENCY_QUANTITY, recommendedRequestEvent.getCurrencyQuantity());
        variables.put(EventParams.CURRENCY_SYMBOL, recommendedRequestEvent.getCurrencySymbol());
        variables.put(EventParams.TIMESTAMP, recommendedRequestEvent.getTimeStamp());

        runtimeService.startProcessInstanceByKey(processKey, variables);
    }

    @Override
    public void publishMessage(String messageName, String requestId) {

        log.info("Publishing message : {} for requestId {}", messageName, requestId);

        MessageCorrelationResult messageCorrelationResult = runtimeService.createMessageCorrelation(messageName)
                .processInstanceVariableEquals(EventParams.REQUEST_ID, requestId)
                .correlateWithResult();
    }

    @Override
    public void acceptCurrencyRecommendation(RecommendationResponseEvent recommendationResponseEvent) {

        log.info("Accepted message from user of type {}", recommendationResponseEvent.getClass().getSimpleName());

        Task task = taskService
                .createTaskQuery()
                .processVariableValueEquals(EventParams.REQUEST_ID, recommendationResponseEvent.getRequestId())
                .singleResult();

        if (task == null) {
            log.info("Task for requestId {} not found", recommendationResponseEvent.getRequestId());
            return;
        }

        runtimeService.setVariable(task.getExecutionId(), EventParams.CURRENCY_OPINION, recommendationResponseEvent.getOpinion());
        runtimeService.setVariable(task.getExecutionId(), EventParams.CURRENCY_SCORE, recommendationResponseEvent.getScore());

        taskService.complete(task.getId());
    }
}
