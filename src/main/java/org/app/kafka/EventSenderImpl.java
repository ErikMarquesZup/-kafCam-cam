package org.app.kafka;

import org.app.domain.event.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class EventSenderImpl implements EventSender {

    private final static Logger log = LoggerFactory.getLogger(EventSenderImpl.class);

    private KafkaTemplate<String, DomainEvent> kafkaTemplate;

    @Inject
    public EventSenderImpl(KafkaTemplate<String, DomainEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendEvent(DomainEvent event, String topic) {

        log.info("Sending event of type: {} to topic {}", event.getClass().getSimpleName(), topic);

        kafkaTemplate.send(topic, event.getRequestId(), event);
    }
}
