package org.app.mongo;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RecommendationDataRepositoryImpl implements RecommendationDataRepository {

    private RecommendationDataMongoStore recommendationDataMongoStore;

    @Inject
    public RecommendationDataRepositoryImpl(RecommendationDataMongoStore recommendationDataMongoStore) {
        this.recommendationDataMongoStore = recommendationDataMongoStore;
    }

    @Override
    public void save(RecommendationData recommendationData) {
        recommendationDataMongoStore.save(recommendationData);
    }
}
