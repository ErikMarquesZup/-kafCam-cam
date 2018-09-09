package org.app.mongo;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecommendationDataMongoStore extends MongoRepository<RecommendationData, String> {

}
