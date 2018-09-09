package org.app.kafka;


import org.app.domain.event.*;

public interface EventConsumer {

    void consume(RecommendedRequestEvent recommendedRequestEvent);

    void consume(CompleteHistoryEvent completeHistoryEvent);

    void consume(CompleteRecommendedRequestEvent completeRecommendedRequestEvent);

    void consume(CannotCompleteHistoryEvent cannotCompleteHistoryEvent);

    void consume(CannotCompleteRecommendedEvent cannotCompleteRecommendedEvent);

    void consume(RequestWaitForUserEvent requestWaitForUserEvent);

    void consume(RecommendationResponseEvent recommendationResponseEvent);
}
