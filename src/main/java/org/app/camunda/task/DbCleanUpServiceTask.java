package org.app.camunda.task;

import org.app.common.util.EventParams;
import org.app.mongo.RequestDataWrapperRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DbCleanUpServiceTask implements JavaDelegate {

    private final static Logger log = LoggerFactory.getLogger(DbCleanUpServiceTask.class);

    private RequestDataWrapperRepository requestDataWrapperRepository;

    @Inject
    public DbCleanUpServiceTask(RequestDataWrapperRepository requestDataWrapperRepository) {
        this.requestDataWrapperRepository = requestDataWrapperRepository;
    }

    @Override
    public void execute(DelegateExecution execution) {

        log.info("Executing for process instance id {}", execution.getProcessInstanceId());

        String requestId = String.valueOf(execution.getVariable(EventParams.REQUEST_ID));

        log.info("Removing data for request {}", requestId);

        requestDataWrapperRepository.deleteByRequestId(requestId);
    }
}
