package org.app.mongo;

import org.app.common.util.EventParams;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Map;

@Document
public class RecommendationData {

    @Id
    private String mongoId;
    private String requestId;
    private String currencySymbol;
    //TODO zmienić na coś bardziej sensownego
    private Long timeStamp;
    private BigDecimal currencyQuantity;
    private Integer score;
    private String opinion;

    public RecommendationData(Map<String, Object> variables) {
        requestId = String.valueOf(variables.get(EventParams.REQUEST_ID));
        currencySymbol = String.valueOf(variables.get(EventParams.CURRENCY_SYMBOL));
        timeStamp = Long.valueOf(String.valueOf(variables.get(EventParams.TIMESTAMP)));
        currencyQuantity = (BigDecimal) variables.get(EventParams.CURRENCY_QUANTITY);
        score = Integer.valueOf(String.valueOf(variables.get(EventParams.CURRENCY_SCORE)));
        opinion = String.valueOf(variables.get(EventParams.CURRENCY_OPINION));
    }

    //For Mongo
    public RecommendationData() {
    }

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public BigDecimal getCurrencyQuantity() {
        return currencyQuantity;
    }

    public void setCurrencyQuantity(BigDecimal currencyQuantity) {
        this.currencyQuantity = currencyQuantity;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
}
