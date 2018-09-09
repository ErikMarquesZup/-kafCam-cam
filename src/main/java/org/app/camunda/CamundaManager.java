package org.app.camunda;

import org.app.domain.event.RecommendationResponseEvent;
import org.app.domain.event.RecommendedRequestEvent;

public interface CamundaManager {

    void startProcessForEvent(RecommendedRequestEvent recommendedRequestEvent);

    void publishMessage(String messageName, String requestId);

    void acceptCurrencyRecommendation(RecommendationResponseEvent recommendationResponseEvent);
}
