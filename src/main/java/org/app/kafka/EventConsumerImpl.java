package org.app.kafka;

import org.app.camunda.CamundaManager;
import org.app.domain.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@KafkaListener(topics = {"${business.topic}", "${notification.topic}"})
public class EventConsumerImpl implements EventConsumer {

    private final static Logger log = LoggerFactory.getLogger(EventConsumerImpl.class);

    private CamundaManager camundaManager;

    @Value("${message.dataReady}")
    private String messageName;

    @Inject
    public EventConsumerImpl(CamundaManager camundaManager) {
        this.camundaManager = camundaManager;
    }

    @Override
    @KafkaHandler
    public void consume(RecommendedRequestEvent recommendedRequestEvent) {

        log.info("Consuming event of type {}", recommendedRequestEvent.getClass().getSimpleName());

        camundaManager.startProcessForEvent(recommendedRequestEvent);
    }

    @Override
    @KafkaHandler
    public void consume(CompleteHistoryEvent completeHistoryEvent) {

        log.info("Consuming event of type {}", completeHistoryEvent.getClass().getSimpleName());

        String requestId = completeHistoryEvent.getRequestId();

        camundaManager.publishMessage(messageName, requestId);
    }

    @Override
    @KafkaHandler
    public void consume(CannotCompleteHistoryEvent cannotCompleteHistoryEvent) {

        log.info("Consuming event of type {}", cannotCompleteHistoryEvent.getClass().getSimpleName());

        String requestId = cannotCompleteHistoryEvent.getRequestId();

        camundaManager.publishMessage(messageName, requestId);
    }

    @Override
    @KafkaHandler
    public void consume(RecommendationResponseEvent recommendationResponseEvent) {

        log.info("Accepted recommendation for requestId {}", recommendationResponseEvent.getRequestId());

        camundaManager.acceptCurrencyRecommendation(recommendationResponseEvent);
    }

    //Silence the No-handler errors
    @Override
    @KafkaHandler
    public void consume(CannotCompleteRecommendedEvent cannotCompleteRecommendedEvent) {
        log.info("Consuming event of type {}", cannotCompleteRecommendedEvent.getClass().getSimpleName());
    }

    @Override
    @KafkaHandler
    public void consume(CompleteRecommendedRequestEvent completeRecommendedRequestEvent) {
        log.info("Consuming event of type {}", completeRecommendedRequestEvent.getClass().getSimpleName());
    }

    @Override
    @KafkaHandler
    public void consume(RequestWaitForUserEvent requestWaitForUserEvent) {
        log.info("Consuming event of type {}", requestWaitForUserEvent.getClass().getSimpleName());
    }

}
