package org.app.kafka;

import org.app.domain.event.DomainEvent;

public interface EventSender {
    void sendEvent(DomainEvent event, String topic);
}
