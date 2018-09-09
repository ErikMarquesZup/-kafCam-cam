package org.app.camunda.task;

import org.app.mongo.RecommendationData;
import org.app.mongo.RecommendationDataRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
public class SaveDataServiceTask implements JavaDelegate {

    private final static Logger log = LoggerFactory.getLogger(SaveDataServiceTask.class);

    private RecommendationDataRepository recommendationDataRepository;

    @Inject
    public SaveDataServiceTask(RecommendationDataRepository recommendationDataRepository) {
        this.recommendationDataRepository = recommendationDataRepository;
    }

    @Override
    public void execute(DelegateExecution execution) {

        log.info("Executing for process instance id {}", execution.getProcessInstanceId());

        Map<String, Object> variables = execution.getVariables();

        RecommendationData recommendationData = new RecommendationData(variables);

        recommendationDataRepository.save(recommendationData);
    }
}
